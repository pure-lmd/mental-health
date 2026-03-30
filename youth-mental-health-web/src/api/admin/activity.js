import request from '@/utils/request'

// 分页查询活动列表
export function getActivityList(params) {
  return request({
    url: '/admin/activity/list',
    method: 'get',
    params
  })
}

// 获取活动详情
export function getActivityDetail(id) {
  return request({
    url: `/admin/activity/${id}`,
    method: 'get'
  })
}

// 添加活动
export function addActivity(data) {
  return request({
    url: '/admin/activity',
    method: 'post',
    data
  })
}

// 更新活动
export function updateActivity(data) {
  return request({
    url: '/admin/activity',
    method: 'put',
    data
  })
}

// 删除活动
export function deleteActivity(id) {
  return request({
    url: `/admin/activity/${id}`,
    method: 'delete'
  })
}

// 更新活动状态
export function updateActivityStatus(id, status) {
  return request({
    url: `/admin/activity/status/${id}`,
    method: 'put',
    params: { status }
  })
}

// 设置热门
export function setActivityHot(id, isHot) {
  return request({
    url: `/admin/activity/hot/${id}`,
    method: 'put',
    params: { isHot }
  })
}
