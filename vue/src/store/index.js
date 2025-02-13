import {createStore} from "vuex";

const store = createStore({
        state :{
            count:0,
            userInfo: {
              username: '',
            },
        },
        mutations:{
            increment(state){
                state.count++
            },
        },
})

export default store