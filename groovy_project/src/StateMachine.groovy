// 定义状态机
class StateMachine {
    /*-----------状态存储--------------------------*/
    Map<String, State> states = [:]  // 存储所有状态对象的映射，键为状态名，值为对应的状态对象
    String initialState             // 初始状态的名称
    String currentStateName         // 当前正在处理的状态的名称
    /*-----------状态存储--------------------------*/

    /*---------------变量检查----------------------*/
    Map<String, Object> variables = [:]          // 存储状态机的动态变量
    Set<String> definedVariables = new HashSet<>()  // 已定义变量的集合
    Set<String> usedVariables = new HashSet<>()     // 被使用的变量集合，用于验证未定义变量
    /*---------------变量检查----------------------*/

    /*------增加会话相关的属性------------------------*/
    boolean waitingForInput = false  // 指示状态机是否等待用户输入
    boolean TransForTrans = false    // 指示是否需要执行状态转换
    /*------增加会话相关的属性------------------------*/

    /**
     * 定义一个字符串变量，并初始化其值。
     * @param name 变量名称
     * @param initialValue 初始值，默认为空字符串
     */
    void vString(String name, String initialValue = '') {
        variables[name] = initialValue
        definedVariables.add(name)  // 将变量添加到已定义变量集合
    }

    /**
     * 定义一个整型变量，并初始化其值。
     * @param name 变量名称
     * @param initialValue 初始值，默认为 0
     */
    void vInt(String name, int initialValue = 0) {
        variables[name] = initialValue
        definedVariables.add(name)  // 将变量添加到已定义变量集合
    }

    /**
     * 重写 `getProperty` 方法，动态访问变量。
     * - 优先从 `variables` 获取变量值。
     * - 如果变量未定义，则检查是否是状态机本身的属性。
     * - 如果都找不到，抛出 `MissingPropertyException` 异常。
     *
     * @param varName 要访问的变量名称
     * @return 返回变量值
     */
    @Override
    def getProperty(String varName) {
        if (variables.containsKey(varName)) {
            usedVariables.add(varName) // 记录变量使用
            return variables[varName]
        } else if (metaClass.hasProperty(this, varName)) {
            return metaClass.getProperty(this, varName)
        } else {
            throw new MissingPropertyException("变量 '${varName}' 未定义")
        }
    }

    /**
     * 重写 `setProperty` 方法，动态设置变量值。
     * - 如果变量存在于 `variables`，直接设置其值。
     * - 如果变量是状态机本身的固定属性，则通过 `metaClass` 设置。
     *
     * @param name 变量名称
     * @param value 要设置的值
     */
    @Override
    void setProperty(String name, value) {
        if (variables.containsKey(name) || !metaClass.hasProperty(this, name)) {
            variables[name] = value
        } else {
            metaClass.setProperty(this, name, value)
        }
    }

    /**
     * 检查是否存在未定义但已使用的变量。
     * - 如果存在未定义的变量，抛出 `IllegalStateException`。
     */
    void checkUndefinedVariables() {
        Set<Object> undefinedVariables = usedVariables - definedVariables
        if (!undefinedVariables.isEmpty()) {
            throw new IllegalStateException("未定义的变量：${undefinedVariables}")
        }
    }

    /**
     * 设置初始状态。
     * @param stateName 初始状态名称
     */
    void start(String stateName) {
        initialState = stateName
    }

    /**
     * 接收 DSL 配置，并将闭包应用于状态机。
     * - 将状态机作为闭包的 `delegate`。
     * - 配置状态和变量等。
     *
     * @param closure 配置闭包
     */
    void acceptConfig(Closure closure) {
        closure.delegate = this
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

    /**
     * 初次处理输入，执行初始状态中的动作队列。
     * @return 返回初始状态的输出结果
     */
    String processInputFirst() {
        if (currentStateName == null) {
            currentStateName = initialState
        }
        StringBuilder output = new StringBuilder()
        State state = states[currentStateName]

        // 检查初始状态是否存在
        if (!state) {
            output.append("未知的状态：$currentStateName")
            return output.toString().trim()
        }

        // 执行初始状态的动作队列
        while (!waitingForInput && state != null) {
            state.actionInqueue(this, output)
            if (waitingForInput || !TransForTrans) {
                return output.toString().trim()
            }
        }
    }

    /**
     * 处理用户输入，按逻辑执行状态动作或状态转换。
     * @param userInput 用户输入
     * @return 返回状态机的响应结果
     */
    String processInput(String userInput) {
        if (currentStateName == null) {
            currentStateName = initialState
        }
        StringBuilder output = new StringBuilder()
        State state = states[currentStateName]
        // 检查当前状态是否存在
        if (!state) {
            output.append("未知的状态：$currentStateName")
            return output.toString().trim()
        }
        // 如果等待输入，先处理输入
        if (waitingForInput || !TransForTrans) {
            boolean inputProcessed = state.handleInput(userInput, output)
            if (inputProcessed) {
                waitingForInput = false
            } else {
                return output.toString().trim()  // 输入未处理成功，返回输出
            }
        }
        // 执行当前状态的动作
        if (!TransForTrans) {
            while (!waitingForInput && state != null) {
                state.actionInqueue(this, output)
                if (waitingForInput || !TransForTrans) {
                    return output.toString().trim()
                }
            }
        }
        // 如果需要状态转换
        if (TransForTrans) {
            currentStateName = state.getNextState()
            if (currentStateName != null) {
                state.actionIndex = 0
                state = states[currentStateName]
                TransForTrans = false
                waitingForInput = false
                state.actionInqueue(this, output)  // 执行转换后的状态动作
            } else {
                currentStateName = null
            }
        }
        return output.toString().trim()
    }

    /**
     * 定义状态，并通过闭包配置状态的行为。
     * @param name 状态名称
     * @param closure 配置状态的闭包
     */
    void state(String name, @DelegatesTo(State) Closure closure) {
        State state = new State(name, this)  // 创建新状态
        closure.delegate = state             // 将状态作为闭包的代理对象
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()                            // 应用闭包配置
        states[name] = state                 // 保存状态到状态机
    }
}
