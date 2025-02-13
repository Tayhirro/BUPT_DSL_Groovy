import './assets/main.css'
import 'tailwindcss/tailwind.css'
import 'prosemirror-view/style/prosemirror.css';
import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import axios from "axios";
import store from "./store"

// 全局设置 Axios 的基础 URL 和携带凭证
//20.204.144.237
axios.defaults.baseURL = 'http://localhost:5000';
axios.defaults.withCredentials = true;



// 全局前置守卫

router.beforeEach((to, from, next) => {
  // 模拟身份验证逻辑
  function isAuthenticated() {
    return localStorage.getItem('auth') === 'true';
  }

  if (to.meta.requiresAuth && !isAuthenticated()) {
    // 如果目标路由需要认证且未认证，跳转到登录页
    next('/login');
  } else {
    // 允许通过
    next();
  }
})


createApp(App)
    .use(store)
    .use(router)  // 将 router 注册到 Vue 应用实例中
    .mount('#app')
window.app = app;