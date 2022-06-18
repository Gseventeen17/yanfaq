<template>
  <div class="manager">
    <div style="display:flex">
      <el-input v-model="input" clearable placeholder="昵称搜索" prefix-icon="Search"
                style="width:200px;margin-left:10px"/>
      <el-button circle icon="Search" style="margin-left:10px" @click="searchUser"/>
    </div>

    <div>
      <el-table v-loading="loading" :data="tableData" style="width: 100%" table-layout="fixed">
        <!--      <Empty v-show="total===0"/>-->
        <el-table-column label="用户名" prop="username"/>
        <!--      <el-table-column prop="password" label="密码" width="120" />-->
        <el-table-column label="昵称" prop="nickName"/>
        <el-table-column label="年龄" prop="age"/>
        <el-table-column label="性别" prop="sex"/>
        <el-table-column label="注册日期" prop="registerTime" sortable/>
        <el-table-column label="信息更新日期" prop="updateTime" sortable/>

        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">Edit</el-button>
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


          <el-form-item v-show="singleInfo.role=='T'" label="科目">
            <el-cascader v-model="preSubject" :options="courses.value" :props="props" :show-all-levels="false"
                         filterable/>
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
  </div>
</template>

<script>
import {computed, reactive, ref} from "@vue/reactivity";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {onMounted} from "@vue/runtime-core";
import Empty from "@/views/Empty";

export default {
  name: "UserManage",
  components: {Empty},
  setup() {
    let tableData = ref([])
    const loading = computed(() => {
      return total.value === 0;
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
    const preSubject = ref([])
    const courses = ref([])
    const props = {
      label: 'label',
      value: 'label',
      children: 'children'
    }

    function searchUser() {
      getUser(input.value)
    }

    function handleEdit(row) {
      Object.assign(singleInfo, JSON.parse(JSON.stringify(row)))
      dialogVisible.value = true

    }

    function getUser(val) {
      request.post("/user/all", {
        pageNum: currentPage4.value,
        pageSize: pageSize4.value,
        search: val,
        role: 'U'

      }).then(res => {
        if (res.code === '0') {

          //   res.data.forEach(e => {
          //   tableData.push(e);
          // });
          tableData.value = res.data.records
          total.value = res.data.total

        } else {
          ElMessage.error(res.msg)
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
        getUser(input.value)
      })
    }

    function handleSizeChange(val) {
      pageSize4.value = val
      getUser(input.value)
    }

    function handleCurrentChange(val) {
      currentPage4.value = val
      getUser(input.value)
    }

    function updateUser() {
      request.put("/user", singleInfo).then(res => {
        if (res.code === '0') {
          ElMessage({
            message: '修改成功',
            type: 'success'
          })
        } else {
          ElMessage.error(res.msg)
        }
        dialogVisible.value = false
        getUser(input.value)
      })
    }

    onMounted(() => {
      getUser(input.value)
    })
    const loadingIcon = `<path d="M512 42.666667c-25.6 0-42.666667 17.066667-42.666667 42.666666v170.666667c0 25.6 17.066667 42.666667 42.666667 42.666667s42.666667-17.066667 42.666667-42.666667V85.333333c0-25.6-17.066667-42.666667-42.666667-42.666666z m0 682.666666c-25.6 0-42.666667 17.066667-42.666667 42.666667v170.666667c0 25.6 17.066667 42.666667 42.666667 42.666666s42.666667-17.066667 42.666667-42.666666v-170.666667c0-25.6-17.066667-42.666667-42.666667-42.666667zM238.933333 179.2c-17.066667-17.066667-42.666667-12.8-59.733333 4.266667-17.066667 17.066667-17.066667 42.666667 0 59.733333L298.666667 362.666667c17.066667 17.066667 42.666667 17.066667 59.733333 4.266666 17.066667-17.066667 17.066667-42.666667 4.266667-59.733333l-4.266667-4.266667-119.466667-123.733333zM725.333333 661.333333c-17.066667-17.066667-42.666667-12.8-59.733333 4.266667-17.066667 17.066667-17.066667 42.666667 0 59.733333l119.466667 119.466667c17.066667 17.066667 42.666667 17.066667 59.733333 4.266667s17.066667-42.666667 4.266667-59.733334l-4.266667-4.266666-119.466667-123.733334zM298.666667 512c0-25.6-17.066667-42.666667-42.666667-42.666667H85.333333c-25.6 0-42.666667 17.066667-42.666666 42.666667s17.066667 42.666667 42.666666 42.666667h170.666667c25.6 0 42.666667-17.066667 42.666667-42.666667z m640-42.666667h-170.666667c-25.6 0-42.666667 17.066667-42.666667 42.666667s17.066667 42.666667 42.666667 42.666667h170.666667c25.6 0 42.666667-17.066667 42.666666-42.666667s-17.066667-42.666667-42.666666-42.666667zM302.933333 661.333333l-119.466666 119.466667c-17.066667 17.066667-17.066667 42.666667-4.266667 59.733333s42.666667 17.066667 59.733333 4.266667l4.266667-4.266667 119.466667-119.466666c17.066667-17.066667 17.066667-42.666667 4.266666-59.733334s-46.933333-17.066667-64 0z m388.266667-285.866666c12.8 0 21.333333-4.266667 29.866667-12.8l119.466666-119.466667c17.066667-17.066667 17.066667-42.666667 4.266667-59.733333-17.066667-17.066667-42.666667-17.066667-59.733333-4.266667l-4.266667 4.266667-119.466667 119.466666c-17.066667 17.066667-17.066667 42.666667 0 59.733334 8.533333 8.533333 21.333333 12.8 29.866667 12.8z" p-id="1945"></path>`


    return {
      props,
      courses,
      preSubject,
      loadingIcon,
      options,
      loading,
      searchUser,
      singleInfo,
      dialogVisible,
      updateUser,
      getUser,
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