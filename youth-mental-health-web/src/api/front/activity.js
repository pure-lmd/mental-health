import request from '@/utils/request'

// 获取活动列表
export function getActivityList(params) {
  return request({
    url: '/front/activity/list',
    method: 'get',
    params
  })
}

// 获取活动详情
export function getActivityDetail(id) {
  return request({
    url: `/front/activity/${id}`,
    method: 'get'
  })
}

// 活动报名
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

// 获取我的报名列表
export function getMyRegistrations(params) {
  return request({
    url: '/front/activity/registration/my',
    method: 'get',
    params
  })
}
