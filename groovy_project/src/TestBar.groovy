import javax.print.DocFlavor
import java.net.ServerSocket
import java.net.Socket
import java.io.PrintWriter
import java.io.BufferedReader
import java.io.InputStreamReader




//socket和内部信息交换的测试桩


Thread.start {
    def session1 = new Session("test-id")
    ServerSocket testServerSocket = new ServerSocket(8080)

    println "Server started, listening on port 8080"
    while(true) {
        Socket testClientSocket = testServerSocket.accept()
        session1.initializeStateMachine {
            vString("username", "")
            state("开始") {
                printMessage("欢迎来到状态机！")
                scanString("username", "请输入用户名：")
                Default("结束")
            }
            state("结束") {
                printMessage("感谢使用，%{username} 再见！")
            }
        }

        // 处理客户端请求
        BufferedReader reader = new BufferedReader(new InputStreamReader(testClientSocket.getInputStream()))
        PrintWriter writer = new PrintWriter(testClientSocket.getOutputStream(), true)

        session1.stateMachine.processInputFirst()
        // 读取客户端输入并处理
        String clientInput = reader.readLine()
        String clientInput2 = reader.readLine()
        println "Received from client: $clientInput"
        session1.stateMachine.processInput("${clientInput}")
        String response = session1.stateMachine.processInput(" ")
        println "Response to client: $response"

        // 返回响应
        writer.println(response)
        testServerSocket.close()
    }
}



// 模拟客户端发送消息并测试
Thread.start {
    Thread.sleep(1000) // 确保服务器先启动

    Socket Serversocket = new Socket("localhost", 8080)
    PrintWriter writer = new PrintWriter(Serversocket.getOutputStream(), true)
    BufferedReader reader = new BufferedReader(new InputStreamReader(Serversocket.getInputStream()))
    // 发送测试消息
    writer.println("Alice")
    writer.println(" ")
    String response = reader.readLine()
    // 使用 assert 测试响应a
    assert response == "感谢使用，Alice 再见！"
    println "Client received response: $response"
    println "All tests passed!"
    Serversocket.close()
}
