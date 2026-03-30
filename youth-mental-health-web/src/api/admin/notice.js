import request from '@/utils/request'

// 分页查询通知公告列表
export function getNoticeList(params) {
  return request({
    url: '/admin/notice/list',
    method: 'get',
    params
  })
}

// 获取通知公告详情
export function getNoticeDetail(id) {
  return request({
    url: `/admin/notice/${id}`,
    method: 'get'
  })
}

// 添加通知公告
export function addNotice(data) {
  return request({
    url: '/admin/notice',
    method: 'post',
    data
  })
}

// 更新通知公告
export function updateNotice(data) {
  return request({
    url: '/admin/notice',
    method: 'put',
    data
  })
}

// 删除通知公告
export function deleteNotice(id) {
  return request({
    url: `/admin/notice/${id}`,
    method: 'delete'
  })
}

// 更新通知公告状态
export function updateNoticeStatus(id, status) {
  return request({
    url: `/admin/notice/status/${id}`,
    method: 'put',
    params: { status }
  })
}

// 设置置顶
export function setNoticeTop(id, isTop) {
  return request({
    url: `/admin/notice/top/${id}`,
    method: 'put',
    params: { isTop }
  })
}
