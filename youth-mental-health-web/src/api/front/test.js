import request from '@/utils/request'

// 获取测试列表
export function getTestList(params) {
  return request({
    url: '/front/test/list',
    method: 'get',
    params
  })
}

// 获取测试详情（含题目）
export function getTestDetail(id) {
  return request({
    url: `/front/test/${id}`,
    method: 'get'
  })
}

// 提交测试
export function submitTest(data) {
  return request({
    url: '/front/test/submit',
    method: 'post',
    data
  })
}

// 获取我的测试记录
export function getMyTestRecords(params) {
  return request({
    url: '/front/test/record',
    method: 'get',
    params
  })
}

// 获取测试记录详情
export function getTestRecordDetail(id) {
  return request({
    url: `/front/test/record/${id}`,
    method: 'get'
  })
}
