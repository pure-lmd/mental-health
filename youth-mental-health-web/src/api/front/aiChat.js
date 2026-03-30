import request from '@/utils/request'
import { getToken } from '@/utils/storage'

// 发送消息给 AI
export function sendMessage(data) {
  return request({
    url: '/front/ai/chat',
    method: 'post',
    data
  })
}

// 流式发送消息给 AI（SSE）
export function sendMessageStream(data, { onMessage, onDone, onError, onSessionId }) {
  const token = getToken()

  fetch('/api/front/ai/chat/stream', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': token || ''
    },
    body: JSON.stringify(data)
  }).then(response => {
    if (!response.ok) {
      onError?.('网络请求失败')
      return
    }
    const reader = response.body.getReader()
    const decoder = new TextDecoder()
    let buffer = ''

    function read() {
      reader.read().then(({ done, value }) => {
        if (done) {
          onDone?.()
          return
        }
        buffer += decoder.decode(value, { stream: true })
        // 解析 SSE 事件
        const lines = buffer.split('\n')
        buffer = lines.pop() || ''

        let eventName = ''
        for (const line of lines) {
          if (line.startsWith('event:')) {
            eventName = line.slice(6).trim()
          } else if (line.startsWith('data:')) {
            const data = line.slice(5)
            if (eventName === 'message') {
              onMessage?.(data)
            } else if (eventName === 'done') {
              onDone?.()
            } else if (eventName === 'error') {
              onError?.(data)
            } else if (eventName === 'sessionId') {
              onSessionId?.(data)
            }
            eventName = ''
          }
        }
        read()
      }).catch(err => {
        onError?.(err.message || '读取流失败')
      })
    }
    read()
  }).catch(err => {
    onError?.(err.message || '网络错误')
  })
}

// 获取 AI 聊天历史
export function getChatHistory(params) {
  return request({
    url: '/front/ai/history',
    method: 'get',
    params
  })
}

// 获取会话列表
export function getSessionList() {
  return request({
    url: '/front/ai/sessions',
    method: 'get'
  })
}

// 删除会话
export function deleteSession(sessionId) {
  return request({
    url: `/front/ai/session/${sessionId}`,
    method: 'delete'
  })
}
