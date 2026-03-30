import request from '@/utils/request'

// 获取我的收藏列表
export function getMyCollect(params) {
  return request({
    url: '/front/collect/my',
    method: 'get',
    params
  })
}

// 添加收藏
export function addCollect(data) {
  return request({
    url: '/front/collect',
    method: 'post',
    data
  })
}

// 取消收藏
export function cancelCollect(data) {
  return request({
    url: '/front/collect',
    method: 'delete',
    data
  })
}

// 取消收藏（按类型和目标ID）- 同cancelCollect
export function removeCollect(data) {
  return request({
    url: '/front/collect',
    method: 'delete',
    data
  })
}

// 检查是否已收藏
export function checkCollect(type, targetId) {
  return request({
    url: '/front/collect/check',
    method: 'get',
    params: { type, targetId }
  })
}
