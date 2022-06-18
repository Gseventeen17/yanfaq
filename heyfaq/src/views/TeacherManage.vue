<template>
  <div class="manager">
    <div style="display:flex">
      <el-button round type="primary" @click="toSave">添加教师</el-button>
      <el-input v-model="input" clearable placeholder="昵称搜索" prefix-icon="Search"
                style="width:200px;margin-left:10px"/>

      <el-input v-model="sub" clearable placeholder="科目搜索" prefix-icon="Search"
                style="width:200px;margin-left:10px"/>
      <el-button circle icon="Search" style="margin-left:10px" @click="searchTeacher"/>
    </div>

    <!--  <Empty v-show="total===0"/>-->
    <div>
      <el-table v-loading="loading" :data="tableData" style="width: 100%" table-layout="fixed">
        <el-table-column label="用户名" prop="username"/>
        <!--      <el-table-column prop="password" label="密码" width="120" />-->
        <el-table-column label="昵称" prop="nickName"/>
        <el-table-column label="年龄" prop="age"/>
        <el-table-column label="科目" prop="subject"/>
        <el-table-column label="性别" prop="sex"/>
        <el-table-column label="注册日期" prop="registerTime" sortable/>
        <el-table-column label="信息更新日期" prop="updateTime" sortable/>

        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button size="small" @click="handleEdit( scope.row)">Edit</el-button>
            <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button size="small" type="danger">Delete
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination v-model:currentPage="currentPage4" v-model:page-size="pageSize4" :background="background"
                     :disabled="disabled" :page-sizes="[5, 10, 20, 30]" :small="small" :total="total"
                     layout="total, sizes, prev, pager, next, jumper" style="margin-top:10px" @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"/>
    </div>

    <el-dialog v-model="dialogVisible" center title="信息修改" width="30%">
      <el-form :model="singleInfo" label-position="top">
        <el-form-item label="用户名">
          <el-input v-model="singleInfo.username" autocomplete="off"/>
        </el-form-item>

        <!--      <el-form-item label="密码" prop="password">-->
        <!--        <el-input v-model="singleInfo.password"  />-->
        <!--      </el-form-item>-->

        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="singleInfo.nickName"/>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="singleInfo.age" :max="100" :min="1"/>
        </el-form-item>

        <el-form-item label="科目">
          <el-cascader v-model="preSubject" :options="courses.value" :props="props" :show-all-levels="false"
                       filterable/>
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <div>
            <el-radio v-model="singleInfo.sex" border label="男" size="large">男</el-radio>
            <el-radio v-model="singleInfo.sex" border label="女" size="large">女</el-radio>
            <el-radio v-model="singleInfo.sex" border label="未知" size="large">未知</el-radio>
          </div>
        </el-form-item>

        <el-form-item label="角色" prop="role">
          <el-select v-model="singleInfo.role" clearable placeholder="Select">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateUser"
        >Confirm</el-button
        >
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="saveDialogVisible" center draggable title="教师添加" width="23%">
      <el-form ref="resetReg" :model="saveData" :rules="FormRules" class="login-container"
               label-position="top"
               label-width="100px" style="max-width: 460px">
        <el-form-item label="username" prop="username">
          <el-input v-model="saveData.username"/>
        </el-form-item>

        <el-form-item label="password" prop="password">
          <el-input v-model="saveData.password" type="password"/>
        </el-form-item>

        <el-form-item label="nickName" prop="nickName">
          <el-input v-model="saveData.nickName"/>
        </el-form-item>

        <el-form-item label="age" prop="age">
          <el-input-number v-model="saveData.age" :max="100" :min="1"/>
        </el-form-item>

        <el-form-item label="科目">
          <el-cascader v-model="preSubject" :options="courses.value" :props="props" :show-all-levels="false"
                       filterable/>
        </el-form-item>

        <el-form-item label="sex" prop="sex">
          <div>
            <el-radio v-model="saveData.sex" border label="男" size="large">男</el-radio>
            <el-radio v-model="saveData.sex" border label="女" size="large">女</el-radio>
            <el-radio v-model="saveData.sex" border label="未知" size="large">未知</el-radio>
          </div>
        </el-form-item>

        <el-form-item style="width: 100%;display:inline">
          <el-button style="width: 50%;display:inline" type="primary" @click="submitForm(resetReg)">
            注册
          </el-button>
          <!--      @click.native.prevent="submitClick"-->
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {computed, reactive, ref} from "@vue/reactivity";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {onMounted} from "@vue/runtime-core";
import Empty from "@/views/Empty";

