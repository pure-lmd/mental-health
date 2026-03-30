import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getToken, clearLoginInfo } from './storage'
import router from '@/router'

// 是否正在跳转登录页（防止重复跳转）
let isRedirecting = false

// 创建axios实例
const request = axios.create({
  baseURL: '/api',
  timeout: 30000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 添加token
    const token = getToken()
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 判断当前是否在登录/注册页
function isAuthPage() {
  const path = router.currentRoute?.value?.path || ''
  return path === '/login' || path === '/register'
}

// 处理401跳转
function handleUnauthorized() {
  // 如果在登录页或注册页，不做跳转和清除操作
  if (isAuthPage()) return
  if (isRedirecting) return
  isRedirecting = true
  ElMessage.error('登录已过期，请重新登录')
  clearLoginInfo()
  router.push('/login').finally(() => {
    isRedirecting = false
  })
}

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data

    // 根据业务状态码处理
    if (res.code !== 200) {
      // 401 未授权
      if (res.code === 401) {
        handleUnauthorized()
        return Promise.reject(new Error(res.message || '未授权'))
      }

      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }

    return res
  },
  error => {
    console.error('响应错误:', error)

    // 处理HTTP错误
    if (error.response) {
      switch (error.response.status) {
        case 401:
          handleUnauthorized()
          break
        case 403:
          ElMessage.error('没有权限访问')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error(error.response.data?.message || '请求失败')
      }
    } else if (error.message.includes('timeout')) {
      ElMessage.error('请求超时，请稍后重试')
    } else {
      ElMessage.error('网络错误，请检查网络连接')
    }

    return Promise.reject(error)
  }
)

export default request
