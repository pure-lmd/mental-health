import request from '@/utils/request'

// 发表评论
export function addComment(data) {
  return request({
    url: '/front/comment',
    method: 'post',
    data
  })
}

// 获取评论列表
export function getCommentList(params) {
  return request({
    url: '/front/comment/list',
    method: 'get',
    params
  })
}

// 删除评论
export function deleteComment(id) {
  return request({
    url: `/front/comment/${id}`,
    method: 'delete'
  })
}
