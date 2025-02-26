
import sqlalchemy
from flask_sqlalchemy import SQLAlchemy
# 定义 Login 表的 ORM 模型

db = SQLAlchemy()
class Login(db.Model):
    __tablename__ = 'login'  # 对应数据库中的 login 表
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    username = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(100), nullable=False)

    def __init__(self, username, password):
        self.username = username
        self.password = password

