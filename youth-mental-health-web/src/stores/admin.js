import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getAdminInfo } from '@/api/admin/admin'
import { getToken, setToken, setUserType, setUserInfo, clearLoginInfo, getUserInfo } from '@/utils/storage'

export const useAdminStore = defineStore('admin', () => {
  // 管理员信息
  const adminInfo = ref(getUserInfo())
  
  // 是否已登录
  const isLoggedIn = ref(!!getToken())
  
  // 设置管理员信息
  function setAdminInfo(info) {
    adminInfo.value = info
    setUserInfo(info)
  }
  
  // 登录成功后的处理
  function handleLoginSuccess(token) {
    setToken(token)
    setUserType('admin')
    isLoggedIn.value = true
  }
  
  // 获取管理员信息
  async function fetchAdminInfo() {
    try {
      const res = await getAdminInfo()
      setAdminInfo(res.data)
      return res.data
    } catch (error) {
      console.error('获取管理员信息失败:', error)
      throw error
    }
  }
  
  // 退出登录
  function logout() {
    clearLoginInfo()
    adminInfo.value = null
    isLoggedIn.value = false
  }
  
  return {
    adminInfo,
    isLoggedIn,
    setAdminInfo,
    handleLoginSuccess,
    fetchAdminInfo,
    logout
  }
})
