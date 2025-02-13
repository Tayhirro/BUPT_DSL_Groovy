// StateMachineTest.groovy
def stateMachine = new StateMachine()
stateMachine.acceptConfig {
    vString("username","")
    state("开始") {
        printMessage("欢迎来到状态机！")
        scanString("username", "请输入用户名：")
        Default("结束")
    }
    state("结束") {
        printMessage("感谢使用，%{username} 再见！")
    }
}
stateMachine.start("开始")

// 测试 1
def initialResponse = stateMachine.processInputFirst()
assert initialResponse == "欢迎来到状态机！\n请输入用户名：\n"

// 测试 2
stateMachine.processInput("Alice")
def response = stateMachine.processInput(" ")
assert response == "感谢使用，Alice 再见！\n"

println "所有测试通过！"
