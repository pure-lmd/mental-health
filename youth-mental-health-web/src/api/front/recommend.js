import request from '@/utils/request'

// 根据健康状态获取推荐内容
export function getRecommendByHealthStatus(healthStatus) {
  return request({
    url: '/front/recommend/by-health-status',
    method: 'get',
    params: { healthStatus }
  })
}
