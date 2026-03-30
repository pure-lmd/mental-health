import request from '@/utils/request'

// 分页查询测试卷列表
export function getTestList(params) {
  return request({
    url: '/admin/test/list',
    method: 'get',
    params
  })
}

// 获取测试卷详情（含题目）
export function getTestDetail(id) {
  return request({
    url: `/admin/test/${id}`,
    method: 'get'
  })
}

// 添加测试卷
export function addTest(data) {
  return request({
    url: '/admin/test',
    method: 'post',
    data
  })
}

// 更新测试卷
export function updateTest(data) {
  return request({
    url: '/admin/test',
    method: 'put',
    data
  })
}

// 删除测试卷
export function deleteTest(id) {
  return request({
    url: `/admin/test/${id}`,
    method: 'delete'
  })
}

// 更新测试卷状态
export function updateTestStatus(id, status) {
  return request({
    url: `/admin/test/status/${id}`,
    method: 'put',
    params: { status }
  })
}

// 获取测试卷的所有题目
export function getTestQuestions(testId) {
  return request({
    url: `/admin/test/${testId}/questions`,
    method: 'get'
  })
}

// 添加题目（含选项）
export function addQuestion(data) {
  return request({
    url: '/admin/test/question',
    method: 'post',
    data
  })
}

// 更新题目（含选项）
export function updateQuestion(data) {
  return request({
    url: '/admin/test/question',
    method: 'put',
    data
  })
}

// 删除题目（含选项）
export function deleteQuestion(id) {
  return request({
    url: `/admin/test/question/${id}`,
    method: 'delete'
  })
}
