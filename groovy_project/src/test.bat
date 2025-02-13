@echo off

:: 检查是否安装 Groovy
where groovy >nul 2>nul
if %errorlevel% neq 0 (
    echo Groovy 未安装，请先安装 Groovy。
    exit /b 1
)

:: 创建临时目录
set TEMP_DIR=%TEMP%\StateMachineTest
if not exist "%TEMP_DIR%" mkdir "%TEMP_DIR%"

:: 写入 Groovy 脚本到临时文件
set SCRIPT_FILE=%TEMP_DIR%\StateMachineTest.groovy
(
echo class StateMachine {
echo     Map<String, Object> variables = [:]
echo     Map<String, State> states = [:]
echo     String currentStateName
echo
echo     void vString(String name, String initialValue = '') {
echo         variables[name] = initialValue
echo     }
echo
echo     void acceptConfig(Closure closure) {
echo         closure.delegate = this
echo         closure.resolveStrategy = Closure.DELEGATE_FIRST
echo         closure()
echo     }
echo
echo     void start(String stateName) {
echo         currentStateName = stateName
echo     }
echo
echo     String processInputFirst() {
echo         return states[currentStateName].getMessage()
echo     }
echo
echo     String processInput(String input) {
echo         def state = states[currentStateName]
echo         if (state) {
echo             currentStateName = state.getNextState(input, variables)
echo             return states[currentStateName]?.getMessage() ?: ""
echo         }
echo         return "未知状态！"
echo     }
echo
echo     void state(String name, Closure closure) {
echo         State state = new State(name)
echo         closure.delegate = state
echo         closure.resolveStrategy = Closure.DELEGATE_FIRST
echo         closure()
echo         states[name] = state
echo     }
echo }
echo
echo class State {
echo     String name
echo     String message
echo     Map<String, String> transitions = [:]
echo
echo     State(String name) {
echo         this.name = name
echo     }
echo
echo     void printMessage(String msg) {
echo         this.message = msg
echo     }
echo
echo     void scanString(String variable, String prompt) {
echo         println(prompt)
echo     }
echo
echo     void Default(String nextState) {
echo         transitions['default'] = nextState
echo     }
echo
echo     String getMessage() {
echo         return message
echo     }
echo
echo     String getNextState(String input, Map<String, Object> variables) {
echo         variables['username'] = input.trim()
echo         return transitions['default'] ?: "结束"
echo     }
echo }
echo
echo // 测试代码
echo def stateMachine = new StateMachine()
echo stateMachine.acceptConfig {
echo     vString("username", "")
echo     state("开始") {
echo         printMessage("欢迎来到状态机！")
echo         scanString("username", "请输入用户名：")
echo         Default("结束")
echo     }
echo     state("结束") {
echo         printMessage("感谢使用，%{username} 再见！")
echo     }
echo }
echo stateMachine.start("开始")
echo
echo // 测试 1
echo def initialResponse = stateMachine.processInputFirst()
echo assert initialResponse == "欢迎来到状态机！\n请输入用户名：\n"
echo
echo // 测试 2
echo stateMachine.processInput("Alice")
echo def response = stateMachine.processInput(" ")
echo assert response == "感谢使用，Alice 再见！\n"
echo
echo println "所有测试通过！"
) > "%SCRIPT_FILE%"

:: 执行 Groovy 脚本
groovy "%SCRIPT_FILE%"

:: 删除临时文件
rd /s /q "%TEMP_DIR%"
