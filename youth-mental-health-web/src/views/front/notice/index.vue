<template>
  <div class="notice-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>通知公告</el-breadcrumb-item></el-breadcrumb></div>
    <div class="notice-list" v-loading="loading">
      <div v-for="item in list" :key="item.id" class="notice-item" @click="$router.push(`/front/notice/${item.id}`)">
        <div class="notice-icon"><svg-icon name="notice" :size="24" color="#36B37E" /></div>
        <div class="notice-content">
          <h3>{{ item.title }}<el-tag v-if="item.isTop" type="danger" size="small">置顶</el-tag></h3>
          <p class="time"><svg-icon name="time" :size="12" /> {{ formatDateTime(item.createTime) }}</p>
        </div>
        <svg-icon name="arrow-right" :size="16" color="#909399" />
      </div>
      <el-empty v-if="!loading && list.length === 0" description="暂无公告" />
    </div>
    <div class="pagination-center" v-if="total > 0"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" /></div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getNoticeList } from '@/api/front/notice'
import { formatDateTime } from '@/utils/common'

const loading = ref(false), list = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

async function fetchList() { loading.value = true; try { const res = await getNoticeList(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.notice-page { .notice-list { background: $bg-white; border-radius: $border-radius-base; box-shadow: $shadow-light; min-height: 300px;
    .notice-item { display: flex; align-items: center; gap: $spacing-md; padding: $spacing-md $spacing-lg; border-bottom: 1px solid $border-lighter; cursor: pointer; transition: $transition-fast; &:last-child { border-bottom: none; } &:hover { background: $bg-color; }
      .notice-icon { width: 48px; height: 48px; border-radius: 50%; background: rgba($primary-color, 0.1); display: flex; align-items: center; justify-content: center; }
      .notice-content { flex: 1; h3 { font-size: $font-size-medium; color: $text-primary; margin-bottom: $spacing-xs; display: flex; align-items: center; gap: $spacing-sm; } .time { color: $text-placeholder; font-size: $font-size-small; display: flex; align-items: center; gap: 4px; } } } } }
</style>
