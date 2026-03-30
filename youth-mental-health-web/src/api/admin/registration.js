import request from '@/utils/request'

// 分页查询报名记录列表
export function getRegistrationList(params) {
  return request({
    url: '/admin/registration/list',
    method: 'get',
    params
  })
}

// 获取报名详情
export function getRegistrationDetail(id) {
  return request({
    url: `/admin/registration/${id}`,
    method: 'get'
  })
}

// 审核报名
export function auditRegistration(data) {
  return request({
    url: '/admin/registration/audit',
    method: 'put',
    data
  })
}

// 删除报名记录
export function deleteRegistration(id) {
  return request({
    url: `/admin/registration/${id}`,
    method: 'delete'
  })
}
