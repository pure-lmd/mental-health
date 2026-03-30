import dayjs from 'dayjs'

/**
 * 格式化日期
 * @param {string|Date} date 日期
 * @param {string} format 格式
 * @returns {string}
 */
export function formatDate(date, format = 'YYYY-MM-DD') {
  if (!date) return ''
  return dayjs(date).format(format)
}

/**
 * 格式化日期时间
 * @param {string|Date} date 日期
 * @param {string} format 格式
 * @returns {string}
 */
export function formatDateTime(date, format = 'YYYY-MM-DD HH:mm:ss') {
  if (!date) return ''
  return dayjs(date).format(format)
}

/**
 * 防抖函数
 * @param {Function} fn 函数
 * @param {number} delay 延迟时间
 * @returns {Function}
 */
export function debounce(fn, delay = 300) {
  let timer = null
  return function (...args) {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      fn.apply(this, args)
    }, delay)
  }
}

/**
 * 节流函数
 * @param {Function} fn 函数
 * @param {number} delay 延迟时间
 * @returns {Function}
 */
export function throttle(fn, delay = 300) {
  let last = 0
  return function (...args) {
    const now = Date.now()
    if (now - last >= delay) {
      last = now
      fn.apply(this, args)
    }
  }
}

/**
 * 深拷贝
 * @param {any} obj 对象
 * @returns {any}
 */
export function deepClone(obj) {
  if (obj === null || typeof obj !== 'object') return obj
  if (obj instanceof Date) return new Date(obj)
  if (obj instanceof Array) return obj.map(item => deepClone(item))
  const cloned = {}
  for (const key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key)) {
      cloned[key] = deepClone(obj[key])
    }
  }
  return cloned
}

/**
 * 生成UUID
 * @returns {string}
 */
export function uuid() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    const r = (Math.random() * 16) | 0
    const v = c === 'x' ? r : (r & 0x3) | 0x8
    return v.toString(16)
  })
}

/**
 * 获取文件扩展名
 * @param {string} filename 文件名
 * @returns {string}
 */
export function getFileExtension(filename) {
  if (!filename) return ''
  const index = filename.lastIndexOf('.')
  return index > -1 ? filename.substring(index + 1).toLowerCase() : ''
}

/**
 * 文件大小格式化
 * @param {number} bytes 字节数
 * @returns {string}
 */
export function formatFileSize(bytes) {
  if (bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}

/**
 * 获取图片完整URL
 * @param {string} path 路径
 * @returns {string}
 */
export function getImageUrl(path) {
  if (!path) return ''
  if (path.startsWith('http://') || path.startsWith('https://')) {
    return path
  }
  return `/api${path.startsWith('/') ? '' : '/'}${path}`
}

/**
 * 获取活动状态文本
 * @param {number} status 状态值
 * @returns {string}
 */
export function getActivityStatusText(status) {
  const map = { 0: '未开始', 1: '报名中', 2: '进行中', 3: '已结束', 4: '已取消' }
  return map[status] || '未知'
}

/**
 * 获取活动状态类型（用于 el-tag）
 * @param {number} status 状态值
 * @returns {string}
 */
export function getActivityStatusType(status) {
  const map = { 0: 'info', 1: 'success', 2: 'primary', 3: 'warning', 4: 'danger' }
  return map[status] || 'info'
}

/**
 * 获取收藏类型文本
 * @param {number} type 类型值
 * @returns {string}
 */
export function getCollectTypeText(type) {
  const map = { 1: '科普文章', 2: '心理测试', 3: '活动' }
  return map[type] || '未知'
}

/**
 * 获取报名状态文本
 * @param {number} status 状态值
 * @returns {string}
 */
export function getRegistrationStatusText(status) {
  const map = { 0: '待审核', 1: '已通过', 2: '已拒绝', 3: '已取消' }
  return map[status] || '未知'
}

/**
 * 获取报名状态类型（用于 el-tag）
 * @param {number} status 状态值
 * @returns {string}
 */
export function getRegistrationStatusType(status) {
  const map = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }
  return map[status] || 'info'
}

/**
 * 获取健康状态文本
 * @param {number} status 状态值
 * @returns {string}
 */
export function getHealthStatusText(status) {
  const map = { 0: '健康', 1: '良好', 2: '建议沟通' }
  return map[status] || '未知'
}

/**
 * 获取健康状态类型（用于 el-tag）
 * @param {number} status 状态值
 * @returns {string}
 */
export function getHealthStatusType(status) {
  const map = { 0: 'success', 1: 'primary', 2: 'warning' }
  return map[status] || 'info'
}
