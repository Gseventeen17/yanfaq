<template>
  <div>
    <!--  <div>-->
    <!--    <el-breadcrumb separator="/">-->
    <!--      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
    <!--      <el-breadcrumb-item>信息修改</el-breadcrumb-item>-->
    <!--    </el-breadcrumb>-->
    <!--  </div>-->

    <el-card style="width: 40%; margin: 10px;border-radius: 15px; margin: 0px auto;">
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model.number="form.age" :max="100" :min="1"/>
        </el-form-item>
        <el-form-item label="性别">

          <div>
            <el-radio v-model="form.sex" border label="男" size="large">男</el-radio>
            <el-radio v-model="form.sex" border label="女" size="large">女</el-radio>
            <el-radio v-model="form.sex" border label="未知" size="large">未知</el-radio>
          </div>

        </el-form-item>

      </el-form>
      <div style="text-align: center">
        <el-button round type="primary" @click="update">保存</el-button>
      </div>
    </el-card>
  </div>

</template>

<script>
import {reactive} from 'vue'
import {onMounted} from "@vue/runtime-core";
import {ElMessage} from "element-plus";
import request from "@/utils/request";
import {useStore} from "vuex";

export default {
  name: "Person",
  setup() {
    // let form=reactive(JSON.parse(localStorage.getItem('user'))||{})
    const form = reactive({
      token: JSON.parse(localStorage.getItem('user')).token
    })
    const store = useStore();

    function update() {
      form.password = undefined
      request.put("/user", form).then(res => {

        if (res.code === '0') {
          ElMessage({
            type: "success",
            message: "更新成功"
          })
          localStorage.setItem("user", JSON.stringify(form))
          store.commit('UPDATENICK', form.nickName)

        } else {
          ElMessage.error(res.msg)
        }
      })
    }

    onMounted(() => {
      request.post('/user/currentUser').then(res => {
        delete res.data.token
        Object.assign(form, res.data)
      })
    })


    return {
      form,
      update,

    }
  },
  // created() {
  //   if(localStorage.getItem('user')===null){
  //     ElMessage.error("请先登录！")
  //   }else {
  //     let str = localStorage.getItem("user")
  //     // form.value=str
  //     Object.assign(this.form ,JSON.parse(str))
  //   }
  //   console.log(this.form)
  // }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>