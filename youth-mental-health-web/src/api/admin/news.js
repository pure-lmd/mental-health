import request from '@/utils/request'

// 分页查询资讯列表
export function getNewsList(params) {
  return request({
    url: '/admin/news/list',
    method: 'get',
    params
  })
}

// 获取资讯详情
export function getNewsDetail(id) {
  return request({
    url: `/admin/news/${id}`,
    method: 'get'
  })
}

// 添加资讯
export function addNews(data) {
  return request({
    url: '/admin/news',
    method: 'post',
    data
  })
}

// 更新资讯
export function updateNews(data) {
  return request({
    url: '/admin/news',
    method: 'put',
    data
  })
}

// 删除资讯
export function deleteNews(id) {
  return request({
    url: `/admin/news/${id}`,
    method: 'delete'
  })
}

// 更新资讯状态
export function updateNewsStatus(id, status) {
  return request({
    url: `/admin/news/status/${id}`,
    method: 'put',
    params: { status }
  })
}

// 设置热门
export function setNewsHot(id, isHot) {
  return request({
    url: `/admin/news/hot/${id}`,
    method: 'put',
    params: { isHot }
  })
}
