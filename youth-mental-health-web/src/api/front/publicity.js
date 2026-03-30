import request from '@/utils/request'

// 获取心理宣传列表
export function getPublicityList(params) {
  return request({
    url: '/front/publicity/list',
    method: 'get',
    params
  })
}

// 获取心理宣传详情
export function getPublicityDetail(id) {
  return request({
    url: `/front/publicity/${id}`,
    method: 'get'
  })
}