export default {
  name: "TeacherManage",
  components: {Empty},
  setup() {
    const loading = computed(() => {
      if (total.value === 0) {
        return true
      } else {
        return false
      }
    })
    let tableData = ref([])
    const preSubject = ref([])
    const editSubject = ref([])
    const finalSubject = computed(() => preSubject.value[preSubject.value.length - 1])
    const courses = ref([])
    const props = {
      label: 'label',
      value: 'label',
      children: 'children'
    }
    const saveDialogVisible = ref(false)
    const saveData = reactive({
      username: '',
      password: '',
      nickName: '',
      age: 1,
      sex: '',
      role: "T",
      subject: finalSubject
    })

    const options = [
      {
        value: 'U',
        label: '用户',
      },
      {
        value: 'T',
        label: '教师',
      }]


    const pageSize4 = ref(10)
    const currentPage4 = ref(1)
    const input = ref('')
    const sub = ref('')
    const small = ref(false)
    const background = ref(false)
    const disabled = ref(false)
    const dialogVisible = ref(false)
    const singleInfo = reactive({})
    const total = ref(0)
    const resetReg = ref()
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
      }]
    })

    function searchTeacher() {
      getTeacher(input.value, sub.value)
    }


    function getTeacher(val1, val2) {
      request.post("/user/allTeacher", {
        pageNum: currentPage4.value,
        pageSize: pageSize4.value,
        search: val1,
        subject: val2,
        role: 'T'
      }).then(res => {
        if (res.code === '0') {

          tableData.value = res.data.records
          total.value = res.data.total

        } else {
          ElMessage.error(res.msg)
        }

      })
    }

    function register() {

      request.post("/user/register", saveData).then(res => {
        if (res.code === '0') {
          resetReg.value.resetFields()
          ElMessage({
            message: '注册成功',
            type: 'success'
          })
          saveDialogVisible.value = false
          getTeacher(input.value, sub.value)
        } else {
          ElMessage.error(res.msg)
        }


      })
    }

    function submitForm(formEl) {
      if (!formEl) return
      formEl.validate((valid) => {
        if (valid) {
          register()

        } else {
          ElMessage.error('注册信息有误请检查')
          return false
        }
      })
    }

    function handleDelete(id) {

      request.delete("/user/" + id).then(res => {
        if (res.code === '0') {
          ElMessage({
            message: '删除成功',
            type: 'success'
          })
        } else {
          ElMessage.error(res.msg)
        }
        getTeacher(input.value, sub.value)
      })
    }

    function handleSizeChange(val) {
      pageSize4.value = val
      getTeacher(input.value, sub.value)
    }

    function handleCurrentChange(val) {
      currentPage4.value = val
      getTeacher(input.value, sub.value)
    }

    function handleEdit(row) {
      Object.assign(singleInfo, JSON.parse(JSON.stringify(row)))
      request.get("/courses/getPid", {
        params: {
          course: singleInfo.subject
        }
      }).then(res => {
        if (res.data === null) {
          preSubject.value = [singleInfo.subject]

        } else {
          preSubject.value = ['专业课', singleInfo.subject,]
        }

      })
      dialogVisible.value = true
    }

    function updateUser() {
      const length = preSubject.value.length;
      singleInfo.subject = preSubject.value[length - 1]

      request.put("/user", singleInfo).then(res => {
        if (res.code === '0') {
          ElMessage({
            message: '修改成功',
            type: 'success'
          })
          preSubject.value = []
        } else {
          ElMessage.error(res.msg)
        }
        dialogVisible.value = false
        getTeacher(input.value, sub.value)
      })
    }

    function toSave() {
      saveDialogVisible.value = true
      // request.get('/courses').then(res => {
      //   courses.value=res.data
      // })
    }

    onMounted(() => {
      getTeacher(input.value, sub.value)

    })


    return {
      sub,
      editSubject,
      toSave,
      props,
      courses,
      preSubject,
      resetReg,
      submitForm,
      saveData,
      FormRules,
      saveDialogVisible,
      options,
      loading,
      searchTeacher,
      singleInfo,
      dialogVisible,
      updateUser,
      getTeacher,
      tableData,
      handleEdit,
      handleDelete,
      pageSize4,
      currentPage4,
      input, small, background, disabled,
      handleSizeChange, handleCurrentChange, total
    }
  },
  beforeCreate() {
    request.get('/courses').then(res => {

      this.courses.value = res.data
    })
  }
}
</script>

<style scoped>

</style>