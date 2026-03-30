import request from '@/utils/request'

// 获取我的报名列表
export function getMyRegistration(params) {
  return request({
    url: '/front/activity/registration/my',
    method: 'get',
    params
  })
}

// 报名活动
export function registerActivity(data) {
  return request({
    url: '/front/activity/register',
    method: 'post',
    data
  })
}

// 取消报名
export function cancelRegistration(id) {
  return request({
    url: `/front/activity/registration/${id}`,
    method: 'delete'
  })
}
