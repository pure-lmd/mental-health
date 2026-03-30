<template>
  <div class="knowledge-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>心理科普</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="page-content">
      <!-- 分类筛选 -->
      <div class="filter-section">
        <div class="filter-item">
          <span class="label">分类：</span>
          <div class="tags">
            <el-tag :type="!queryParams.categoryId ? '' : 'info'" effect="plain" @click="selectCategory(null)">全部</el-tag>
            <el-tag v-for="cat in categoryList" :key="cat.id" :type="queryParams.categoryId === cat.id ? '' : 'info'" effect="plain" @click="selectCategory(cat.id)">
              <span class="category-tag-content">
                <el-image v-if="cat.icon" :src="cat.icon" class="category-icon" fit="cover" />
                {{ cat.name }}
              </span>
            </el-tag>
          </div>
        </div>
        <div class="search-box">
          <el-input v-model="queryParams.keyword" placeholder="搜索科普..." clearable @keyup.enter="fetchList">
            <template #append><el-button @click="fetchList"><svg-icon name="search" :size="14" /></el-button></template>
          </el-input>
        </div>
      </div>
      
      <!-- 列表 -->
      <div class="knowledge-list" v-loading="loading">
        <div v-for="item in list" :key="item.id" class="knowledge-card" @click="$router.push(`/front/knowledge/${item.id}`)">
          <div class="cover" :style="{ backgroundImage: `url(${item.cover})` }"></div>
          <div class="info">
            <h3>{{ item.title }}</h3>
            <p class="summary">{{ item.summary }}</p>
            <div class="meta">
              <span><svg-icon name="view" :size="14" /> {{ item.viewCount || 0 }} 浏览</span>
              <span><svg-icon name="collect" :size="14" /> {{ item.collectCount || 0 }} 收藏</span>
              <span><svg-icon name="time" :size="14" /> {{ formatDate(item.createTime) }}</span>
            </div>
          </div>
        </div>
        <el-empty v-if="!loading && list.length === 0" description="暂无数据" />
      </div>
      
      <div class="pagination-center">
        <el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getCategoryList, getKnowledgeList } from '@/api/front/knowledge'
import { formatDate } from '@/utils/common'

const loading = ref(false)
const categoryList = ref([])
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 9, categoryId: null, keyword: '' })

async function fetchCategories() { try { const res = await getCategoryList(); categoryList.value = res.data || [] } catch (e) { console.error(e) } }
async function fetchList() { loading.value = true; try { const res = await getKnowledgeList(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
function selectCategory(id) { queryParams.categoryId = id; queryParams.pageNum = 1; fetchList() }

onMounted(() => { fetchCategories(); fetchList() })
</script>

<style lang="scss" scoped>
.knowledge-page {
  .filter-section {
    background: $bg-white;
    border-radius: $border-radius-base;
    padding: $spacing-md $spacing-lg;
    margin-bottom: $spacing-md;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: $shadow-light;
    
    .filter-item {
      display: flex;
      align-items: center;
      gap: $spacing-sm;
      .label { color: $text-secondary; white-space: nowrap; }
      .tags { display: flex; flex-wrap: wrap; gap: $spacing-xs; .el-tag { cursor: pointer; }
        .category-tag-content { display: inline-flex; align-items: center; gap: 4px; }
        .category-icon { width: 18px; height: 18px; border-radius: 50%; flex-shrink: 0; }
      }
    }
    .search-box { width: 300px; }
  }
  
  .knowledge-list {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: $spacing-md;
    min-height: 300px;
    
    .knowledge-card {
      background: $bg-white;
      border-radius: $border-radius-base;
      overflow: hidden;
      cursor: pointer;
      transition: $transition-base;
      box-shadow: $shadow-light;
      
      &:hover { transform: translateY(-4px); box-shadow: $shadow-medium; }
      
      .cover { height: 160px; background-size: cover; background-position: center; background-color: $bg-color; }
      
      .info {
        padding: $spacing-md;
        h3 { font-size: $font-size-medium; color: $text-primary; margin-bottom: $spacing-sm; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
        .summary { font-size: $font-size-small; color: $text-secondary; line-height: 1.6; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; margin-bottom: $spacing-sm; height: 42px; }
        .meta { display: flex; gap: $spacing-md; color: $text-placeholder; font-size: $font-size-small; span { display: flex; align-items: center; gap: 4px; } }
      }
    }
  }
}
</style>
