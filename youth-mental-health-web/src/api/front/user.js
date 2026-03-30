import request from '@/utils/request'

// 获取当前用户信息
export function getUserInfo() {
  return request({
    url: '/front/user/info',
    method: 'get'
  })
}

// 修改用户信息
export function updateUserInfo(data) {
  return request({
    url: '/front/user/info',
    method: 'put',
    data
  })
}

// 修改密码
export function updatePassword(data) {
  return request({
    url: '/front/user/password',
    method: 'put',
    data
  })
}

// 修改密码（别名）
export function updateUserPassword(data) {
  return updatePassword(data)
}
