import groovy.json.JsonBuilder
import groovy.json.JsonOutput

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


//读入文件后，直接检测stateMachine的Map<String, State> states = [:]
//并检测states中每一个State的执行的等待队列List<ActionWithHandler> queueActions = []  // 使用新的封装类
//以json的格式打印

//def stateInfoList = stateMachine.states.collect { stateName, state ->
//    [
//            state: stateName,
//            queueActions: state.queueActions.collect { actionWithHandler ->
//                actionWithHandler.description ?: "Unnamed action"
//            },
//            transitions: state.transitions,
//            defaultTransition: state.defaultTransition
//    ]
//}




def stateInfoList = []

def stateInfo = stateMachine.states.collectEntries { stateName, state ->
    [
            (stateName): [
                queueActions: state.queueActions.collect { it.description ?: "Unnamed action" } ,
                transitions:state.transitions.collectEntries { input, nextState -> [(input): nextState]} ,
                defaultTransition:state.defaultTransition ?: "None"
            ]
    ]
}

println "{"
stateInfo.each { stateName, stateDetails ->
    println "State: ${stateName}"+" {"
    println "  Queue Actions:"+ " {"
    stateDetails.queueActions.each { action ->
        println "    - ${action}"
    }
    println "  }"
    println "  Transitions:"+ " {"
    stateDetails.transitions.each { input, nextState ->
        println "    Input '${input}' -> Next State '${nextState}'"
    }
    println "  }"
    println "  Default Transition: ${stateDetails.defaultTransition}"
    println " }"
}
println "}"


def jsonOutput = JsonOutput.prettyPrint(JsonOutput.toJson(stateInfo))
println(jsonOutput)

//// 打印或保存列表内容
//stateInfoList.each { stateInfo ->
//    println "State: ${stateInfo.state}"
//    println "Queue Actions: ${stateInfo.queueActions.join(", ")}"
//    println "Transitions: ${stateInfo.transitions}"
//    println "Default Transition: ${stateInfo.defaultTransition}"
//    println "--------------------------------------------"
//}
