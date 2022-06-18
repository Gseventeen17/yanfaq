<template>
  <transition
      enter-active-class="animate__animated animate__fadeIn"
      leave-active-class="animate__animated animate__fadeOut"
      mode="out-in">
    <div>
      <el-card
          v-for="ques in questionReturn"
          :key="ques.id"
          class="box-card"
          shadow="hover"
          style="width: 80%; margin-top: 10px;border-radius: 15px; "
          @click="showDetails(ques)"
      >
        <template #header>
          <div class="card-header">
            <span>{{ ques.title }}</span>
            <span>
          <el-tag
              v-show="ques.solved==='t'"
              class="mx-1"
              effect="dark"
          >
       已解决
    </el-tag>
          <el-tag
              v-show="ques.solved==='f'"
              class="mx-1"
              effect="dark"
              type="danger"
          >
       未解决
    </el-tag>
          {{ ques.subject }}
        </span>


          </div>
        </template>
        <div style="display: flex;
           justify-content: space-between;
           flex-wrap: wrap;">
          <div style="height:20px;width:70%;text-overflow:ellipsis; white-space:nowrap; overflow:hidden;">{{
              ques.ques
            }}
          </div>
          <div>
            <el-button v-show="showDelete" round type="danger" @click.stop="deleteQues(ques)">删除</el-button>
          </div>

        </div>

      </el-card>
    </div>
  </transition>
</template>

<script>
import {useRouter} from "vue-router";
import request from "@/utils/request";
import {computed} from "vue";
import {useStore} from "vuex";
import {ElMessage} from "element-plus";


export default {
  name: "SoloQuestion",
  props: ['questionReturn', 'showDelete'],
  emits: ["refresh"],
  setup(props, context) {
    const store = useStore();
    const questionsList = props.questionReturn
    const router = useRouter()
    const localUser = computed(() => {
      if (store.state.nickName === '') {
        if (localStorage.getItem('user') === null) {
          return {nickName: ''}
        } else {
          return JSON.parse(localStorage.getItem('user'))
        }
      } else {
        return {nickName: store.state.nickName}
      }
    });

    function showDetails(ques) {
      // router.push({
      //   name: "details",
      //   params:{
      //     questionInfo:JSON.stringify(ques)
      //   }
      // });
      router.push({
        name: "details",
        query: {
          questionId: ques.id
        }
      });
    }

    function deleteQues(ques) {
      request.delete("/questions/" + ques.id).then(res => {
        if (res.code === '0') {
          context.emit('refresh')
          ElMessage({
            message: '删除成功',
            type: 'success'
          })
        } else {
          ElMessage.error(res.msg)
        }
      })

    }

    return {
      localUser,
      questionsList,
      showDetails,
      deleteQues
    }
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}


.box-card {
  width: 480px;
}
</style>