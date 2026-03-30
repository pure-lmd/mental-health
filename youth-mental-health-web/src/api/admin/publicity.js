import request from '@/utils/request'

// 分页查询宣传列表
export function getPublicityList(params) {
  return request({
    url: '/admin/publicity/list',
    method: 'get',
    params
  })
}

// 获取宣传详情
export function getPublicityDetail(id) {
  return request({
    url: `/admin/publicity/${id}`,
    method: 'get'
  })
}

// 添加宣传
export function addPublicity(data) {
  return request({
    url: '/admin/publicity',
    method: 'post',
    data
  })
}

// 更新宣传
export function updatePublicity(data) {
  return request({
    url: '/admin/publicity',
    method: 'put',
    data
  })
}

// 删除宣传
export function deletePublicity(id) {
  return request({
    url: `/admin/publicity/${id}`,
    method: 'delete'
  })
}

// 更新宣传状态
export function updatePublicityStatus(id, status) {
  return request({
    url: `/admin/publicity/status/${id}`,
    method: 'put',
    params: { status }
  })
}
