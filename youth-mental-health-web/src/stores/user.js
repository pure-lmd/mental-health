import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfo } from '@/api/front/user'
import { getToken, setToken, setUserType, setUserInfo, clearLoginInfo, getUserInfo as getStoredUserInfo } from '@/utils/storage'

export const useUserStore = defineStore('user', () => {
  // 用户信息
  const userInfo = ref(getStoredUserInfo())
  
  // 是否已登录
  const isLoggedIn = ref(!!getToken())
  
  // 设置用户信息
  function setInfo(info) {
    userInfo.value = info
    setUserInfo(info)
  }
  
  // 登录成功后的处理
  function handleLoginSuccess(token) {
    setToken(token)
    setUserType('user')
    isLoggedIn.value = true
  }
  
  // 获取用户信息
  async function fetchUserInfo() {
    try {
      const res = await getUserInfo()
      setInfo(res.data)
      return res.data
    } catch (error) {
      console.error('获取用户信息失败:', error)
      throw error
    }
  }
  
  // 更新用户信息
  function updateUserInfo(info) {
    userInfo.value = { ...userInfo.value, ...info }
    setUserInfo(userInfo.value)
  }
  
  // 退出登录
  function logout() {
    clearLoginInfo()
    userInfo.value = null
    isLoggedIn.value = false
  }
  
  return {
    userInfo,
    isLoggedIn,
    setInfo,
    handleLoginSuccess,
    fetchUserInfo,
    updateUserInfo,
    logout
  }
})
