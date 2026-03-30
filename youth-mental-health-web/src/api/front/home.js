import request from '@/utils/request'

// 获取首页数据
export function getHomeData() {
  return request({
    url: '/front/home/data',
    method: 'get'
  })
}

// 获取轮播图列表
export function getBannerList() {
  return request({
    url: '/front/home/banner/list',
    method: 'get'
  })
}

// 获取最新公告
export function getLatestNotice(limit = 5) {
  return request({
    url: '/front/home/notice/latest',
    method: 'get',
    params: { limit }
  })
}

// 获取热门科普
export function getHotKnowledge(limit = 6) {
  return request({
    url: '/front/home/knowledge/hot',
    method: 'get',
    params: { limit }
  })
}

// 获取热门活动
export function getHotActivity(limit = 4) {
  return request({
    url: '/front/home/activity/hot',
    method: 'get',
    params: { limit }
  })
}
