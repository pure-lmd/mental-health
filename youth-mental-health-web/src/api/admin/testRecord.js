import request from '@/utils/request'

// 分页查询测试记录列表
export function getTestRecordList(params) {
  return request({
    url: '/admin/test-record/list',
    method: 'get',
    params
  })
}

// 获取测试记录详情
export function getTestRecordDetail(id) {
  return request({
    url: `/admin/test-record/${id}`,
    method: 'get'
  })
}

// 删除测试记录
export function deleteTestRecord(id) {
  return request({
    url: `/admin/test-record/${id}`,
    method: 'delete'
  })
}
