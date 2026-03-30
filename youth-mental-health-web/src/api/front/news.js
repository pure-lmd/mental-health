import request from '@/utils/request'

// 获取资讯列表
export function getNewsList(params) {
  return request({
    url: '/front/news/list',
    method: 'get',
    params
  })
}

// 获取资讯详情
export function getNewsDetail(id) {
  return request({
    url: `/front/news/${id}`,
    method: 'get'
  })
}
