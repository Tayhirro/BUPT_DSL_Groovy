class Session{
    String id
    StateMachine stateMachine
    long lastActivityTime

    Session(String id){
        this.id =id
        this.stateMachine=new StateMachine()
        this.lastActivityTime=System.currentTimeMillis()
    }
    void updateActivity(){
        lastActivityTime=System.currentTimeMillis()
    }


    //初始化stateMachine
    void initializeStateMachine(Closure closure) {

        //配置stateMachine的state，对states进行初始化
        stateMachine.acceptConfig(closure)
        //println "stateMchine:${stateMachine.states}"
        stateMachine.start('开始')
    }
}
