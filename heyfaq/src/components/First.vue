<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <el-row :gutter="20">

          <el-col :span="3">
            <div class="grid-content bg-purple" @click="indexmain">
              <span style="color: #ffffff; font-size: 30px">MiniFaq</span>
            </div>
          </el-col>

          <el-col :span="5">
            <div class="grid-content bg-purple">
              <!--              <el-input v-model="input" clearable placeholder="点击以搜索全部内容" @keydown.enter="doSearch"/>-->
              <el-input
                  v-model="input"
                  class="input-with-select"
                  clearable
                  placeholder="点击以搜索全部内容"
              >
                <template #prepend>
                  <el-select v-model="selectSubject" placeholder="Select" style="width: 80px">
                    <el-option label="内容" value="1"/>
                    <el-option label="科目" value="2"/>
                  </el-select>
                </template>
              </el-input>
              <el-button round style="margin-left: 20px" type="primary" @click="doSearch">
                <el-icon><Search /></el-icon>
              </el-button>
            </div>
          </el-col>

          <el-col :span="12">
            <div class="grid-content bg-purple"></div>
          </el-col>

          <el-col id="regis" :span="2">
            <div class="grid-content bg-purple">
              <el-button v-show="isShow" round type="primary" @click="loginDialog = !loginDialog">登录</el-button>
            </div>
          </el-col>

          <el-col :span="2">
            <div class="grid-content bg-purple">
              <el-button v-show="isShow" round type="primary" @click="registerDialog = !registerDialog">注册</el-button>
              <el-dropdown v-if="!isShow">
                <el-button round type="primary">
                  {{ localUser.nickName }}
                  <el-icon class="el-icon--right">
                    <arrow-down/>
                  </el-icon>
                </el-button>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item divided>
                      <el-popconfirm title="确定退出吗？" @confirm="logOut">
                        <template #reference>
                          <el-button type="text">退出</el-button>
                        </template>
                      </el-popconfirm>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </el-col>

        </el-row>

      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>

    </el-container>

    <el-dialog v-model="loginDialog" center title="系统登录" width="23%">
      <Login/>
    </el-dialog>

    <el-dialog v-model="registerDialog" center title="系统注册" width="25%" top="7vh">
      <Register/>
    </el-dialog>

  </div>
</template>

<script>
import {computed} from "vue";
import {useRouter} from "vue-router";
import Login from "@/components/Login";
import Register from "@/components/Register";
import {useStore} from "vuex";
import {ElMessage} from "element-plus";

export default {
  name: "First",

  components: {
    Register,
    Login
  },

  setup() {
    const store = useStore();
    const router = useRouter();
    const selectSubject = computed({
      get() {
        return store.state.selectSubject
      },
      set(val) {
        store.state.selectSubject = val
      }
    });

    //获取本地用户以更新用户昵称
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
    //注册对话框，注册完成可以自动关闭
    const registerDialog = computed({
      get() {
        return store.state.registerState
      },
      set(val) {
        store.state.registerState = val
      }
    });
    //登录对话框，登录完成可以自动关闭
    const loginDialog = computed({
      get() {
        return store.state.loginState
      },
      set(val) {
        store.state.loginState = val
      }
    });
    //搜索输入值
    const input = computed({
      get() {
        return store.state.searchInput
      },
      set(val) {
        store.state.searchInput = val
      }
    });
    //顶部组件展示状态符
    const isShow = computed({
      get() {
        return store.state.ralShow
      },
      set(val) {
        store.state.ralShow = val
      }
    });

    //搜索动作
    function doSearch() {
      if (input.value) {
        if (selectSubject.value === '1') {
          store.dispatch("doSearch", {
            pageNum: 1,
            pageSize: 5,
            search: input.value,
            sort: 'updateTime'
          });
        } else {
          store.dispatch("doSearchBySubject", {
            pageNum: 1,
            pageSize: 5,
            search: input.value,
            sort: 'updateTime'
          });
        }

        if (!store.state.currentSearch) {
          router.push({
            name: "searchresult",
          });
          // store.state.currentSearch = true;
        }
      } else {
        ElMessage.error("关键词为空")
      }
    }

    //跳转主页
    function indexmain() {
      router.push({
        name: "mainview",
      });
    }

    //退出
    function logOut() {
      localStorage.clear()
      router.push({
        name: 'index'
      })
      store.commit('CLOSERAL')
      store.commit('UPDATENICK', '')
      store.commit('UPDATEROLE', '')
      store.commit('SETMQ', {})
      store.commit('SETAQ', {})
      store.commit('SETSQ', {})
    }


    return {
      // 数据
      input,
      loginDialog,
      registerDialog,
      isShow,
      localUser,
      selectSubject,
      // 函数
      doSearch,
      indexmain,
      logOut
    };
  },
};
</script>
<style>

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}

.bg-purple {
  display: flex;
  align-items: center;
  justify-content: center;
}


.el-header {
  background: #7e57c2;
}

.grid-content {
  border-radius: 4px;
  min-height: 60px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

html,
body {
  height: 100%;
}

.el-main {
  padding: 10px;
}
</style>