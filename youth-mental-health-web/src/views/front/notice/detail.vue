<template>
  <div class="notice-detail">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item :to="{ path: '/front/notice' }">通知公告</el-breadcrumb-item><el-breadcrumb-item>{{ detail.title }}</el-breadcrumb-item></el-breadcrumb></div>
    <div class="content-wrapper" v-loading="loading">
      <article class="article-content">
        <h1 class="title">{{ detail.title }}</h1>
        <div class="meta"><span><svg-icon name="time" :size="14" /> {{ formatDateTime(detail.createTime) }}</span></div>
        <div class="article-body" v-html="detail.content"></div>
      </article>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getNoticeDetail } from '@/api/front/notice'
import { formatDateTime } from '@/utils/common'

const route = useRoute()
const loading = ref(false)
const detail = ref({})

async function fetchDetail() { loading.value = true; try { const res = await getNoticeDetail(route.params.id); detail.value = res.data } catch (e) { console.error(e) } finally { loading.value = false } }
onMounted(() => { fetchDetail() })
</script>

<style lang="scss" scoped>
.notice-detail { .content-wrapper { background: $bg-white; border-radius: $border-radius-base; padding: $spacing-xl; box-shadow: $shadow-light; min-height: 400px; }
  .article-content { .title { font-size: 28px; font-weight: 600; color: $text-primary; margin-bottom: $spacing-md; } .meta { display: flex; gap: $spacing-lg; color: $text-secondary; font-size: $font-size-small; margin-bottom: $spacing-lg; padding-bottom: $spacing-lg; border-bottom: 1px solid $border-lighter; span { display: flex; align-items: center; gap: 4px; } } .article-body { line-height: 1.8; color: $text-regular; font-size: $font-size-medium; white-space: pre-wrap; } } }
</style>
