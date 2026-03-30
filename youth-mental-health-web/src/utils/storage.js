// 存储工具类

const TOKEN_KEY = 'token'
const USER_TYPE_KEY = 'userType' // admin 或 user
const USER_INFO_KEY = 'userInfo'

// 获取Token
export function getToken() {
  return localStorage.getItem(TOKEN_KEY)
}

// 设置Token
export function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token)
}

// 移除Token
export function removeToken() {
  localStorage.removeItem(TOKEN_KEY)
}

// 获取用户类型
export function getUserType() {
  return localStorage.getItem(USER_TYPE_KEY)
}

// 设置用户类型
export function setUserType(type) {
  localStorage.setItem(USER_TYPE_KEY, type)
}

// 移除用户类型
export function removeUserType() {
  localStorage.removeItem(USER_TYPE_KEY)
}

// 获取用户信息
export function getUserInfo() {
  const info = localStorage.getItem(USER_INFO_KEY)
  return info ? JSON.parse(info) : null
}

// 设置用户信息
export function setUserInfo(info) {
  localStorage.setItem(USER_INFO_KEY, JSON.stringify(info))
}

// 移除用户信息
export function removeUserInfo() {
  localStorage.removeItem(USER_INFO_KEY)
}

// 清除所有登录信息
export function clearLoginInfo() {
  removeToken()
  removeUserType()
  removeUserInfo()
}
