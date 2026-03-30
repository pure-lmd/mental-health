<template>
  <div class="activity-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>心理活动</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="activity-list" v-loading="loading">
      <div v-for="item in list" :key="item.id" class="activity-card" @click="$router.push(`/front/activity/${item.id}`)">
        <div class="cover" :style="{ backgroundImage: `url(${item.cover})` }">
          <el-tag :type="getActivityStatusType(item.status)" class="status-tag">{{ getActivityStatusText(item.status) }}</el-tag>
        </div>
        <div class="info">
          <h3>{{ item.title }}</h3>
          <p class="desc">{{ item.description }}</p>
          <div class="meta-group">
            <div class="meta"><svg-icon name="location" :size="14" /> {{ item.address }}</div>
            <div class="meta"><svg-icon name="time" :size="14" /> {{ formatDateTime(item.startTime) }}</div>
          </div>
          <div class="progress-bar">
            <div class="progress-header">
              <span>报名进度</span>
              <span>{{ item.currentNum || 0 }} / {{ item.maxNum || '-' }}</span>
            </div>
            <el-progress :percentage="item.maxNum ? Math.round((item.currentNum || 0) / item.maxNum * 100) : 0" :stroke-width="8" :show-text="false" color="#36B37E" />
          </div>
        </div>
      </div>
      <el-empty v-if="!loading && list.length === 0" description="暂无活动" />
    </div>
    
    <div class="pagination-center" v-if="total > 0">
      <el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getActivityList } from '@/api/front/activity'
import { formatDateTime, getActivityStatusText, getActivityStatusType } from '@/utils/common'

const loading = ref(false), list = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

async function fetchList() { loading.value = true; try { const res = await getActivityList(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }

onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.activity-page {
  .activity-list {
    display: flex;
    flex-direction: column;
    gap: $spacing-lg;
    min-height: 300px;

    .activity-card {
      background: $bg-white;
      border-radius: $border-radius-large;
      overflow: hidden;
      display: flex;
      box-shadow: $shadow-light;
      cursor: pointer;
      transition: $transition-base;

      &:hover {
        box-shadow: $shadow-medium;
        transform: translateX(4px);
      }

      .cover {
        width: 300px;
        min-height: 220px;
        background-size: cover;
        background-position: center;
        background-color: $bg-color;
        flex-shrink: 0;
        position: relative;

        .status-tag {
          position: absolute;
          top: $spacing-md;
          left: $spacing-md;
          font-size: $font-size-base;
          padding: 6px 14px;
          border-radius: $border-radius-round;
        }
      }

      .info {
        flex: 1;
        padding: $spacing-xl;
        display: flex;
        flex-direction: column;
        justify-content: center;

        h3 {
          font-size: $font-size-xl;
          color: $text-primary;
          margin-bottom: $spacing-sm;
          font-weight: 600;
        }

        .desc {
          font-size: $font-size-base;
          color: $text-secondary;
          margin-bottom: $spacing-lg;
          line-height: 1.7;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          overflow: hidden;
        }

        .meta-group {
          display: flex;
          flex-wrap: wrap;
          gap: $spacing-md $spacing-xl;
          margin-bottom: $spacing-lg;

          .meta {
            display: flex;
            align-items: center;
            gap: $spacing-sm;
            color: $text-regular;
            font-size: $font-size-small;
          }
        }

        .progress-bar {
          .progress-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: $spacing-xs;
            font-size: $font-size-small;
            color: $text-secondary;
          }
        }
      }
    }
  }
}
</style>
