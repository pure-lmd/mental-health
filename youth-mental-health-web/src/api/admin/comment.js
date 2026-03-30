import request from '@/utils/request'

// 分页查询评论列表
export function getCommentList(params) {
  return request({
    url: '/admin/comment/list',
    method: 'get',
    params
  })
}

// 获取评论详情
export function getCommentDetail(id) {
  return request({
    url: `/admin/comment/${id}`,
    method: 'get'
  })
}

// 删除评论
export function deleteComment(id) {
  return request({
    url: `/admin/comment/${id}`,
    method: 'delete'
  })
}

// 更新评论状态
export function updateCommentStatus(id, status) {
  return request({
    url: `/admin/comment/status/${id}`,
    method: 'put',
    params: { status }
  })
}

// 审核评论
export function auditComment(id, status, auditRemark) {
  return request({
    url: '/admin/comment/audit',
    method: 'put',
    data: { id, status, auditRemark }
  })
}
