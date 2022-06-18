<template>

  <div>
    <!--    <div>-->
    <!--    <el-breadcrumb separator="/">-->
    <!--      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
    <!--      <el-breadcrumb-item>已解答</el-breadcrumb-item>-->
    <!--    </el-breadcrumb>-->
    <!--  </div>-->
    <el-input v-model="input" clearable placeholder="搜索内容，按下回车以搜索" style="width: 80%;margin-top: 10px"
              @keydown.enter="getSolved"/>
    <!--  <el-empty v-show="total===0" description="无数据" image="http://localhost:9090/file/ace73cfb176349a9ad4c260693172eb4.png" />-->

    <Empty v-show="total===0"/>
    <!--  <el-card-->
    <!--      @click="showDetails(ques)"-->
    <!--      v-for="ques in questionReturn"-->
    <!--      :key="ques.id"-->
    <!--      :body-style="{ padding: '20px' }"-->
    <!--      style="width: 80%; margin-top: 10px"-->
    <!--      shadow="hover"-->
    <!--      class="box-card"-->
    <!--  >-->
    <!--    <template #header>-->
    <!--      <div class="card-header">-->
    <!--        <span>{{ ques.title }}</span>-->
    <!--        <span>{{ ques.subject }}</span>-->
    <!--      </div>-->
    <!--    </template>-->
    <!--    <div style="display: flex;-->
    <!--           justify-content: space-between;-->
    <!--           flex-wrap: wrap;">-->
    <!--      <div class="description">-->
    <!--        {{ ques.ques }}-->


    <!--      </div>-->

    <!--    </div>-->

    <!--  </el-card>-->

    <SoloQuestion :questionReturn="questionReturn" :showDelete="false"/>


    <el-pagination v-show="total!==0" v-model:current-page="currentPage4" v-model:page-size="pageSize4"
                   :background="background"
                   :disabled="disabled" :page-sizes="[5, 10, 20, 30]" :small="small" :total="total"
                   layout="total, sizes, prev, pager, next, jumper" style="margin-top:10px" @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"/>

    <el-dialog v-model="dialogVisible" :title="details.title" draggable>
      <el-descriptions :column="2" title="问题详情">
        <el-descriptions-item label="问题作者">{{
            details.author
          }}
        </el-descriptions-item>
        <el-descriptions-item label="所属科目">{{
            details.subject
          }}
        </el-descriptions-item>
        <el-descriptions-item label="问题描述">{{
            details.description
          }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>
<script>
import {computed, reactive, ref} from "@vue/reactivity";
import {useStore} from "vuex";
import {onMounted} from "@vue/runtime-core";
import {onBeforeRouteLeave, useRouter} from 'vue-router'
import SoloQuestion from "@/views/SoloQuestion";
import Empty from "@/views/Empty";

export default {
  name: "Solved",
  components: {Empty, SoloQuestion},
  setup() {
    const router = useRouter()
    const dialogVisible = ref(false);
    const store = useStore();
    const questionReturn = computed(() => store.state.Solved.records);
    const total = computed(() => store.state.Solved.total);
    const pageSize4 = ref(5)
    const currentPage4 = ref(1)
    const input = ref('')
    const small = ref(false)
    const background = ref(false)
    const disabled = ref(false)
    const details = reactive({
      title: "",
      author: "",
      description: "",
      subject: "",
    });


    function showDetails(ques) {
      details.title = ques.title;
      details.author = ques.author;
      details.description = ques.ques;
      details.subject = ques.subject;
      dialogVisible.value = false;
      router.push({
        name: "details",
        params: {
          questionInfo: JSON.stringify(ques)
        }
      });
    }


    function getSolved() {
      store.dispatch("findSolved", {
        pageNum: currentPage4.value,
        pageSize: pageSize4.value,
        search: input.value
      });
    }

    function handleSizeChange(val) {
      pageSize4.value = val
      getSolved()
    }

    function handleCurrentChange(val) {
      currentPage4.value = val
      getSolved()
    }

    onBeforeRouteLeave((to, from) => {
      store.state.currentSearch = false
    })

    onMounted(() => {
      // getSolved()
    })

    return {
      input,
      getSolved,
      small, background, disabled, total, handleSizeChange, handleCurrentChange,
      questionReturn,
      pageSize4,
      currentPage4,
      details,
      dialogVisible,
      showDetails,
    };
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      // if(from.name==='details'){
      vm.currentPage4 = vm.$store.state.SolvedqState.pageNum || 1
      vm.pageSize4 = vm.$store.state.SolvedqState.pageSize || 5
      vm.input = vm.$store.state.SolvedqState.search || ''
      vm.getSolved()
      //   vm.$store.commit('SETMQ',{})
      // }else{
      //   vm.getSolved()
      // }
    })

  },
  beforeRouteLeave(to, from) {
    localStorage.setItem('name', 'solved')
    if (to.name !== 'index') {
      this.$store.commit('SETSOLVEDQ', {
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

.description {
  line-height: normal;
  width: 100%;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
</style>
