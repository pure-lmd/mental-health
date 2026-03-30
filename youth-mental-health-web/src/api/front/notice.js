import request from '@/utils/request'

// 获取通知公告列表
export function getNoticeList(params) {
  return request({
    url: '/front/notice/list',
    method: 'get',
    params
  })
}

// 获取通知公告详情
export function getNoticeDetail(id) {
  return request({
    url: `/front/notice/${id}`,
    method: 'get'
  })
}
