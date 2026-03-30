import request from '@/utils/request'

// 分页查询科普列表
export function getKnowledgeList(params) {
  return request({
    url: '/admin/knowledge/list',
    method: 'get',
    params
  })
}

// 获取科普详情
export function getKnowledgeDetail(id) {
  return request({
    url: `/admin/knowledge/${id}`,
    method: 'get'
  })
}

// 添加科普
export function addKnowledge(data) {
  return request({
    url: '/admin/knowledge',
    method: 'post',
    data
  })
}

// 更新科普
export function updateKnowledge(data) {
  return request({
    url: '/admin/knowledge',
    method: 'put',
    data
  })
}

// 删除科普
export function deleteKnowledge(id) {
  return request({
    url: `/admin/knowledge/${id}`,
    method: 'delete'
  })
}

// 更新科普状态
export function updateKnowledgeStatus(id, status) {
  return request({
    url: `/admin/knowledge/status/${id}`,
    method: 'put',
    params: { status }
  })
}

// 设置热门
export function setKnowledgeHot(id, isHot) {
  return request({
    url: `/admin/knowledge/hot/${id}`,
    method: 'put',
    params: { isHot }
  })
}
