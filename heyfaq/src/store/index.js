//搭建Vuex中的store

import vuex from 'vuex'
import request from "@/utils/request";


//actions--用于响应组件中的动作
const actions = {
    doSearch(context,value){
        request.get("/questions", {
            params: {
                pageNum: value.pageNum,
                pageSize: value.pageSize,
                search: value.search,
                sort:value.sort
            }
          }).then(res=>{

            //   questionReturn.value=res.data.records
              context.commit('DOSEARCH',res.data)
          })
        
    },
    doSearchBySubject(context,value){
        request.get("/questions/bySubject", {
            params: {
                pageNum: value.pageNum,
                pageSize: value.pageSize,
                search: value.search,
                sort:value.sort
            }
        }).then(res=>{
            //   questionReturn.value=res.data.records
            context.commit('DOSEARCH',res.data)
        })

    },
    findMine(context,value){
        request.get("/questions/myquestions",{
            params:{
                pageNum:value.pageNum,
                pageSize:value.pageSize,
                search: value.search,
            }
        }).then(res=>{
            //   questionReturn.value=res.data.records
            if(res.code==='0'){
                context.commit('FINDMINE',res.data)
            }
        })
    },
    findToBeSolved(context,value){
        request.get("/questions/tobesolved",{
            params:{
                pageNum:value.pageNum,
                pageSize:value.pageSize,
                search: value.search,
            }
        }).then(res=>{
            //   questionReturn.value=res.data.records
            if(res.code==='0'){
                context.commit('FINDTOBESOLVED',res.data)
            }

        })

    },
    findSolved(context,value){
        request.get("/questions/solved",{
            params:{
                pageNum:value.pageNum,
                pageSize:value.pageSize,
                search: value.search,
            }
        }).then(res=>{
            //   questionReturn.value=res.data.records
            if(res.code==='0'){
                context.commit('FINDSOLVED',res.data)
            }

        })

    }
}
//mutations--用于操作数据
const mutations = {
    ADD_PERSON(state, val){
        state.personList.unshift(val)
    },
    DOSEARCH(state,val){
        state.ariticleList=val
        

    },
    FINDMINE(state,val){
        state.myquestions=val


    },
    FINDTOBESOLVED(state,val){
        state.toBeSolved=val


    },
    FINDSOLVED(state,val){
        state.Solved=val


    },
    CLOSEREG(state){
        // state.registerState=!state.registerState
        state.registerState=!state.registerState
    },
    CLOSELOG(state){
        state.loginState=!state.loginState
    },
    CLOSERAL(state){
        state.ralShow=!state.ralShow
    },
    UPDATENICK(state,val){
        state.nickName=val
    },
    UPDATEROLE(state,val){
        state.userRole=val
    },
    SETAQ(state,val){
        state.AqState=val
    },
    SETSOLVEDQ(state,val){
        state.SolvedqState=val
    },
    SETTOSOLVEDQ(state,val){
        state.TosolvedqState=val
    },
    SETMQ(state,val){
        state.MqState=val
    },
    SETSQ(state,val){
        state.SqState=val
    }
}
//state--用于存储数据
const state = {
    nickName:'',
    userRole:'',
    searchInput:'',
    ariticleList:[],
    myquestions:[],
    toBeSolved:[],
    Solved:[],

    AqState:{},
    MqState:{},
    SqState:{},
    SolvedqState:{},
    TosolvedqState:{},

    selectSubject:'1',
    registerState:false,
    loginState:false,
    ralShow:localStorage.getItem('user')===null,

    currentSearch:false,
}

const getters = {
}

export default new vuex.Store({
    actions,
    mutations,
    state,
    getters
})
