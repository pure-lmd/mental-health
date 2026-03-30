import request from '@/utils/request'

// 分页查询反馈列表
export function getFeedbackList(params) {
  return request({
    url: '/admin/feedback/list',
    method: 'get',
    params
  })
}

// 获取反馈详情
export function getFeedbackDetail(id) {
  return request({
    url: `/admin/feedback/${id}`,
    method: 'get'
  })
}

// 回复反馈
export function replyFeedback(data) {
  return request({
    url: '/admin/feedback/reply',
    method: 'put',
    data
  })
}

// 删除反馈
export function deleteFeedback(id) {
  return request({
    url: `/admin/feedback/${id}`,
    method: 'delete'
  })
}
