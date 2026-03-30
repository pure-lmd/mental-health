import request from '@/utils/request'

// 分页查询分类列表
export function getCategoryList(params) {
  return request({
    url: '/admin/category/list',
    method: 'get',
    params
  })
}

// 获取所有启用分类
export function getAllCategories() {
  return request({
    url: '/admin/category/all',
    method: 'get'
  })
}

// 获取分类详情
export function getCategoryDetail(id) {
  return request({
    url: `/admin/category/${id}`,
    method: 'get'
  })
}

// 添加分类
export function addCategory(data) {
  return request({
    url: '/admin/category',
    method: 'post',
    data
  })
}

// 更新分类
export function updateCategory(data) {
  return request({
    url: '/admin/category',
    method: 'put',
    data
  })
}

// 删除分类
export function deleteCategory(id) {
  return request({
    url: `/admin/category/${id}`,
    method: 'delete'
  })
}

// 更新分类状态
export function updateCategoryStatus(id, status) {
  return request({
    url: `/admin/category/status/${id}`,
    method: 'put',
    params: { status }
  })
}
