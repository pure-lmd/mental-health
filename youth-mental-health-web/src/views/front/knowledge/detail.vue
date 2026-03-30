<template>
  <div class="knowledge-detail">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item :to="{ path: '/front/knowledge' }">心理科普</el-breadcrumb-item><el-breadcrumb-item>{{ detail.title }}</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="content-wrapper" v-loading="loading">
      <article class="article-content">
        <h1 class="title">{{ detail.title }}</h1>
        <div class="meta">
          <span><svg-icon name="time" :size="14" /> {{ formatDateTime(detail.createTime) }}</span>
          <span><svg-icon name="view" :size="14" /> {{ detail.viewCount || 0 }} 浏览</span>
          <span><svg-icon name="collect" :size="14" /> {{ detail.collectCount || 0 }} 收藏</span>
        </div>
        <div class="article-body" v-html="detail.content"></div>
        <div class="article-actions">
          <el-button :type="isCollected ? 'warning' : 'default'" @click="handleCollect">
            <svg-icon name="collect" :size="14" />
            <span>{{ isCollected ? '已收藏' : '收藏' }}</span>
          </el-button>
        </div>
      </article>
      
      <!-- 评论区 -->
      <div class="comment-section">
        <h3><svg-icon name="comment" :size="18" /> 评论 ({{ comments.length }})</h3>
        <div class="comment-form">
          <el-input v-model="commentContent" type="textarea" :rows="3" placeholder="发表你的看法..." />
          <el-button type="primary" :loading="commentLoading" @click="handleComment">发表评论</el-button>
        </div>
        <div class="comment-list">
          <div v-for="item in comments" :key="item.id" class="comment-item">
            <el-avatar :size="40" :src="item.userAvatar"><svg-icon name="user" :size="20" /></el-avatar>
            <div class="comment-content">
              <div class="comment-header">
                <span class="name">{{ item.userNickname }}</span>
                <span class="time">{{ formatDateTime(item.createTime) }}</span>
              </div>
              <p>{{ item.content }}</p>
            </div>
          </div>
          <el-empty v-if="comments.length === 0" description="暂无评论，快来抢沙发吧~" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getKnowledgeDetail, getKnowledgeComments } from '@/api/front/knowledge'
import { addCollect, removeCollect } from '@/api/front/collect'
import { addComment } from '@/api/front/comment'
import { formatDateTime } from '@/utils/common'

const route = useRoute()
const loading = ref(false)
const detail = ref({})
const comments = ref([])
const isCollected = ref(false)
const commentContent = ref('')
const commentLoading = ref(false)

async function fetchDetail() {
  loading.value = true
  try {
    const [detailRes, commentsRes] = await Promise.all([
      getKnowledgeDetail(route.params.id),
      getKnowledgeComments(route.params.id, { pageNum: 1, pageSize: 100 })
    ])
    detail.value = detailRes.data.knowledge
    comments.value = commentsRes.data?.records || []
    isCollected.value = detailRes.data.isCollected || false
  } catch (e) { console.error(e) } finally { loading.value = false }
}

async function handleCollect() {
  try {
    if (isCollected.value) {
      await removeCollect({ targetId: detail.value.id, type: 1 })
      isCollected.value = false
      ElMessage.success('已取消收藏')
    } else {
      await addCollect({ targetId: detail.value.id, type: 1 })
      isCollected.value = true
      ElMessage.success('收藏成功')
    }
  } catch (e) { console.error(e) }
}

async function handleComment() {
  if (!commentContent.value.trim()) { ElMessage.warning('请输入评论内容'); return }
  commentLoading.value = true
  try {
    await addComment({ targetId: detail.value.id, type: 1, content: commentContent.value })
    ElMessage.success('评论成功')
    commentContent.value = ''
    const res = await getKnowledgeComments(route.params.id, { pageNum: 1, pageSize: 100 })
    comments.value = res.data?.records || []
  } catch (e) { console.error(e) } finally { commentLoading.value = false }
}

onMounted(() => { fetchDetail() })
</script>

<style lang="scss" scoped>
.knowledge-detail {
  .content-wrapper {
    background: $bg-white;
    border-radius: $border-radius-base;
    padding: $spacing-xl;
    box-shadow: $shadow-light;
    min-height: 500px;
  }
  
  .article-content {
    .title { font-size: 28px; font-weight: 600; color: $text-primary; margin-bottom: $spacing-md; }
    .meta { display: flex; gap: $spacing-lg; color: $text-secondary; font-size: $font-size-small; margin-bottom: $spacing-lg; padding-bottom: $spacing-lg; border-bottom: 1px solid $border-lighter; span { display: flex; align-items: center; gap: 4px; } }
    .article-body { line-height: 1.8; color: $text-regular; font-size: $font-size-medium; margin-bottom: $spacing-xl; white-space: pre-wrap; }
    .article-actions { display: flex; gap: $spacing-md; padding-top: $spacing-lg; border-top: 1px solid $border-lighter; }
  }
  
  .comment-section {
    margin-top: $spacing-xl;
    padding-top: $spacing-xl;
    border-top: 1px solid $border-lighter;
    
    h3 { display: flex; align-items: center; gap: $spacing-sm; font-size: $font-size-large; color: $text-primary; margin-bottom: $spacing-lg; }
    
    .comment-form {
      display: flex;
      flex-direction: column;
      gap: $spacing-md;
      margin-bottom: $spacing-lg;
      .el-button { align-self: flex-end; }
    }
    
    .comment-list {
      .comment-item {
        display: flex;
        gap: $spacing-md;
        padding: $spacing-md 0;
        border-bottom: 1px solid $border-lighter;
        
        .comment-content {
          flex: 1;
          .comment-header { display: flex; justify-content: space-between; margin-bottom: $spacing-sm; .name { font-weight: 500; color: $text-primary; } .time { color: $text-placeholder; font-size: $font-size-small; } }
          p { color: $text-regular; line-height: 1.6; }
        }
      }
    }
  }
}
</style>
