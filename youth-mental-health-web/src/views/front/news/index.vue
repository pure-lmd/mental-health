<template>
  <div class="news-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>文化资讯</el-breadcrumb-item></el-breadcrumb></div>
    <div class="news-list" v-loading="loading">
      <div v-for="(item, index) in list" :key="item.id" class="news-card" :class="{ reverse: index % 2 === 1 }" @click="$router.push(`/front/news/${item.id}`)">
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
import { getNewsList } from '@/api/front/news'
import { formatDate } from '@/utils/common'

const loading = ref(false), list = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

async function fetchList() { loading.value = true; try { const res = await getNewsList(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.news-page {
  .news-list {
    display: flex;
    flex-direction: column;
    gap: $spacing-lg;
    min-height: 300px;

    .news-card {
      display: flex;
      background: $bg-white;
      border-radius: $border-radius-base;
      overflow: hidden;
      cursor: pointer;
      transition: $transition-base;
      box-shadow: $shadow-light;

      &:hover {
        transform: translateY(-4px);
        box-shadow: $shadow-medium;
      }

      &.reverse {
        flex-direction: row-reverse;
      }

      .cover {
        width: 320px;
        min-height: 200px;
        background-size: cover;
        background-position: center;
        background-color: $bg-color;
        flex-shrink: 0;
      }

      .info {
        flex: 1;
        padding: $spacing-lg;
        display: flex;
        flex-direction: column;
        justify-content: center;

        h3 {
          font-size: $font-size-xl;
          color: $text-primary;
          margin-bottom: $spacing-md;
        }

        p {
          font-size: $font-size-base;
          color: $text-secondary;
          line-height: 1.8;
          display: -webkit-box;
          -webkit-line-clamp: 3;
          -webkit-box-orient: vertical;
          overflow: hidden;
          margin-bottom: $spacing-md;
        }

        .meta {
          display: flex;
          gap: $spacing-md;
          color: $text-placeholder;
          font-size: $font-size-small;
          span { display: flex; align-items: center; gap: 4px; }
        }
      }
    }
  }
}
</style>
