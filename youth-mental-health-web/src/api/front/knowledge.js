import request from '@/utils/request'

// 获取分类列表
export function getCategoryList() {
  return request({
    url: '/front/knowledge/category/list',
    method: 'get'
  })
}

// 分页查询科普列表
export function getKnowledgeList(params) {
  return request({
    url: '/front/knowledge/list',
    method: 'get',
    params
  })
}

// 获取科普详情
export function getKnowledgeDetail(id) {
  return request({
    url: `/front/knowledge/${id}`,
    method: 'get'
  })
}

// 获取科普评论列表
export function getKnowledgeComments(id, params) {
  return request({
    url: `/front/knowledge/${id}/comments`,
    method: 'get',
    params
  })
}
