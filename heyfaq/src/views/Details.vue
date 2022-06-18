<template>
  <div>


    <div>

      <el-page-header content="详情" icon="ArrowLeft" title="返回" @back="goBack"/>
    </div>
    <el-divider>
      <el-icon>
        <star-filled/>
      </el-icon>
    </el-divider>
    <div class="title" style="display: flex;
           justify-content: space-between;
           flex-wrap: wrap;">
      <h1> {{ info.title }}</h1>
      <div style="text-align: right">
        <h3> {{ info.author }}</h3>
        <div style="font-size: 12px">{{ Cmoment }}</div>
      </div>

    </div>
    <div>

    </div>
    <el-card style="width: 80%; margin: 0;border-radius: 15px; ">
      <div>
        <span>{{ info.ques }}</span>
      </div>
      <div class="demo-image__preview">
        <el-image
            v-show="info.quesurl"
            :preview-src-list="srcList1"
            :src="info.quesurl"
            fit="cover"
            style="width: 200px; height: auto"
        />
      </div>
    </el-card>

    <div v-show="info.solved==='t'" class="title" style="display: flex;
           justify-content: space-between;
           flex-wrap: wrap;">
      <h1> Answer</h1>
      <div style="text-align: right">
        <h3> {{ info.teacher }}</h3>
        <div style="font-size: 12px">{{ Umoment }}</div>
      </div>

    </div>
    <el-card v-show="info.solved==='t'" style="width: 80%; margin: 0;border-radius: 15px; ">
      <div>
        <span>{{ info.answer }}</span>
      </div>
      <div class="demo-image__preview">
        <el-image
            v-show="info.ansurl"
            :preview-src-list="srcList2"
            :src="info.ansurl"
            fit="cover"

            style="width: 200px; height: auto"
        />
      </div>
    </el-card>


    <div style="width:80%;margin-top: 10px;text-align:center">
      <el-button v-show="solveButton" round size="large" type="primary" @click="answer">解答</el-button>
      <el-button v-show="editButton" round size="large" type="primary" @click="answer">修改</el-button>
    </div>
    <transition
        appear
        enter-active-class="animate__animated animate__fadeInDown"
        leave-active-class="animate__animated animate__fadeOutUp"
        mode="out-in">
      <div v-show="answerStatus" style="width:80%;margin-top: 10px">
        <el-form :model="info" label-width="60px">
          <el-form-item label="答疑">
            <el-input
                v-model="info.answer"
                autosize
                placeholder="答疑结果,不方便阐述可上传图片（限一张）"
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
        <div style="text-align: center">
          <el-button :disabled="disable" type="primary" @click="saveAnswer">保存</el-button>
        </div>
      </div>
    </transition>
    <el-dialog v-model="dialogVisible">
      <img :src="dialogImageUrl" alt="" style="width: 100%"/>
    </el-dialog>


  </div>
</template>

<script>

