import request from '@/utils/request'

// 用户注册
export function register(data) {
  return request({
    url: '/front/register',
    method: 'post',
    data
  })
}

// 用户登录
export function login(data) {
  return request({
    url: '/front/login',
    method: 'post',
    data
  })
}
