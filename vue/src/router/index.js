import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue';    // 从 views 文件夹中导入页面
import Signin from '@/views/Signin.vue';  // 从 views 文件夹中导入页面
import Login from '@/views/Login.vue';  // 从 views 文件夹中导入页面

const routes = [
  {
    path: '/',
    redirect: '/login'  // 当访问根路径时，重定向到 /login
  },
  {
    path: '/signin',
    name: 'signin',
    component: Signin // 添加 signin 路由，指向 Signin 组件
  },
  {
    path: '/login',
    name: 'login',
    component: Login // 添加 signin 路由，指向 Signin 组件

  },
  {
    path: '/home',
    name: 'home',
    component: Home, // 添加 signin 路由，指向 Signin 组件
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router