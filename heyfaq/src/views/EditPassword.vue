<template>
  <div>
    <!--  <div>-->
    <!--    <el-breadcrumb separator="/">-->
    <!--      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
    <!--      <el-breadcrumb-item>密码修改</el-breadcrumb-item>-->
    <!--    </el-breadcrumb>-->
    <!--  </div>-->

    <el-card style="width: 40%; margin: 10px;border-radius: 15px; margin: 0px auto;">
      <el-form ref="resetReg" :model="passForm" :rules="FormRules" label-width="80px">
        <el-form-item label="原密码" prop="origin">
          <el-input v-model="passForm.origin"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="passForm.newPass"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input v-model="passForm.checkPass"></el-input>
        </el-form-item>

      </el-form>
      <div style="text-align: center">
        <el-button round type="primary" @click="submitForm(resetReg)">保存</el-button>
      </div>
    </el-card>
  </div>

</template>

<script>
import {reactive, ref} from "vue";
// import {ref} from "vue/dist/vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request";
import {useRouter} from "vue-router";
import {useStore} from "vuex";

export default {
  name: "EditPassword",
  setup() {
    const router = useRouter()
    const store = useStore()
    const resetReg = ref()
    const passForm = reactive({
      origin: '',
      newPass: '',
      checkPass: ''
    })


    function validatePass(rule, value, callback) {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== passForm.newPass) {
        callback(new Error("密码不一致"))
      } else if (value === passForm.origin) {
        callback(new Error("新旧密码不能相同"))
      } else {
        callback()
      }
    }

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

    function updatePass() {
      request.post('/user/updatePass', {oldPass: passForm.origin, newPass: passForm.newPass}).then(res => {
        if (res.code === '0') {
          ElMessage({
            message: '修改成功，请重新登陆',
            type: 'success'
          })
          localStorage.clear()
          router.push({
            name: 'index'
          })
          store.commit('CLOSERAL')
          store.commit('UPDATENICK', '')
          store.commit('UPDATEROLE', '')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }

    const FormRules = reactive({
      newPass:[{validator: checkPassWord, trigger: 'blur'}],
      checkPass: [{validator: validatePass, trigger: 'blur'}]
    })


    function submitForm(formEl) {

      if (!formEl) return
      formEl.validate((valid) => {
        if (valid) {
          updatePass()

        } else {

          ElMessage.error('信息有误请检查')
          return false
        }
      })
    }

    return {
      submitForm,
      resetReg,
      passForm,
      FormRules
    }
  }
}
</script>

<style scoped>

</style>