import { createRouter, createWebHistory } from 'vue-router'
import { getToken, getUserType } from '@/utils/storage'
import adminRoutes from './admin'
import frontRoutes from './front'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/register/index.vue'),
    meta: { title: '注册' }
  },
  ...adminRoutes,
  ...frontRoutes,
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue'),
    meta: { title: '页面不存在' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - 大学生心理健康管理系统` : '大学生心理健康管理系统'
  
  const token = getToken()
  const userType = getUserType()
  
  // 白名单路由
  const whiteList = ['/login', '/register']
  
  if (whiteList.includes(to.path)) {
    next()
    return
  }
  
  // 检查是否需要登录
  if (to.meta.requireAuth !== false) {
    if (!token) {
      next('/login')
      return
    }
    
    // 检查用户类型权限
    if (to.path.startsWith('/admin') && userType !== 'admin') {
      next('/login')
      return
    }
    
    if (to.path.startsWith('/front') && userType !== 'user') {
      next('/login')
      return
    }
  }
  
  next()
})

export default router
