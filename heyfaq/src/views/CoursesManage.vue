<template>
  <div class="manager">
    <div style="display:flex">
      <el-button round type="primary" @click="toSave">添加课程</el-button>
      <el-input v-model="input" clearable placeholder="名称搜索" prefix-icon="Search"
                style="width:200px;margin-left:10px"/>
      <el-button circle icon="Search" style="margin-left:10px" @click="searchCourse"/>
    </div>
    <!--  <el-empty v-show="total===0" description="无数据" image="http://localhost:9090/file/ace73cfb176349a9ad4c260693172eb4.png" />-->
    <!--  <Empty v-show="total===0"/>-->
    <div>
      <el-table v-loading="loading" :data="tableData" style="width: 100%" table-layout="fixed">
        <el-table-column label="代号" prop="value"/>
        <el-table-column label="名称" prop="label"/>
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
                     layout="total, sizes, prev, pager, next, jumper" style="margin-top:10px"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"/>
    </div>

    <el-dialog v-model="dialogVisible" center title="信息修改" width="23%">
      <el-form :model="singleInfo" label-position="top">
        <el-form-item label="代号">
          <el-input v-model="singleInfo.value" autocomplete="off"/>
        </el-form-item>

        <el-form-item label="名称" prop="nickName">
          <el-input v-model="singleInfo.label"/>
        </el-form-item>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="updateCourse"
        >Confirm</el-button
        >
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="saveDialogVisible" center draggable title="课程添加" width="23%">
      <el-form ref="resetReg" :model="saveData" :rules="FormRules" class="login-container"
               label-position="top"
               label-width="100px" style="max-width: 460px">
        <el-form-item label="代号" prop="value">
          <el-input v-model="saveData.value"/>
        </el-form-item>

        <el-form-item label="名称" prop="label">
          <el-input v-model="saveData.label"/>
        </el-form-item>


        <el-form-item style="width: 100%;display:inline">
          <el-button style="width: 50%;display:inline" type="primary" @click="submitForm(resetReg)">
            添加
          </el-button>
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
  name: "CoursesManage",
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
    const resetReg = ref()
    const FormRules = reactive({
      value: [{
        required: true,
        message: '请输入代号',
        trigger: 'blur'
      }],
      label: [{
        required: true,
        message: '请输入名称',
        trigger: 'blur'
      }]
    })
    const saveData = reactive({
      value: '',
      label: '',
      pid: 5
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
    const small = ref(false)
    const background = ref(false)
    const disabled = ref(false)
    const dialogVisible = ref(false)
    const singleInfo = reactive({})
    const total = ref(0)
    const saveDialogVisible = ref(false)

    function searchCourse() {
      getCourse(input.value)
    }

    function handleEdit(row) {

      Object.assign(singleInfo, JSON.parse(JSON.stringify(row)))
      dialogVisible.value = true
    }

    function getCourse(val) {
      request.get("/courses/getProfession", {
        params: {
          pageNum: currentPage4.value,
          pageSize: pageSize4.value,
          search: val,
          role: 'U'
        }
      }).then(res => {
        if (res.code === '0') {
          tableData.value = res.data.records
          total.value = res.data.total
        } else {
          ElMessage.error(res.msg)
        }
      })
    }

    function handleDelete(id) {
      request.delete("/courses/" + id).then(res => {
        if (res.code === '0') {
          ElMessage({
            message: '删除成功',
            type: 'success'
          })
        } else {
          ElMessage.error(res.msg)
        }
        getCourse(input.value)
      })
    }

    function handleSizeChange(val) {
      pageSize4.value = val
      getCourse(input.value)
    }

    function handleCurrentChange(val) {
      currentPage4.value = val
      getCourse(input.value)
    }

    function updateCourse() {
      request.put("/courses", singleInfo).then(res => {
        if (res.code === '0') {
          ElMessage({
            message: '修改成功',
            type: 'success'
          })
        } else {
          ElMessage.error(res.msg)
        }
        dialogVisible.value = false
        getCourse(input.value)
      })
    }

    function addCourse() {
      request.post("/courses/saveCourse", saveData).then(res => {
        if (res.code === '0') {
          resetReg.value.resetFields()
          ElMessage({
            message: '添加成功',
            type: 'success'
          })
          saveDialogVisible.value = false
          getCourse(input.value)
        } else {
          ElMessage.error(res.msg)
        }


      })
    }

    function submitForm(formEl) {
      if (!formEl) return
      formEl.validate((valid) => {
        if (valid) {
          addCourse()
        } else {
          ElMessage.error('课程信息有误请检查')
          return false
        }
      })
    }

    onMounted(() => {
      getCourse(input.value)
    })

    function toSave() {
      saveDialogVisible.value = true
    }

    return {
      resetReg,
      submitForm,
      FormRules,
      saveData,
      saveDialogVisible,
      toSave,
      options,
      loading,
      searchCourse,
      singleInfo,
      dialogVisible,
      updateCourse,
      getCourse,
      tableData,
      handleEdit,
      handleDelete,
      pageSize4,
      currentPage4,
      input, small, background, disabled,
      handleSizeChange, handleCurrentChange, total
    }
  }
}
</script>

<style scoped>

</style>