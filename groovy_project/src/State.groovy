// 定义状态类
class State {
    String name
    Map<String, String> transitions = [:]
    String defaultTransition
    StateMachine stateMachine

    //执行的等待队列
    List<ActionWithHandler> queueActions = []  // 使用新的封装类
    int actionIndex = 0  // 当前执行的操作索引


    private String nextStateName



    State(String name,StateMachine stateMachine) {
        this.name = name
        this.stateMachine=stateMachine
    }


    void Input(String key,String action){
        transitions[key] = action
    }

    void Default(String nextState) {
        defaultTransition = nextState
    }
    // 封装打印消息的功能，支持变量替换
    void printMessage(String message){
        addAction({ ->
            String processedMessage = message.replaceAll(/%\{(\w+)\}/) { fullMatch, varName ->
                stateMachine.variables[varName]?.toString() ?: ''
            }
            output.append(processedMessage + "\n")
        }, "printMessage: \"$message\"")
        addInputHandler { -> }
    }



    void addAction(Closure action, String description = '') {
        queueActions << new ActionWithHandler(action: action, description: description)
    }
    // 直接赋值
    void assign(String varName, Closure closure) {
        closure.delegate=stateMachine.variables
        closure.resolveStrategy=Closure.DELEGATE_FIRST
        //println "add_assign into queue"
        addAction({
            stateMachine.variables[varName] = closure.call()
        }, "assign: $varName")
    }
    // 添加输入处理器
    void addInputHandler(Closure handler) {
        if (queueActions.size() > 0) {
            def lastAction = queueActions[-1]
            lastAction.handleInput = handler
        } else {
            throw new IllegalStateException("没有可关联的动作")
        }
    }


    void scanInt(String varName, String prompt = '') {
        //println "add_acanInt into queue"
        addAction({ ->
            stateMachine.waitingForInput = true
            output.append(prompt + "\n")
        }, "scanInt: $varName with prompt \"$prompt\"")
        addInputHandler { String userInput, StateMachine stateMachine, StringBuilder output ->
            try {
                int value = userInput.toInteger()
                stateMachine.variables[varName] = value
                //println "stateMachine.variables:${stateMachine.variables}"
                stateMachine.waitingForInput = false
                return true  // 输入处理成功
            } catch (NumberFormatException e) {
                output.append("输入无效，请输入一个有效的整数。\n")
                output.append(prompt + "\n")
                return false  // 输入处理失败，继续等待输入
            }
        }
    }
    void scanString(String varName, String prompt = ''){
        //println "add_scanString into queue"
        //将prompt提示词添加到状态队列中
        addAction({ ->
            stateMachine.waitingForInput = true
            output.append(prompt + "\n")
        }, "scanString: $varName with prompt \"$prompt\"")
        //处理队列
        addInputHandler { String userInput, StateMachine stateMachine, StringBuilder output ->
            try {
                String value = userInput.toString()
                stateMachine.variables[varName] = value
                //println "stateMachine.variables:${stateMachine.variables}"
                stateMachine.waitingForInput = false
                return true  // 输入处理成功
            } catch (NumberFormatException e) {
                output.append("输入无效，请输入一个有效的字符串。\n")
                output.append(prompt + "\n")
                return false  // 输入处理失败，继续等待输入
            }
        }
    }
    // 修改 action 方法
    void actionInqueue(StateMachine stateMachine, StringBuilder output) {

        while (actionIndex < queueActions.size()) {
            //println "now in ${actionIndex}"
            def actionWithHandler = queueActions[actionIndex]
            def actionClosure = actionWithHandler.action
            actionClosure.delegate = [stateMachine: stateMachine, output: output, state: this]
            actionClosure.resolveStrategy = Closure.DELEGATE_FIRST
            actionClosure.call()
            if (stateMachine.waitingForInput) {
                // 等待输入，暂停执行
                return
            }
            actionIndex++
        }
    }
    // 修改 handleInput 方法
    boolean handleInput(String userInput, StringBuilder output) {
        if (actionIndex > 0 && actionIndex < queueActions.size()) {
            //println "queueActions:${queueActions}"
            def actionWithHandler = queueActions[actionIndex]
            if (actionWithHandler.handleInput) {
                actionIndex++
                return actionWithHandler.handleInput(userInput, stateMachine, output)
            }
        }
        // 如果没有特殊的输入处理，尝试状态转移
        nextStateName = transitions[userInput] ?: defaultTransition
        stateMachine.TransForTrans=true
        stateMachine.waitingForInput = false
        return true
    }



    String getNextState() {
        return nextStateName
    }


}
