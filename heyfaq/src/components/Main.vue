<template>
  <div class="common-layout">
    <el-container style="height: calc(100vh - 100px)">

      <el-aside width="200px">
        <Aside :activeIndex="activeIndex" :role="role"></Aside>
      </el-aside>
      <el-main class="mainBox">
        <transition
            appear
            enter-active-class="animate__animated animate__fadeInDown"
            leave-active-class="animate__animated animate__fadeOutUp"
            mode="out-in">

          <div v-show="currentPath!=='问题详情'">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>{{ currentPath }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
        </transition>

        <router-view v-slot="{ Component }" style=" height: 96%; width: 100%;">
          <transition
              enter-active-class="animate__animated animate__fadeIn"
              leave-active-class="animate__animated animate__fadeOut"
              mode="out-in">
            <component :is="Component"/>
          </transition>
        </router-view>
      </el-main>
    </el-container>
    <!--回到顶部-->
    <el-backtop target=".mainBox">
      <el-icon  style="
        color:#7e57c2;
      ">
        <svg t="1652704394567" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3289" width="200" height="200"><path d="M754.410527 438.579143C758.932518 142.869721 521.379982 8.992982 505.401013 0.25V0l-0.501999 0.25-0.501999-0.25v0.501999c-15.976969 8.742983-253.505505 142.617721-249.009514 438.329144-48.459905 31.979938-99.155806 88.905826-90.916822 187.333634 7.989984 98.376808 101.417802 163.59568 137.116732 160.077687 35.72293-3.516993 25.222951-30.69794 25.222951-30.69794l12.259976-50.9999s52.679897 81.94884 69.411864 81.94884c15.24997-1.255998 87.424829 0 95.915813 0h0.753998c8.742983 0 80.641842-1.255998 95.915813 0 16.729967 0 69.437864-81.94784 69.437864-81.94784l12.259976 50.9989s-10.24998 27.179947 25.423951 30.69794c35.77293 3.517993 129.125748-61.700879 137.164732-160.077687 7.936984-98.427808-42.709917-155.352697-90.943822-187.585634zM504.900014 434.334152c-6.731987-0.252-95.162814-3.491993-103.150799-109.155787 2.989994-100.386804 96.418812-109.381786 103.149799-109.883785 6.984986 0.501999 100.186804 9.496981 103.151798 109.883785-8.240984 105.663794-96.419812 109.155787-103.150798 109.155787zM452.696116 943.858157c0 12.008977-9.495981 21.755958-20.976959 21.755957-11.730977 0-21.227959-9.746981-21.227959-21.755957V836.486366c0-12.007977 9.496981-21.755958 21.227959-21.755957s20.976959 9.747981 20.976959 21.755957V943.858157z m77.425849 58.181886c0 12.009977-9.495981 21.958957-21.504958 21.958957-11.957977 0-21.452958-9.949981-21.452958-21.958957V842.214355c0-12.259976 9.493981-22.006957 21.452958-22.006957 11.756977 0 21.504958 9.99898 21.504958 22.006957v159.825688z m71.44686-83.403837c0 12.009977-9.494981 21.755958-21.203959 21.755957-11.756977 0-21.252958-9.746981-21.001959-21.755957v-76.92385c0-12.007977 9.446982-21.755958 21.001959-21.755957 11.708977 0 21.203959 9.747981 21.203959 21.755957v76.92385z" p-id="3290"></path></svg>
      </el-icon>
    </el-backtop>
  </div>

</template>

<script>
import {computed} from '@vue/reactivity'
import {useStore} from 'vuex'
import {useRoute, useRouter} from "vue-router";
import Aside from "@/components/Aside";
import request from "@/utils/request";
import {onMounted} from "@vue/runtime-core";
import {ref} from "vue";

export default {
  name: "Main",
  components: {Aside},
  setup() {
    const store = useStore()
    const route = useRoute()
    const roletest=ref('')

    const role = computed(() => {
      if (store.state.userRole === '') {
        if (localStorage.getItem('user') === null) {
          return 'B'
        } else {

          request.post('/user/currentUser').then(res=>{

            roletest.value=res.data.role
          })
          return roletest.value
          // return JSON.parse(localStorage.getItem('user')).role
        }
      } else {
        return store.state.userRole
      }
    });

    const activeIndex = route.name

    const router = useRouter()

    const currentPath = computed(() => {
      return router.currentRoute.value.meta.title
    })


    return {
      currentPath,
      role,
      activeIndex,
    }
  },
}
</script>

<style scoped>

.animate__animated.animate__fadeIn {
  --animate-duration: 0.5s;
}

.animate__animated.animate__fadeOut {
  --animate-duration: 0.5s;
}

.animate__animated.animate__fadeOutUp {
  --animate-duration: 0.3s;
}
</style>