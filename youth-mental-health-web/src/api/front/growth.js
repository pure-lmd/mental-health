import api from '../index'

// 获取成长轨迹
export function getGrowthTrajectory() {
  return api.get('/front/user/growth/trajectory')
}

// 获取学习统计
export function getLearningStats() {
  return api.get('/front/user/growth/statistics')
}

// 获取学习记录
export function getLearningRecords(params) {
  return api.get('/front/user/learning/records', { params })
}

// 记录学习行为
export function recordLearning(data) {
  return api.post('/front/user/learning/record', data)
}
