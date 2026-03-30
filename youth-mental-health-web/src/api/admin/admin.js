import request from '@/utils/request'

// 管理员登录
export function adminLogin(data) {
  return request({
    url: '/admin/login',
    method: 'post',
    data
  })
}

// 获取当前管理员信息
export function getAdminInfo() {
  return request({
    url: '/admin/info',
    method: 'get'
  })
}

// 修改管理员信息
export function updateAdminInfo(data) {
  return request({
    url: '/admin/info',
    method: 'put',
    data
  })
}

// 修改密码
export function updateAdminPassword(data) {
  return request({
    url: '/admin/password',
    method: 'put',
    data
  })
}

// 分页查询管理员列表
export function getAdminList(params) {
  return request({
    url: '/admin/list',
    method: 'get',
    params
  })
}

// 添加管理员
export function addAdmin(data) {
  return request({
    url: '/admin',
    method: 'post',
    data
  })
}

// 更新管理员
export function updateAdmin(data) {
  return request({
    url: '/admin',
    method: 'put',
    data
  })
}

// 删除管理员
export function deleteAdmin(id) {
  return request({
    url: `/admin/${id}`,
    method: 'delete'
  })
}

// 更新管理员状态
export function updateAdminStatus(id, status) {
  return request({
    url: `/admin/status/${id}`,
    method: 'put',
    params: { status }
  })
}
