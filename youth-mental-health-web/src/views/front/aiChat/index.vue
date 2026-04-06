<template>
  <div class="ai-chat-page">
    <div class="breadcrumb-wrapper">
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>AI 助手</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="chat-wrapper">
      <!-- 历史记录侧边栏 -->
      <div class="history-sidebar" :class="{ 'collapsed': historyCollapsed }">
        <div class="sidebar-header">
          <span class="title">
            <svg-icon name="record" :size="18" />
            对话历史
          </span>
          <el-button link @click="toggleHistory" class="collapse-btn">
            <svg-icon name="menu-fold" :size="16" :class="{ 'flipped': !historyCollapsed }" />
          </el-button>
        </div>
        <div class="sidebar-content">
          <el-button type="primary" class="new-chat-btn" @click="handleNewChat" :disabled="historyCollapsed">
            <svg-icon name="add" :size="16" />
            <span>新建对话</span>
          </el-button>
          <div class="history-list" v-loading="loadingHistory">
            <div
              v-for="(session, index) in sessionList"
              :key="index"
              class="history-item"
              :class="{ 'active': session === currentSessionId }"
              @click="handleSelectSession(session)"
            >
              <svg-icon name="comment" :size="16" />
              <span class="session-name">对话 {{ index + 1 }}</span>
              <el-button
                link
                class="delete-btn"
                @click.stop="handleDeleteSession(session, index)"
              >
                <svg-icon name="delete" :size="14" />
              </el-button>
            </div>
            <div v-if="sessionList.length === 0 && !loadingHistory" class="empty-tip">
              <svg-icon name="comment" :size="48" color="#d0d0d0" />
              <p>暂无对话记录</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 聊天主区域 -->
      <div class="chat-main">
        <div class="chat-header">
          <el-button link class="toggle-history-btn" @click="toggleHistory">
            <svg-icon name="menu-unfold" :size="20" />
          </el-button>
          <svg-icon name="ai" :size="28" color="#36B37E" />
          <span>AI 心理助手</span>
          <p>我是您的 AI 心理咨询助手，有什么可以帮您的吗？</p>
        </div>

        <div class="chat-messages" ref="messagesRef">
          <div v-for="(msg, index) in messages" :key="index" class="message" :class="msg.role">
            <el-avatar :size="36" v-if="msg.role === 'assistant'">
              <svg-icon name="ai" :size="20" />
            </el-avatar>
            <div class="message-content">
              <div class="bubble markdown-content" v-html="renderMarkdown(msg.content)"></div>
              <span v-if="msg.streaming" class="cursor-blink">|</span>
              <span class="time">{{ msg.time }}</span>
            </div>
            <el-avatar :size="36" v-if="msg.role === 'user'" :src="userInfo?.avatar">
              <svg-icon name="user" :size="20" />
            </el-avatar>
          </div>
          <div v-if="messages.length === 0" class="empty-chat-tip">
            <svg-icon name="ai" :size="64" color="#36B37E" />
            <p>您好！我是 AI 心理助手</p>
            <p>有什么可以帮您的吗？</p>
          </div>
        </div>

        <div class="chat-input">
          <el-input
            v-model="inputMessage"
            type="textarea"
            :rows="2"
            placeholder="输入您想咨询的问题..."
            :disabled="isStreaming"
            @keyup.enter.ctrl="handleSend"
          />
          <el-button type="primary" :loading="isStreaming" @click="handleSend">
            <svg-icon name="send" :size="16" />
            <span>发送</span>
          </el-button>
        </div>
      </div>
    </div>

    <!-- 删除确认对话框 -->
    <el-dialog v-model="deleteDialogVisible" title="删除对话" width="350px">
      <p>确定要删除这条对话记录吗？删除后无法恢复。</p>
      <template #footer>
        <el-button @click="deleteDialogVisible = false">取消</el-button>
        <el-button type="danger" @click="confirmDelete">删除</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, nextTick, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { sendMessageStream, getSessionList, deleteSession, getChatHistory } from '@/api/front/aiChat'
import dayjs from 'dayjs'
import MarkdownIt from 'markdown-it'

const md = new MarkdownIt({
  breaks: true,
  linkify: true,
  typographer: true
})

const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)
const messagesRef = ref(null)
const inputMessage = ref('')
const isStreaming = ref(false)
const messages = ref([])
const currentSessionId = ref('')
const historyCollapsed = ref(false)
const loadingHistory = ref(false)
const sessionList = ref([])
const deleteDialogVisible = ref(false)
const sessionToDelete = ref('')

function scrollToBottom() {
  nextTick(() => {
    if (messagesRef.value) {
      messagesRef.value.scrollTop = messagesRef.value.scrollHeight
    }
  })
}

