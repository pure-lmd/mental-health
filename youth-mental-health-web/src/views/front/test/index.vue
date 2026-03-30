<template>
  <div class="test-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>心理测试</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="test-list" v-loading="loading">
      <div v-for="item in list" :key="item.id" class="test-card">
        <div class="cover" :style="{ backgroundImage: `url(${item.cover})` }"></div>
        <div class="info">
          <h3>{{ item.title }}</h3>
          <p>{{ item.description }}</p>
          <div class="meta">
            <span>总分：{{ item.totalScore }}分</span>
            <span>|</span>
            <span>题目数：{{ item.questionCount || '-' }}题</span>
          </div>
          <el-button type="primary" @click="$router.push(`/front/test/answer/${item.id}`)">
            <svg-icon name="test" :size="14" />
            <span>开始测试</span>
          </el-button>
        </div>
      </div>
      <el-empty v-if="!loading && list.length === 0" description="暂无测试" />
    </div>
    
    <div class="pagination-center" v-if="total > 0">
      <el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getTestList } from '@/api/front/test'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

async function fetchList() { loading.value = true; try { const res = await getTestList(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }

onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.test-page {
  .test-list {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: $spacing-lg;
    min-height: 300px;
    
    .test-card {
      background: $bg-white;
      border-radius: $border-radius-base;
      overflow: hidden;
      display: flex;
      box-shadow: $shadow-light;
      transition: $transition-base;
      
      &:hover { box-shadow: $shadow-medium; }
      
      .cover { width: 200px; height: 180px; background-size: cover; background-position: center; background-color: $bg-color; flex-shrink: 0; }
      
      .info {
        flex: 1;
        padding: $spacing-lg;
        display: flex;
        flex-direction: column;
        
        h3 { font-size: $font-size-large; color: $text-primary; margin-bottom: $spacing-sm; }
        p { flex: 1; font-size: $font-size-small; color: $text-secondary; line-height: 1.6; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden; }
        .meta { color: $text-placeholder; font-size: $font-size-small; margin: $spacing-md 0; display: flex; gap: $spacing-sm; }
        .el-button { align-self: flex-start; }
      }
    }
  }
}
</style>
