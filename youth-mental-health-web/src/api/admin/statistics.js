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

// 获取健康趋势分析
export function getHealthTrend() {
  return request({
    url: '/admin/statistics/health-trend',
    method: 'get'
  })
}

// 获取高风险用户预警列表
export function getWarningList() {
  return request({
    url: '/admin/statistics/warning-list',
    method: 'get'
  })
}

// 获取活动效果评估
export function getActivityEffect() {
  return request({
    url: '/admin/statistics/activity-effect',
    method: 'get'
  })
}

// 获取科普效果评估
export function getKnowledgeEffect() {
  return request({
    url: '/admin/statistics/knowledge-effect',
    method: 'get'
  })
}