// 渲染Markdown内容
function renderMarkdown(content) {
  if (!content) return ''
  return md.render(content)
}

// 加载会话列表
async function loadSessionList() {
  loadingHistory.value = true
  try {
    const res = await getSessionList()
    sessionList.value = res.data || []
  } catch (e) {
    console.error('加载会话列表失败:', e)
  } finally {
    loadingHistory.value = false
  }
}

// 加载指定会话的历史记录
async function loadSessionHistory(sessionId) {
  try {
    const res = await getChatHistory({ sessionId })
    messages.value = (res.data || []).map(msg => ({
      role: msg.role,
      content: msg.content,
      time: dayjs(msg.createTime).format('HH:mm')
    }))
  } catch (e) {
    console.error('加载历史记录失败:', e)
  }
}

// 切换侧边栏
function toggleHistory() {
  historyCollapsed.value = !historyCollapsed.value
}

// 新建对话
function handleNewChat() {
  currentSessionId.value = ''
  messages.value = []
  ElMessage.success('已新建对话')
}

// 选择会话
async function handleSelectSession(sessionId) {
  if (sessionId === currentSessionId.value) return
  currentSessionId.value = sessionId
  await loadSessionHistory(sessionId)
  scrollToBottom()
}

// 删除会话
function handleDeleteSession(sessionId) {
  sessionToDelete.value = sessionId
  deleteDialogVisible.value = true
}

// 确认删除
async function confirmDelete() {
  try {
    await deleteSession(sessionToDelete.value)
    ElMessage.success('删除成功')
    if (sessionToDelete.value === currentSessionId.value) {
      currentSessionId.value = ''
      messages.value = []
    }
    await loadSessionList()
  } catch (e) {
    console.error('删除失败:', e)
  } finally {
    deleteDialogVisible.value = false
  }
}

// 发送消息
function handleSend() {
  if (!inputMessage.value.trim()) {
    ElMessage.warning('请输入内容')
    return
  }
  if (isStreaming.value) return

  const userMsg = inputMessage.value.trim()
  
  // 如果没有当前会话，生成一个新的
  if (!currentSessionId.value) {
    currentSessionId.value = dayjs().valueOf().toString()
  }

  // 立即展示用户消息
  messages.value.push({
    role: 'user',
    content: userMsg,
    time: dayjs().format('HH:mm')
  })
  inputMessage.value = ''
  scrollToBottom()

  // 添加一个空的 AI 消息占位，用于流式填充
  const aiMsgIndex = messages.value.length
  messages.value.push({
    role: 'assistant',
    content: '',
    time: dayjs().format('HH:mm'),
    streaming: true
  })
  scrollToBottom()

  isStreaming.value = true

  sendMessageStream(
    { message: userMsg, sessionId: currentSessionId.value },
    {
      onMessage(chunk) {
        messages.value[aiMsgIndex].content += chunk
        scrollToBottom()
      },
      onSessionId(id) {
        currentSessionId.value = id
        // 刷新会话列表
        loadSessionList()
      },
      onDone() {
        messages.value[aiMsgIndex].streaming = false
        isStreaming.value = false
        scrollToBottom()
        // 刷新会话列表
        loadSessionList()
      },
      onError(err) {
        if (!messages.value[aiMsgIndex].content) {
          messages.value[aiMsgIndex].content = err || '抱歉，网络出现问题，请稍后再试。'
        }
        messages.value[aiMsgIndex].streaming = false
        isStreaming.value = false
        scrollToBottom()
      }
    }
  )
}

// 初始化
onMounted(() => {
  loadSessionList()
})
</script>

