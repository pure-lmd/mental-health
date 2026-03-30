import request from '@/utils/request'

// 分页查询收藏记录列表
export function getCollectList(params) {
  return request({
    url: '/admin/collect/list',
    method: 'get',
    params
  })
}

// 删除收藏记录
export function deleteCollect(id) {
  return request({
    url: `/admin/collect/${id}`,
    method: 'delete'
  })
}
