import request from '@/utils/request'

// 获取概览数据
export function getOverview() {
  return request({
    url: '/admin/statistics/overview',
    method: 'get'
  })
}

// 获取测试统计
export function getTestStatistics() {
  return request({
    url: '/admin/statistics/test',
    method: 'get'
  })
}

// 获取健康状态统计
export function getHealthStatistics() {
  return request({
    url: '/admin/statistics/health',
    method: 'get'
  })
}

// 获取活动统计
export function getActivityStatistics() {
  return request({
    url: '/admin/statistics/activity',
    method: 'get'
  })
}
