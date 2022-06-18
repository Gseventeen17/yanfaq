<template>

  <div>
    <el-input v-model="input" clearable placeholder="搜索内容，按下回车以搜索" style="width: 80%;margin-top: 10px"
              @keydown.enter="getMyQuestions"/>

    <Empty v-show="total===0"/>


    <SoloQuestion :questionReturn="questionReturn" :showDelete="true" @refresh="getMyQuestions"/>


    <el-pagination v-show="total!==0" v-model:current-page="currentPage4" v-model:page-size="pageSize4"
                   :background="background"
                   :disabled="disabled" :page-sizes="[5, 10, 20, 30]" :small="small" :total="total"
                   layout="total, sizes, prev, pager, next, jumper" style="margin-top:10px" @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"/>
  </div>

</template>
<script>
import {computed, ref} from "@vue/reactivity";
import request from "@/utils/request";
import {useStore} from "vuex";
import {onMounted} from "@vue/runtime-core";
import {onBeforeRouteLeave, useRouter} from 'vue-router'
import SoloQuestion from "@/views/SoloQuestion";
import Empty from "@/views/Empty";
import {ElMessage} from "element-plus";

export default {
  name: "MyQuestions",
  components: {Empty, SoloQuestion},
  setup() {
    const router = useRouter()
    const dialogVisible = ref(false);
    const store = useStore();
    const questionReturn = computed(() => store.state.myquestions.records);
    const total = computed(() => store.state.myquestions.total);
    const pageSize4 = ref(5)
    const currentPage4 = ref(1)
    const input = ref('')
    const small = ref(false)
    const background = ref(false)
    const disabled = ref(false)


    function showDetails(ques) {
      router.push({
        name: "details",
        params: {
          questionInfo: JSON.stringify(ques)
        }
      });
    }


    function getMyQuestions() {

      store.dispatch("findMine", {
        pageNum: currentPage4.value,
        pageSize: pageSize4.value,
        search: input.value,
      });
    }

    function handleSizeChange(val) {
      pageSize4.value = val
      getMyQuestions()
    }

    function handleCurrentChange(val) {
      currentPage4.value = val
      getMyQuestions()
    }

    onBeforeRouteLeave((to, from) => {
      store.state.currentSearch = false
    })

    function deleteQues(ques) {
      request.delete("/questions/" + ques.id).then(res => {
        if (res.code === '0') {
          ElMessage({
            type: "success",
            message: '删除成功'
          })
        } else {
          ElMessage.error(res.msg)
        }
        getMyQuestions()
      })
    }

    onMounted(() => {
      // getMyQuestions()
    })

    return {
      input,
      getMyQuestions,
      deleteQues,
      small, background, disabled, total, handleSizeChange, handleCurrentChange,
      questionReturn,
      pageSize4,
      currentPage4,
      dialogVisible,
      showDetails,
    };
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.currentPage4 = vm.$store.state.MqState.pageNum || 1
      vm.pageSize4 = vm.$store.state.MqState.pageSize || 5
      vm.input = vm.$store.state.MqState.search || ''
      vm.getMyQuestions()
    })

  },
  beforeRouteLeave(to, from) {
    if (to.name !== 'index') {
      this.$store.commit('SETMQ', {
        pageNum: this.currentPage4,
        pageSize: this.pageSize4,
        search: this.input
      })

    }
  }
};
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
