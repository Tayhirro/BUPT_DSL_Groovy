import groovy.transform.Field
import org.codehaus.groovy.ast.tools.ClosureUtils

import java.awt.image.BufferedImage
import java.net.ServerSocket
import java.net.Socket
import java.util.Scanner

import java.util.concurrent.ConcurrentHashMap
import java.util.UUID
import java.util.Timer
import java.util.TimerTask

import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

// 使用 @Field 注解定义 sessions 变量，使其在闭包和方法中可访问
@Field Map<String, Session> sessions = new ConcurrentHashMap<>()
// `sessions` 是一个线程安全的映射，用于存储所有会话信息，键是 session ID，值是会话对象。

/* 定义一个可选的超时时间，默认值为 10 分钟（以毫秒为单位）
long SESSION_TIMEOUT = 10 * 60 * 1000

// 定义一个定时任务，用于清理超时的会话
Timer timer = new Timer(true)
timer.scheduleAtFixedRate(new TimerTask() {
    @Override
    void run() {
        long now = System.currentTimeMillis()
        // 移除所有超时的会话
        sessions.values().removeIf { session ->
            (now - session.lastActivityTime) > SESSION_TIMEOUT
        }
    }
}, SESSION_TIMEOUT, SESSION_TIMEOUT)
*/



// 定义一个处理客户端请求的函数
def clientHandler(Socket clientSocket, Closure closure) {
    try {
        println "Handling new client: ${clientSocket.remoteSocketAddress}"
        // 通过 `BufferedReader` 和 `BufferedWriter` 处理客户端的输入和输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))

        // 读取客户端发送的 JSON 请求
        String requestJson = reader.readLine()
        println "Received request: ${requestJson}"
        if (requestJson == null || requestJson.trim().isEmpty()) {
            // 如果请求为空，返回错误响应
            writer.write(jsonResponse([error: "Empty request"]))
            writer.flush()
            return
        }

        // 使用 `JsonSlurper` 将 JSON 请求解析为 Map 对象
        Map request = new JsonSlurper().parseText(requestJson)

        String sessionId = request.session_id  // 获取 session ID
        String userInput = request.user_input  // 获取用户输入

        Session session
        boolean isNewSession = false

        if (sessionId == null || sessionId.trim().isEmpty() || !sessions.containsKey(sessionId)) {
            // 如果 session ID 不存在或无效，创建新会话
            sessionId = UUID.randomUUID().toString() // 生成新的唯一 session ID
            session = new Session(sessionId)
            println "正在初始化新的 session..."

            // 初始化状态机
            session.initializeStateMachine(closure)
            println "初始化新的 session 完成"

            // 获取初始状态的响应
            String initialResponse = session.stateMachine.processInputFirst()
            println "初始化后的响应: ${initialResponse}"

            // 将新会话存入会话映射
            sessions[sessionId] = session
            println session
            isNewSession = true

            // 构建响应并发送给客户端
            Map response = [response: initialResponse, session_id: sessionId]
            String responseJson = new JsonBuilder(response).toString()
            println "Sending response: ${responseJson}"
            writer.write(responseJson + "\n")
            writer.flush()
        } else {
            // 如果 session 已存在，获取会话对象
            session = sessions[sessionId]
            println "使用已有的 session: ${sessionId}"

            // 更新会话的最后活动时间
            session.updateActivity()

            // 处理用户输入并获取响应
            String responseText = session.stateMachine.processInput(userInput)
            println "Processed input: '${userInput}', response: '${responseText}'"

            // 构建响应并发送
            Map response = [response: responseText]
            if (isNewSession) {
                response.session_id = sessionId
            }
            String responseJson = new JsonBuilder(response).toString()
            writer.write(responseJson + "\n")
            writer.flush()
        }
    } catch (Exception e) {
        // 捕获并打印异常
        e.printStackTrace()
    } finally {
        // 关闭客户端连接
        clientSocket.close()
        println "Closed connection with client"
    }
}

// 将 Map 对象转换为 JSON 字符串
def jsonResponse(Map response) {
    return new JsonBuilder(response).toString()
}

// 定义主服务器函数，用于接受配置并启动服务
def acceptConfigs(Closure closure) {
    // 启动服务器，监听端口 9999
    ServerSocket serverSocket = new ServerSocket(9999)
    println "Groovy 服务器正在运行在端口 9999"
    while (true) {
        println "等待客户端连接..."
        // 接收客户端连接
        Socket clientSocket = serverSocket.accept()
        println "客户端连接成功，开始处理请求。"
        // 为每个客户端请求启动一个新的线程
        new Thread({
            clientHandler(clientSocket, closure)
        }).start()
    }
}
