// StateMachineTest.groovy

def stateMachine = new StateMachine()

// 加载 DSL 文件
String MainDSL = new File("Main.gdsl").text

// 创建一个闭包，将 MainDSL 的内容放入闭包中
def configClosure = new GroovyShell().evaluate("""
return {
    ${MainDSL}
}
""")

// 设置闭包的委托和解析策略
configClosure.delegate = stateMachine
configClosure.resolveStrategy = Closure.DELEGATE_FIRST

// 接收 DSL 配置
stateMachine.acceptConfig(configClosure)

// 读取测试输入
def testInputs = new File("test").readLines()

// 预期输出列表
def expectedOutputs = [
        "欢迎来到机器人服务，请选择：\n1. 查询余额\n2. 转账\n3. 退出",
        "您的余额是1000元。输入任意键返回主菜单。",
        "欢迎来到机器人服务，请选择：\n1. 查询余额\n2. 转账\n3. 退出",
        "请输入转账金额：",
        "转账成功！输入任意键返回主菜单。",
        "欢迎来到机器人服务，请选择：\n1. 查询余额\n2. 转账\n3. 退出",
        "您的余额是1100元。输入任意键返回主菜单。",
        "欢迎来到机器人服务，请选择：\n1. 查询余额\n2. 转账\n3. 退出",
        "感谢您的使用，再见！"
]

// 开始状态机
stateMachine.start('开始')

def outputs = []
def inputIndex = 0
def outputIndex = 0

// 处理初始输出
def output = stateMachine.processInputFirst()
outputs << output
assert output == expectedOutputs[outputIndex++]
println "测试${outputIndex}通过！"
// 循环处理输入和输出
while (outputIndex < expectedOutputs.size()){
    if (stateMachine.waitingForInput) {
        if (inputIndex < testInputs.size()) {
            def input = testInputs[inputIndex++]
            output = stateMachine.processInput(input)
            // 有可能 output 为 null，需要过滤
            if (output) {
                outputs << output
                assert output == expectedOutputs[outputIndex++]
                println "测试${outputIndex}通过！"
            }
        } else {
            // 如果没有更多的输入，退出循环
            break
        }
    } else {
        // 如果状态机不等待输入，继续处理
        if(stateMachine.TransForTrans){
            output = stateMachine.processInput("")
            // 有可能 output 为 null，需要过滤
            if (output) {
                outputs << output
                assert output == expectedOutputs[outputIndex++]
                println "测试${outputIndex}通过！"
            }
        }else{
            def input = testInputs[inputIndex++]
            output=stateMachine.processInput(input)
            if (output) {
                outputs << output
                assert output == expectedOutputs[outputIndex++]
                println "测试${outputIndex}通过！"
            }
        }
    }
}

println "所有测试通过！"
