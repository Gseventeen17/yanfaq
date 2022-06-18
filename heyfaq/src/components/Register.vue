<template>
  <el-form label-position="top" :model="temporary" :rules="FormRules" class="login-container"
           label-width="100px"
           style="max-width: 460px" ref="resetReg">
    <el-form-item label="用户名" prop="username">

      <el-input v-model="temporary.username"/>
    </el-form-item>

    <el-form-item label="密码" prop="password">
      <el-input v-model="temporary.password" type="password"/>
    </el-form-item>

    <el-form-item label="确认密码" prop="checkPass">
      <el-input
          v-model="temporary.checkPass"
          type="password"
          autocomplete="off"
      />
    </el-form-item>

    <el-form-item label="昵称" prop="nickName">
      <el-input v-model="temporary.nickName"/>
    </el-form-item>

    <el-form-item label="年龄" prop="age">
      <el-input-number v-model="temporary.age" :min="1" :max="100" @change="handleChange"/>
    </el-form-item>

    <el-form-item label="性别" prop="sex">
      <div>
        <el-radio v-model="temporary.sex" label="男" size="large" border>男</el-radio>
        <el-radio v-model="temporary.sex" label="女" size="large" border>女</el-radio>
        <el-radio v-model="temporary.sex" label="未知" size="large" border>未知</el-radio>
      </div>
    </el-form-item>

    <el-form-item style="width: 100%;display:inline">
      <el-button @click="submitForm(resetReg)" style="width: 50%;display:inline" type="primary" :loading="loading">
        注册
      </el-button>
      <!--      @click.native.prevent="submitClick"-->
    </el-form-item>

  </el-form>
</template>

<script>
import {
  reactive,
  ref
} from 'vue'
import request from "@/utils/request";
import {
  useStore
} from 'vuex'
import {
  ElMessage,
} from 'element-plus'
import {axios} from 'axios'


export default {
  name: 'Register',
  setup() {
    const store = useStore()
    const resetReg = ref()
    const loading=ref(false)

    const temporary = reactive({
    username: '',
      password: '',
      nickName: '',
      age: 1,
      sex: '',
      checkPass: '',
      role: 'U'
    })

    function register() {
      delete temporary.checkPass
      request.post("/user/register", temporary).then(res => {
        if (res.code === '0') {
          resetReg.value.resetFields()
          store.commit('CLOSEREG')
          ElMessage({
            message: '注册成功,点击上方登录',
            type: 'success'
          })
        } else {
          ElMessage.error(res.msg)
        }
        loading.value=false
      })
    }

    function handleChange(value) {

    }

    function validatePass2(rule, value, callback) {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== temporary.password) {
        callback(new Error("密码不一致"))

      } else {
        callback()
      }
    }

    function checkUsername  (rule, value, callback) {
      // 正则表达式验证
      const regPass = /^[0-9a-z]{3,20}$/
      if(value===''){
        callback(new Error('请输入用户名'))
      }else if (!(regPass.test(value))) {
        callback(new Error('账号为3-20位数字字母组合'))
      }else {
        callback()
      }}

    function checkPassWord  (rule, value, callback) {
      // 正则表达式验证
      const regPass = /^[0-9a-z]{6,20}$/
      if(value===''){
        callback(new Error('请输入密码'))
      }else if (!(regPass.test(value))) {
        callback(new Error('密码为6-20位数字字母组合'))
      }else {
        callback()
      }}

      const FormRules = reactive({
        username: [{
          validator: checkUsername, trigger: 'blur'
        }],
        password: [{
          validator: checkPassWord, trigger: 'blur'
        }],
        checkPass: [{validator: validatePass2, trigger: 'blur'}],
      })

      function submitForm(formEl) {
      loading.value=true
        if (!formEl) return
        formEl.validate((valid) => {
          if (valid) {
            register()
          } else {
            ElMessage.error('注册信息有误请检查')
            loading.value=false
            return false

          }

        })
      }

      return {
        loading,
        submitForm,
        FormRules,
        resetReg,
        handleChange,
        register,
        temporary
      }
    }
  }
</script>

<style scoped>

</style>