import {computed, reactive, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {onMounted} from "@vue/runtime-core";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import dayjs from 'dayjs'

// 引入中文语言包
import 'dayjs/locale/zh-cn'

// 引入插件 dayjs/plugin/relativeTime
// 在你npm i daysj时，插件已经下载了。
// 具备计算相对时间的功能
import rTime from 'dayjs/plugin/relativeTime'
import cTime from 'dayjs/plugin/customParseFormat'
import moment from 'moment';

// 使用中文语言包。固定格式
dayjs.locale('zh-cn')

// 使用插件。固定格式dayjs.extend(插件)
dayjs.extend(rTime)
dayjs.extend(cTime)

moment.locale('zh-cn');

export default {
  name: "Details",
  setup() {
    const router = useRouter()
    const route = useRoute()
    const path = ref('')
    const pathName = ref('')
    const solveButton = ref(false)
    const sendInfo = ref(route.query.questionId);
    const info = reactive({})
    let srcList1 = ['']
    let srcList2 = ['']
    const fileList = ref([]);
    let fileInfo = {url: ''}
    const dialogVisible = ref(false)
    const dialogImageUrl = ref('')
    const answerStatus = ref(false)
    const editButton = ref(false)
    const Cmoment = computed(() => {
      if (info.createdTime !== null) {
        return dayjs().to(dayjs(info.createdTime))
      } else {
        return ''
      }
    })
    const Umoment = computed(() => {
      if (info.updateTime !== null) {
        return dayjs().to(dayjs(info.updateTime))
      } else {
        return ''
      }
    })

    //保存按钮禁用
    const disable = computed({
          get() {
            if (info.ansurl !== null) {
              return false
            } else return info.answer === ''||info.answer === null;
          },
          set(val) {
            info.ansurl = val
          }
        }
    )


    // const disable=computed(()=>info.answer === ''||info.answer === null||info.ansurl===null||info.ansurl==='')

    const current = reactive({})

    function getSingleQues() {
      request.get('/questions/getOne', {
        params: {
          id: sendInfo.value
        }
      }).then(res => {
        if (res.code == 0) {
          Object.assign(info, res.data)
          srcList1[0] = info.quesurl
          srcList2[0] = info.ansurl
        } else {
          ElMessage.error(res.msg)
        }
      })
    }

    function goBack() {
      router.back()
    }

    function handlePictureCardPreview(file) {
      dialogImageUrl.value = file.url
      dialogVisible.value = true
    }

    function handleRemove(file, fileList) {
      request.delete("/file/" + fileInfo.id).then(res => {
        // info.ansurl = ''
        console.log(disable);
        disable.value = null
      })
    }

    function handlerSuccess(res) {
      fileInfo = res.data
      info.ansurl = fileInfo.url
    }

    //显示答案编辑框
    function answer() {
      answerStatus.value = true
      solveButton.value = false
    }

    //保存/修改答案提交
    function saveAnswer() {
      request.put("/questions", info).then(res => {
        if (res.code === '0') {
          ElMessage({
            message: '发布成功',
            type: 'success'
          })
          answerStatus.value = false
          // info.solved = 't'
          getSingleQues()
          editButton.value = true
        } else {
          ElMessage.error(res.msg)
        }
      })
    }


    onMounted(() => {
      getSingleQues()
      request.post('/user/currentUser').then(res => {
        if (res.code === '0') {
          Object.assign(current, res.data)
        }
      })
    })
    return {
      disable,
      current,
      Umoment,
      Cmoment,
      editButton,
      saveAnswer,
      answer,
      answerStatus,
      dialogImageUrl,
      dialogVisible,
      handlerSuccess,
      handleRemove,
      handlePictureCardPreview,
      fileList,
      solveButton,
      srcList1,
      srcList2,
      pathName,
      goBack,
      info,
      path,
      name
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (from.path === '/') {
        from.name = localStorage.getItem('name')
      }
      vm.path = from.path

      switch (from.name) {
        case 'tobesolved':
          vm.solveButton = true;
          break;
        case 'solved':
          vm.editButton = true;
          break;
      }


    })
  }

}
</script>

<style scoped>

.title{
  width: 80%;
}

h1 {
  font-size: 30px;
  line-height: 35px;
  margin: 1em 0 .5em 0;
  padding: 0 0 0 10px;
  font-weight: normal;
  color: white;
  font-family: 'Hammersmith One', sans-serif;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.4);
  position: relative;
  color: #7e57c2;
}


h1:after {
  position: absolute;
  content: "";
  left: 0;
  top: 0;
  bottom: 0;
  width: 5px;
  border-radius: 2px;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.5),
  0 1px 1px rgba(255, 255, 255, 0.3);
}


h1:after {
  background: #7e57c2;
}

span {
  margin: 1em 0;
  padding: 0 0 0 2em;
  text-indent: -1.5em;
  font: normal 20px/2 Arial, Helvetica, sans-serif;
  color: #000;
  /*border: none;*/

  /*color: #000000;*/
  /*text-indent: 2em;*/
  /*-o-text-overflow: ellipsis;*/
  /*text-overflow: ellipsis;*/
}

.scrollbar {
  height: calc(100vh - 60px);
}

.demo-image__error .image-slot {
  font-size: 30px;
}

.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}

.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}

h3 {
  margin: 2em 0 0.5em 0;
  font-weight: 600;
  font-family: 'Titillium Web', sans-serif;
  position: relative;
  font-size: 18px;
  line-height: 20px;
  color: #788699;
  font-family: 'Muli', sans-serif;
}

.el-scrollbar__wrap {
  overflow: scroll;
  width: 110%;
  height: 120%;
}

.animate__animated.animate__fadeOutUp {
  --animate-duration: 0.3s;
}

.animate__animated.animate__fadeInDown {
  --animate-duration: 0.3s;
}

</style>