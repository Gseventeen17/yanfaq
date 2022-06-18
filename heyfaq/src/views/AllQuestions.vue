<template>
  <div>
    <div style="width: 80%;height: 20px">
      <el-select v-model="selector" class="m-2" placeholder="Select" size="small" style="float: right;width: 100px"
                 @change="reSort">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
    </div>
    <Empty v-show="total===0"/>
    <div>
      <SoloQuestion :questionReturn="questionReturn" :showDelete="false"/>
    </div>

    <div v-show="total!==0"
         style="margin-top:10px">
      <el-pagination
          v-model:currentPage="currentPage4"
          v-model:page-size="pageSize4"
          :page-sizes="[5, 10, 20, 30]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>
    </div>
  </div>
</template>
<script>
import 'animate.css'
import {computed, reactive, ref} from "@vue/reactivity";
import {useStore} from "vuex";
import {useRouter} from 'vue-router'
import SoloQuestion from "@/views/SoloQuestion";
import Empty from "@/views/Empty";

export default {
  name: "AllQuestions",
  components: {Empty, SoloQuestion},
  setup() {
    const selector = ref('createdTime')
    const options = ref([{
      label: '最新发布',
      value: 'createdTime'
    }, {
      label: '最新解答',
      value: 'updateTime'
    }])
    const router = useRouter();
    const dialogVisible = ref(false);
    const store = useStore();
    const questionReturn = computed(() => store.state.ariticleList.records);
    const total = computed(() => store.state.ariticleList.total);
    const pageSize4 = ref(5)
    const currentPage4 = ref(1)
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
      router.push({
        name: "details",
        query: {
          questionId: ques.id
        }
      });
    }

    function getQuestions() {
      store.dispatch("doSearch", {
        pageNum: currentPage4.value,
        pageSize: pageSize4.value,
        search: '',
        sort: selector.value
      });
    }

    function reSort() {
      currentPage4.value = 1
      getQuestions()
    }

    function handleSizeChange(val) {
      pageSize4.value = val
      getQuestions()
    }

    function handleCurrentChange(val) {
      currentPage4.value = val
      getQuestions()
    }

    return {
      getQuestions,
      reSort,
      selector, options,
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
      vm.currentPage4 = vm.$store.state.AqState.pageNum || 1
      vm.pageSize4 = vm.$store.state.AqState.pageSize || 5
      vm.selector = vm.$store.state.AqState.sort || 'createdTime'
      vm.getQuestions()
    })
  },
  beforeRouteLeave(to, from) {
    if (to.name !== 'index') {
      this.$store.commit('SETAQ', {
        pageNum: this.currentPage4,
        pageSize: this.pageSize4,
        sort: this.selector
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
