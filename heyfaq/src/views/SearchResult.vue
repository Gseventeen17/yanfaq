<template>
  <div>
    <Empty v-show="total===0"/>
    <SoloQuestion :questionReturn="questionReturn" :showDelete="false"/>
    <el-pagination v-show="total!==0" v-model:currentPage="currentPage4" v-model:page-size="pageSize4"
                   :page-sizes="[5, 10, 20, 30]"
                   :total="total"
                   layout="total, sizes, prev, pager, next, jumper" style="margin-top:10px" @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"/>
  </div>
</template>
<script>
import {computed, reactive, ref} from "@vue/reactivity";
import {useStore} from "vuex";
import {useRouter} from 'vue-router'
import Empty from "@/views/Empty";
import SoloQuestion from "@/views/SoloQuestion";

export default {
  name: "SearchResult",
  components: {Empty, SoloQuestion},
  setup() {
    const router = useRouter()
    const dialogVisible = ref(false);
    const store = useStore();
    const questionReturn = computed(() => store.state.ariticleList.records)
    const total = computed(() => store.state.ariticleList.total);
    const pageSize4 = ref(5);
    const currentPage4 = ref(1);
    const details = reactive({
      title: "",
      author: "",
      description: "",
      subject: "",
    });
    const selectSubject = computed({
      get() {
        return store.state.selectSubject
      },
      set(val) {
        store.state.selectSubject = val
      }
    });

    function updatePade() {
    }

    const sortW = ref('updateTime')

    function getQuestions() {
      if (selectSubject.value === '1') {
        store.dispatch("doSearch", {
          pageNum: currentPage4.value,
          pageSize: pageSize4.value,
          search: store.state.searchInput,
          sort: sortW.value
        });
      } else {
        store.dispatch("doSearchBySubject", {
          pageNum: currentPage4.value,
          pageSize: pageSize4.value,
          search: store.state.searchInput,
          sort: sortW.value
        });
      }

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
      handleCurrentChange,
      handleSizeChange,
      total,
      questionReturn,
      pageSize4,
      currentPage4,
      updatePade,
      details,
      dialogVisible,
    };
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (from.name === 'details') {
        vm.currentPage4 = vm.$store.state.SqState.pageNum
        vm.pageSize4 = vm.$store.state.SqState.pageSize
        vm.getQuestions()
        vm.$store.commit('SETSQ', {})
      }
    })

  },
  beforeRouteLeave(to, from) {
    if (to.name === 'details') {
      this.$store.commit('SETSQ', {
        pageNum: this.currentPage4,
        pageSize: this.pageSize4
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
