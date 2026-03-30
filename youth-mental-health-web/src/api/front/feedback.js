import request from '@/utils/request'

// 获取我的反馈列表
export function getMyFeedback(params) {
  return request({
    url: '/front/feedback/my',
    method: 'get',
    params
  })
}

// 提交反馈
export function submitFeedback(data) {
  return request({
    url: '/front/feedback',
    method: 'post',
    data
  })
}

// 更新反馈
export function updateFeedback(id, data) {
  return request({
    url: `/front/feedback/${id}`,
    method: 'put',
    data
  })
}

// 删除反馈
export function deleteFeedback(id) {
  return request({
    url: `/front/feedback/${id}`,
    method: 'delete'
  })
}
