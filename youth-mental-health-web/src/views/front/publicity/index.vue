<template>
  <div class="publicity-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>心理宣传</el-breadcrumb-item></el-breadcrumb></div>
    <div class="publicity-list" v-loading="loading">
      <div v-for="item in list" :key="item.id" class="publicity-card" @click="$router.push(`/front/publicity/${item.id}`)">
        <div class="cover" :style="{ backgroundImage: `url(${item.cover})` }"></div>
        <div class="info">
          <h3>{{ item.title }}</h3>
          <p>{{ item.summary }}</p>
          <div class="meta"><span><svg-icon name="view" :size="14" /> {{ item.viewCount || 0 }}</span><span><svg-icon name="time" :size="14" /> {{ formatDate(item.createTime) }}</span></div>
        </div>
      </div>
      <el-empty v-if="!loading && list.length === 0" description="暂无数据" />
    </div>
    <div class="pagination-center" v-if="total > 0"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" /></div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getPublicityList } from '@/api/front/publicity'
import { formatDate } from '@/utils/common'

const loading = ref(false), list = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 9 })

async function fetchList() { loading.value = true; try { const res = await getPublicityList(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.publicity-page { .publicity-list { display: grid; grid-template-columns: repeat(3, 1fr); gap: $spacing-md; min-height: 300px;
    .publicity-card { background: $bg-white; border-radius: $border-radius-base; overflow: hidden; cursor: pointer; transition: $transition-base; box-shadow: $shadow-light; &:hover { transform: translateY(-4px); box-shadow: $shadow-medium; }
      .cover { height: 160px; background-size: cover; background-position: center; background-color: $bg-color; }
      .info { padding: $spacing-md; h3 { font-size: $font-size-medium; color: $text-primary; margin-bottom: $spacing-sm; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; } p { font-size: $font-size-small; color: $text-secondary; line-height: 1.6; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; margin-bottom: $spacing-sm; height: 42px; } .meta { display: flex; gap: $spacing-md; color: $text-placeholder; font-size: $font-size-small; span { display: flex; align-items: center; gap: 4px; } } } } } }
</style>
