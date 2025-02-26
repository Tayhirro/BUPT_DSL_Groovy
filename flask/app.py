from flask import Flask, render_template, jsonify,make_response,session,request
import json
from flask_cors import CORS
import socket
import redis


import jwt  #用于token
import datetime
import bcrypt   #用于加密
import dashscope
import logging

from models import Login,db

from werkzeug.security import generate_password_hash, check_password_hash
from datetime import timedelta

from http import HTTPStatus

from functools import wraps

secret_key = "123456"
global_session_id = ""   #全局变量
now_state =0
dashscope.api_key = "example"

def create_app(config_path='./composer.json'):
    app = Flask(__name__)
    with open(config_path,'r',encoding='utf-8') as file:
        config_data = json.load(file)
    app.config.update({
        'REDIS_HOST': config_data['database_root'],
        'REDIS_PORT': config_data['redis_port'],
        'REDIS_DB': config_data['redis_db'],
        'SQLALCHEMY_DATABASE_URI': f"mysql+pymysql://root:123456@{config_data['database_root']}:3306/robot",
        'SQLALCHEMY_TRACK_MODIFICATIONS': False
    })
    #初始化数据库
    db.init_app(app)

    # 创建数据库表（仅第一次需要）
    with app.app_context():
        db.create_all()
    redis_client = redis.Redis(
        host=app.config['REDIS_HOST'],
        port=app.config['REDIS_PORT'],
        db=app.config['REDIS_DB']
    )
    CORS(app, supports_credentials=True)
    logging.basicConfig(level=logging.INFO)
    return app,redis_client




def generate_jwt(user_id):
    payload = {
        'user_id':user_id,
        'exp': datetime.datetime.utcnow() + datetime.timedelta(days=1)  # 设置过期时间为 1 天
    }
    token = jwt.encode(payload, secret_key, algorithm='HS256')
    return token

def token_required(f):
    @wraps(f)
    def decorated(*args,**kwargs):
        token=request.cookies.get('auth_token') or request.headers.get('Authorization')
        if not token:
            return jsonify({'message':'Token is missing'}),401
        try:
            payload=jwt.decode(token,secret_key,algorithms='HS256')
            kwargs['user_id']=payload['user_id']

        except jwt.ExpiredSignatureError:
            # Token 已过期
            return jsonify({'message': 'Token has expired'}), 401
        except jwt.InvalidTokenError:
            # Token 无效（签名不匹配，或者格式不正确等原因）
            return jsonify({'message': 'Invalid token'}), 401
        return f(*args,**kwargs)

    return decorated



def call_groovy_service(user_input):
    global global_session_id
    request_dict = {'session_id': global_session_id, 'user_input': user_input}
    request_json = json.dumps(request_dict) + "\n"

    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as groovy_socket:
            groovy_socket.settimeout(10)  # 设置超时时间
            groovy_socket.connect(('localhost', 9999))
            groovy_socket.sendall(request_json.encode('utf-8'))

            # 接收响应
            response_data = b''.join(iter(lambda: groovy_socket.recv(4096), b'')).decode('utf-8')

        # 解析响应
        response_dict = json.loads(response_data)
        global_session_id = response_dict.get('session_id', global_session_id)
        return {'response': response_dict.get('response', '')}

    except (ConnectionRefusedError, socket.timeout) as e:
        return {'error': '无法连接到后端服务', 'message': str(e)}, 503
    except json.JSONDecodeError as e:
        return {'error': '响应格式错误', 'message': str(e)}, 500
    except Exception as e:
        return {'error': '服务器内部错误', 'message': str(e)}, 500

def call_baichuan_api(input_type, input_data):
    try:

        if input_type == 'message':
            response = dashscope.Generation.call(
                model='baichuan-7b-v1',
                messages=input_data,
                result_format='message'
            )
        elif input_type == 'prompt':
            response = dashscope.Generation.call(
                model='baichuan-7b-v1',
                prompt=input_data
            )
        else:
            raise ValueError("Unsupported input type")

        if response.status_code == HTTPStatus.OK:
            return response
        else:
            return {'error': '调用百川失败', 'message': response.message}, 500
    except Exception as e:
        return {'error': '百川调用异常', 'message': str(e)}, 500

app,redis =create_app()


@app.route('/api/home/postquestion', methods=['POST'])
@token_required
def get_question(user_id):
    global global_session_id  # 声明使用全局变量
    global now_state
    # 仅支持 POST 方法
    if request.method != 'POST':
        return jsonify({'error': 'Only POST method is supported for this route'}), 405

    # 从请求中提取用户输入和 user_id
    data = request.json
    user_input = data.get('messages')
    if not user_input:
        return jsonify({'error': 'No input provided'}), 400
    # 初始化或更新全局 session_id
    if user_input == '@begin':
        now_state = 1
    elif user_input=='@quit':
        now_state = 0

    # 根据 now_state 处理不同逻辑
    if now_state == 0:
        result = call_groovy_service(user_input)
    elif now_state == 1:
        messages = [{'role': 'system', 'content': 'You are a helpful assistant.'},
                    {'role': 'user', 'content': user_input}]
        result = call_baichuan_api('message', messages)
        content = result['output']['choices'][0]['message']['content']
        result ={'response': content}
    # 如果 result 为 None 或没有正确的响应数据，返回适当的错误信息
    if not result:
        return jsonify({'error': 'No response from service'}), 500

    # 返回结果
    return jsonify(result)
