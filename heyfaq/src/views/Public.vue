<template>
  <div>
    <el-card style="width: 40%; margin: 10px;border-radius: 15px; margin: 0px auto;">
      <template #header>
        <div class="card-header">
          <span>问题发布</span>

        </div>
      </template>
      <el-form ref="form" :model="problems" :rules="FormRules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="problems.title" placeholder="简要概括下你的问题(●'◡'●)"></el-input>
        </el-form-item>
        <el-form-item label="科目" prop="subject">
          <el-cascader v-model="preSubject" :options="courses.value" :props="props" :show-all-levels="false"/>
        </el-form-item>
        <el-form-item label="内容" prop="ques">
          <el-input
              v-model="problems.ques"
              autosize
              placeholder="问题内容,不方便阐述可上传图片（限一张）"
              type="textarea"
          />
        </el-form-item>

        <el-form-item>
          <el-upload ref="upload"
                     :auto-upload="true"
                     :file-list="fileList"
                     :limit="1"
                     :on-preview="handlePictureCardPreview"
                     :on-remove="handleRemove"
                     :on-success="handlerSuccess"
                     action="http://localhost:9090/file/upload"
                     list-type="picture-card"
          >
            <el-icon>
              <Plus/>
            </el-icon>
          </el-upload>
        </el-form-item>


      </el-form>
      <el-dialog v-model="dialogVisible">
        <img :src="dialogImageUrl" alt="" style="width: 100%"/>
      </el-dialog>
      <div style="text-align: center">
        <el-button type="primary" @click="submitForm(form)">保存</el-button>
      </div>
    </el-card>
  </div>

</template>

<script>
import {computed, reactive, ref} from 'vue'
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

export default {
  name: "Public",
  setup() {
    const router = useRouter();
    const preSubject = ref([])
    const resetReg = ref()
    const finalSubject = computed(() => preSubject.value[preSubject.value.length - 1])
    const courses = ref([])
    const problems = reactive({
      title: '',
      ques: '',
      subject: finalSubject,
      quesurl: ''
    })


    const form = reactive({})

    const props = {
      label: 'label',
      value: 'label',
      children: 'children'
    }
    const FormRules = reactive({
      ques: [{
        required: true,
        message: '请输入内容',
        trigger: 'blur'
      }
      ],
      title: [{
        required: true,
        message: '请输入标题',
        trigger: 'blur'
      }],
      subject: [{
        required: true,
        message: '请输入科目',
        trigger: 'blur'
      }
      ],

    })

    const fileList = ref([]);
    let fileInfo = {url: ''}
    const upload = ref()
    const dialogImageUrl = ref('')
    const dialogVisible = ref(false)

    function handlePictureCardPreview(file) {
      dialogImageUrl.value = file.url
      dialogVisible.value = true
    }

    function newQu() {

      problems.quesurl = fileInfo.url
      request.post("/questions/newquestions", problems).then(res => {
        if (res.code === '0') {
          ElMessage({
            message: '发布成功',
            type: 'success'
          })
          preSubject.value = []
          fileInfo.url = ''
          problems.title = ''
          problems.ques = ''
          problems.quesurl = ''
          fileList.value = []

        }
      })
    }

    function submitForm(formEl) {
      if (!formEl) return
      formEl.validate((valid) => {
        if (valid) {
          newQu()
        } else {
          ElMessage.error('问题信息有误请检查')
          return false
        }
      })
    }


    function handleRemove(file, fileList) {
      request.delete("/file/" + fileInfo.id).then(res => {
        problems.quesurl = ''
      })
    }

    function handlerSuccess(res) {
      fileInfo = res.data
      problems.quesurl = fileInfo.url
    }


    return {
      resetReg,
      submitForm,
      FormRules,
      preSubject,
      newQu,
      handlerSuccess,
      handleRemove,
      fileList,
      dialogImageUrl, dialogVisible,
      handlePictureCardPreview,
      props,
      problems,
      courses,
      form,
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
.card-header {
  display: flex;
  justify-content: center;
  align-items: center;
}

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