<template>
  <el-form
      :model="info"
      :rules="FormRules"
      class="login-container"
      label-position="top"
      label-width="100px"
      style="max-width: 460px">

    <el-form-item label="用户名" prop="username">
      <el-input v-model="info.username"/>
    </el-form-item>

    <el-form-item label="密码" prop="password">
      <el-input v-model="info.password" type="password"/>
    </el-form-item>

    <el-form-item style="width: 100%;display:inline">
      <div style="width: 50%;display:inline">
        <el-button :disabled="info.username===''||info.password===''"
                   style="width: 100%;display:inline"
                   type="primary"
                   @click="loginAction">
          登录
        </el-button>
      </div>

      <div style="width: 50%;display:inline">
        <el-button text-align="center" type="text" @click="registerDialog=!registerDialog">去注册</el-button>
      </div>

    </el-form-item>

  </el-form>

</template>

<script>
import {useStore} from 'vuex'
import {reactive, ref} from 'vue'
import Register from "@/components/Register";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {computed} from "@vue/reactivity";

export default {
  name: 'Login',

  components: {
    Register
  },

  setup() {
    const store = useStore()
    //表单
    const info = reactive({
      username: '',
      password: '',
    })
    //校验规则
    const FormRules = reactive({
      username: [{
        required: true,
        message: '请输入用户名',
        trigger: 'blur'
      }],
      password: [{
        required: true,
        message: '请输入密码',
        trigger: 'blur'
      },
      ]
    })

    const router = useRouter()

    const currentPath = computed(() => {
      return router.currentRoute.value.name
    })
    const registerDialog = computed({
      get() {
        return store.state.registerState
      },
      set(val) {
        store.state.registerState = val
      }
    });

    //登录函数
    function loginAction() {
      request.post("/user/login", info).then(res => {
        if (res.code === '0') {
          //从返回数据中删除密码存到本地
          res.data.password = undefined
          localStorage.setItem('user', JSON.stringify(res.data))
          //关闭对话框，更新昵称与角色
          store.commit('CLOSELOG')
          store.commit('UPDATEROLE', res.data.role)
          store.commit('UPDATENICK', res.data.nickName)
          //
          if(currentPath.value==='index'){
            router.push({
              name: "mainview",
            });
          }
          //提示信息
          ElMessage({
            message: '登录成功',
            type: 'success'
          })
          //关闭头部按钮
          store.commit('CLOSERAL')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }


    return {
      registerDialog,
      info,
      FormRules,
      loginAction
    }
  }
}
</script>

<style scoped>
</style>