@app.route('/api/query', methods=['GET'])
@token_required
def handle_query():
    try:
        # 获取请求中的用户名
        username = request.args.get('username')

        if not username:
            return jsonify({"message": "Username not provided", "status": "error"}), 400

        # 查询用户
        user = Login.query.filter_by(username=username).first()

        if not user:
            return jsonify({"message": "User not found", "status": "error"}), 404

        # 返回用户信息
        user_info = {
            "username": user.username,
            "email": user.email,  # 假设你有 email 字段
            # 其他字段根据需要添加
        }

        return jsonify({"message": "User found", "status": "success", "data": user_info}), 200

    except Exception as e:
        return jsonify({"message": str(e), "status": "error"}), 500

@app.route('/api/update-password', methods=['POST'])
@token_required
def handle_update_password():
    try:
        # 获取请求中的 JSON 数据
        data = request.json
        username = data.get('username')
        new_password = data.get('newPassword')
        if not username or not new_password:
            return jsonify({"message": "Missing required fields", "status": "error"}), 400

        # 查找该用户名的用户
        user = Login.query.filter_by(username=username).first()

        if not user:
            return jsonify({"message": "User not found", "status": "error"}), 404

        # 对新密码进行哈希处理
        hashed_password = bcrypt.hashpw(new_password.encode('utf-8'), bcrypt.gensalt())

        # 更新用户的密码
        user.password = hashed_password.decode('utf-8')  # 存储哈希值作为字符串
        db.session.commit()

        return jsonify({"message": "Password updated successfully", "status": "success"}), 200

    except Exception as e:
        db.session.rollback()  # 出现异常时回滚事务
        return jsonify({"message": str(e), "status": "error"}), 500




@app.route('/api/deleteaccount',methods =['POST'])
def handle_delete():
    try:
        data = request.json
        delete_username = data.get('username')
        if not delete_username:
            return jsonify({"message":"something wrong happened","status":"error"}) , 400

        # 查找该用户名的用户
        user_to_delete = Login.query.filter_by(username=delete_username).first()

        if user_to_delete is None:
            return jsonify({"message": "User not found", "status": "error"}), 404

            # 删除用户
        db.session.delete(user_to_delete)
        db.session.commit()

        return jsonify({"message": f"User {delete_username} deleted successfully", "status": "success"}), 200

    except Exception as e:
        db.session.rollback()  # 出现异常时回滚
        return jsonify({"message": str(e), "status": "error"}), 500

@app.route('/api/register',methods=['GET', 'POST'])
def handle_signin():
    data = request.json  # 从请求体中获取 JSON 数据
    username = data.get('username')
    password = data.get('password')
    if not username or not password:
        return jsonify({"message": "Username and password are required", "status": "error"}), 400
    # 检查用户名是否已存在
    existing_user = Login.query.filter_by(username=username).first()
    if existing_user:
        return jsonify({"message": "Username already exists", "status": "error"}), 400
    # 对密码进行哈希处理
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

    # 创建并保存新用户
    new_user = Login(username=username, password=hashed_password.decode('utf-8'))
    db.session.add(new_user)
    db.session.commit()
    #增加cookie返回
    response=make_response(jsonify({"message": "User registered successfully", "status": "success"}))
    return response, 201
@app.route('/api/login',methods=['POST'])
def handle_Login():

    global now_state
    now_state =0
    data=request.json
    username = data.get('username')
    password = data.get('password')
    if not username or not password:
        return jsonify({"message": "Username and password are required", "status": "error"}), 400
    existing_user =Login.query.filter_by(username=username).first()
    if not existing_user:
        return  jsonify({"message": "Username not Exist", "status": "error"}),400
    # 验证密码
    if bcrypt.checkpw(password.encode('utf-8'), existing_user.password.encode('utf-8')):
        response=make_response(jsonify({"message": "User registered successfully", "status": "success"}))
        genarate_token = generate_jwt(username)
        response.set_cookie('auth_token',
                            genarate_token,
                            httponly=False,
                            secure=False,
                            samesite='Lax',
                            max_age=60 * 60 * 24)
        print(response.get_data(as_text=True))
        print(response.json)
        return  response,200
    else:
       return jsonify({"message": "Invalid username or password", "status": "error"}), 400

@app.route('/api/home', methods=['GET', 'POST'])
@token_required
def handle_home(user_id):
    global global_session_id  # 声明使用全局变量
    if request.method == 'GET':
        # 初始化会话：向 Groovy 服务器发送一个空的 user_input，获取 session_id
        request_dict = {
            'session_id': '',  # 空表示新会话
            'user_input': ''   # 空输入
        }
        request_json = json.dumps(request_dict) + "\n"

        try:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as groovy_socket:
                groovy_socket.connect(('localhost', 9999))  # 连接到 Groovy 服务器
                groovy_socket.sendall(request_json.encode('utf-8'))  # 发送请求

                # 接收响应
                response_chunks = []
                while True:
                    chunk = groovy_socket.recv(4096)
                    if not chunk:
                        break
                    response_chunks.append(chunk)
                response_data = b''.join(response_chunks).decode('utf-8').strip()

                # 解析响应 JSON
                response_dict = json.loads(response_data)
                print(response_dict)

                # 更新全局 session_id
                if 'session_id' in response_dict and response_dict['session_id']:
                    global_session_id = response_dict['session_id']
            # 返回初始化成功的消息
            return jsonify({'message': response_dict.get('response', '')})

        except ConnectionRefusedError:
            return jsonify({'error': '无法连接到后端服务'}), 503
        except json.JSONDecodeError:
            return jsonify({'error': '接收到的响应格式错误'}), 500
        except Exception as e:
            # 其他异常处理
            return jsonify({'error': '服务器内部错误', 'message': str(e)}), 500
    else:
        return jsonify({'message': 'Only GET method is supported for this route'}), 405


if __name__ == '__main__':

    app.run(debug=True)