<style lang="scss" scoped>
.ai-chat-page {
  .chat-wrapper {
    background: $bg-white;
    border-radius: $border-radius-base;
    box-shadow: $shadow-light;
    height: calc(100vh - 200px);
    min-height: 500px;
    display: flex;
    overflow: hidden;
  }

  .history-sidebar {
    width: 260px;
    min-width: 260px;
    border-right: 1px solid $border-lighter;
    display: flex;
    flex-direction: column;
    transition: all 0.3s;

    &.collapsed {
      width: 0;
      min-width: 0;
      border: none;
      overflow: hidden;
    }

    .sidebar-header {
      padding: $spacing-md;
      border-bottom: 1px solid $border-lighter;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .title {
        display: flex;
        align-items: center;
        gap: $spacing-xs;
        font-size: $font-size-medium;
        font-weight: 600;
        color: $text-primary;
      }

      .collapse-btn {
        padding: 4px;
        :deep(.svg-icon) {
          transition: transform 0.3s;
          &.flipped {
            transform: rotate(180deg);
          }
        }
      }
    }

    .sidebar-content {
      flex: 1;
      display: flex;
      flex-direction: column;
      padding: $spacing-md;
      overflow: hidden;

      .new-chat-btn {
        width: 100%;
        margin-bottom: $spacing-md;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: $spacing-xs;
      }

      .history-list {
        flex: 1;
        overflow-y: auto;

        .history-item {
          display: flex;
          align-items: center;
          padding: $spacing-sm $spacing-md;
          border-radius: $border-radius-base;
          cursor: pointer;
          margin-bottom: $spacing-xs;
          transition: all 0.2s;

          &:hover {
            background: $bg-color;
          }

          &.active {
            background: $primary-light;
            color: $primary-color;
          }

          .svg-icon {
            margin-right: $spacing-sm;
            flex-shrink: 0;
          }

          .session-name {
            flex: 1;
            font-size: $font-size-small;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }

          .delete-btn {
            opacity: 0;
            padding: 4px;
            color: $danger-color;
            transition: opacity 0.2s;

            &:hover {
              background: $danger-color-light;
            }
          }

          &:hover .delete-btn {
            opacity: 1;
          }
        }

        .empty-tip {
          text-align: center;
          padding: $spacing-xl $spacing-md;
          color: $text-placeholder;

          p {
            margin-top: $spacing-md;
            font-size: $font-size-small;
          }
        }
      }
    }
  }

  .chat-main {
    flex: 1;
    display: flex;
    flex-direction: column;
    min-width: 0;
  }

  .chat-header {
    padding: $spacing-lg;
    border-bottom: 1px solid $border-lighter;
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    flex-wrap: wrap;

    .toggle-history-btn {
      padding: 4px;
      margin-right: $spacing-xs;
    }

    span {
      font-size: $font-size-large;
      font-weight: 600;
      color: $text-primary;
    }

    p {
      width: 100%;
      margin: $spacing-sm 0 0;
      color: $text-secondary;
      font-size: $font-size-small;
    }
  }

  .chat-messages {
    flex: 1;
    padding: $spacing-lg;
    overflow-y: auto;

    .message {
      display: flex;
      gap: $spacing-sm;
      margin-bottom: $spacing-lg;

      &.user {
        flex-direction: row-reverse;

        .bubble {
          background: $primary-color;
          color: #fff;
        }
      }

      &.assistant {
        .bubble {
          background: $bg-color;
          color: $text-primary;
        }
      }

      .message-content {
        max-width: 70%;
        display: flex;
        flex-direction: column;
        align-items: flex-start;

        .bubble {
          padding: $spacing-md;
          border-radius: $border-radius-base;
          line-height: 1.6;
          white-space: pre-wrap;
          word-break: break-word;
          
          &.markdown-content {
            white-space: normal;
            
            :deep(p) {
              margin: 0.5em 0;
              &:first-child { margin-top: 0; }
              &:last-child { margin-bottom: 0; }
            }
            
            :deep(ul), :deep(ol) {
              margin: 0.5em 0;
              padding-left: 1.5em;
            }
            
            :deep(li) {
              margin: 0.3em 0;
              line-height: 1.6;
            }
            
            :deep(strong) {
              font-weight: 600;
            }
            
            :deep(em) {
              font-style: italic;
            }
            
            :deep(code) {
              background: rgba(0, 0, 0, 0.06);
              padding: 2px 6px;
              border-radius: 4px;
              font-size: 0.9em;
            }
            
            :deep(pre) {
              background: rgba(0, 0, 0, 0.06);
              padding: $spacing-sm;
              border-radius: $border-radius-small;
              overflow-x: auto;
              margin: 0.5em 0;
              
              code {
                background: none;
                padding: 0;
              }
            }
            
            :deep(blockquote) {
              border-left: 3px solid $primary-color;
              padding-left: $spacing-sm;
              margin: 0.5em 0;
              color: $text-secondary;
            }
          }
        }

        .time {
          font-size: $font-size-small;
          color: $text-placeholder;
          margin-top: $spacing-xs;
        }
      }

      &.user .message-content {
        align-items: flex-end;
      }
    }

    .empty-chat-tip {
      text-align: center;
      padding: $spacing-xl * 2 $spacing-lg;
      color: $text-secondary;

      p {
        margin-top: $spacing-md;
        font-size: $font-size-medium;
      }
    }
  }

  .chat-input {
    padding: $spacing-md $spacing-lg;
    border-top: 1px solid $border-lighter;
    display: flex;
    gap: $spacing-md;

    .el-input {
      flex: 1;
    }

    .el-button {
      align-self: flex-end;
      display: flex;
      align-items: center;
      gap: $spacing-xs;
    }
  }
}

.cursor-blink {
  animation: blink 0.8s infinite;
  font-weight: 100;
}

@keyframes blink {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0;
  }
}
</style>
