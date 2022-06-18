import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "@/router";
import App from './App.vue'
import * as ElIcon from '@element-plus/icons-vue'
import store from './store'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
const app = createApp(App)
for (let iconName in ElIcon){
    app.component(iconName, ElIcon[iconName])
}

app.use(router).use(store).use(ElementPlus,{
    locale: zhCn,
}).mount('#app')


router.beforeEach((to, from, next) => {
    /* 路由发生变化修改页面title */
    if (to.meta.title) {
        document.title = to.meta.title
    }
    next()
})
