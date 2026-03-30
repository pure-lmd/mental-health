<template>
  <div class="admin-home">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <div class="welcome-text">
        <h1>欢迎回来，{{ adminInfo?.nickname || '管理员' }}</h1>
        <p>今天是 {{ currentDate }}，祝您工作愉快！</p>
      </div>
      <div class="welcome-illustration">
        <svg-icon name="mental" :size="80" color="#40A9FF" />
      </div>
    </div>
    
    <!-- 数据概览 -->
    <div class="overview-section">
      <div class="stat-card" style="--card-color: #40A9FF">
        <div class="stat-icon">
          <svg-icon name="user" :size="32" />
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ overview.userCount || 0 }}</div>
          <div class="stat-label">用户总数</div>
        </div>
      </div>
      
      <div class="stat-card" style="--card-color: #FF7A45">
        <div class="stat-icon">
          <svg-icon name="test" :size="32" />
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ overview.testCount || 0 }}</div>
          <div class="stat-label">测试总数</div>
        </div>
      </div>
      
      <div class="stat-card" style="--card-color: #67C23A">
        <div class="stat-icon">
          <svg-icon name="activity" :size="32" />
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ overview.activityCount || 0 }}</div>
          <div class="stat-label">活动数量</div>
        </div>
      </div>
      
      <div class="stat-card" style="--card-color: #E6A23C">
        <div class="stat-icon">
          <svg-icon name="knowledge" :size="32" />
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ overview.knowledgeCount || 0 }}</div>
          <div class="stat-label">科普文章</div>
        </div>
      </div>
    </div>
    
    <!-- 快捷入口 -->
    <div class="quick-entry-section">
      <h3 class="section-title">
        <svg-icon name="category" :size="20" />
        <span>快捷入口</span>
      </h3>
      <div class="quick-entry-grid">
        <div class="entry-item" @click="$router.push('/admin/user')">
          <svg-icon name="user" :size="28" color="#40A9FF" />
          <span>用户管理</span>
        </div>
        <div class="entry-item" @click="$router.push('/admin/test')">
          <svg-icon name="test" :size="28" color="#FF7A45" />
          <span>测试管理</span>
        </div>
        <div class="entry-item" @click="$router.push('/admin/knowledge')">
          <svg-icon name="knowledge" :size="28" color="#67C23A" />
          <span>科普管理</span>
        </div>
        <div class="entry-item" @click="$router.push('/admin/activity')">
          <svg-icon name="activity" :size="28" color="#E6A23C" />
          <span>活动管理</span>
        </div>
        <div class="entry-item" @click="$router.push('/admin/feedback')">
          <svg-icon name="feedback" :size="28" color="#909399" />
          <span>反馈管理</span>
        </div>
        <div class="entry-item" @click="$router.push('/admin/statistics')">
          <svg-icon name="statistics" :size="28" color="#F56C6C" />
          <span>数据统计</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import dayjs from 'dayjs'
import { useAdminStore } from '@/stores/admin'
import { getOverview } from '@/api/admin/statistics'

const adminStore = useAdminStore()
const adminInfo = computed(() => adminStore.adminInfo)

const currentDate = dayjs().format('YYYY年MM月DD日 dddd')
const overview = ref({})

async function fetchOverview() {
  try {
    const res = await getOverview()
    overview.value = res.data
  } catch (error) {
    console.error('获取概览数据失败:', error)
  }
}

onMounted(() => {
  fetchOverview()
})
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.admin-home {
  .welcome-section {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: linear-gradient(135deg, $primary-color 0%, $accent-color 100%);
    border-radius: $border-radius-large;
    padding: $spacing-xl * 1.5;
    margin-bottom: $spacing-lg;
    color: #fff;
    
    .welcome-text {
      h1 {
        font-size: $font-size-xxl;
        margin-bottom: $spacing-sm;
      }
      
      p {
        opacity: 0.9;
      }
    }
    
    .welcome-illustration {
      opacity: 0.8;
    }
  }
  
  .overview-section {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: $spacing-md;
    margin-bottom: $spacing-lg;
    
    .stat-card {
      background: $bg-white;
      border-radius: $border-radius-base;
      padding: $spacing-lg;
      display: flex;
      align-items: center;
      gap: $spacing-md;
      box-shadow: $shadow-light;
      transition: $transition-base;
      
      &:hover {
        box-shadow: $shadow-medium;
        transform: translateY(-2px);
      }
      
      .stat-icon {
        width: 60px;
        height: 60px;
        border-radius: $border-radius-base;
        background: rgba(var(--card-color), 0.1);
        display: flex;
        align-items: center;
        justify-content: center;
        color: var(--card-color);
        background-color: color-mix(in srgb, var(--card-color) 10%, transparent);
        
        .svg-icon {
          color: var(--card-color);
        }
      }
      
      .stat-info {
        .stat-value {
          font-size: 28px;
          font-weight: 700;
          color: $text-primary;
        }
        
        .stat-label {
          color: $text-secondary;
          margin-top: $spacing-xs;
        }
      }
    }
  }
  
  .quick-entry-section {
    background: $bg-white;
    border-radius: $border-radius-base;
    padding: $spacing-lg;
    box-shadow: $shadow-light;
    
    .section-title {
      display: flex;
      align-items: center;
      gap: $spacing-sm;
      font-size: $font-size-large;
      font-weight: 600;
      color: $text-primary;
      margin-bottom: $spacing-lg;
    }
    
    .quick-entry-grid {
      display: grid;
      grid-template-columns: repeat(6, 1fr);
      gap: $spacing-md;
      
      .entry-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: $spacing-sm;
        padding: $spacing-lg;
        background: $bg-color;
        border-radius: $border-radius-base;
        cursor: pointer;
        transition: $transition-base;
        
        &:hover {
          background: linear-gradient(135deg, rgba($primary-color, 0.1) 0%, rgba($accent-color, 0.1) 100%);
          transform: translateY(-2px);
        }
        
        span {
          color: $text-primary;
          font-weight: 500;
        }
      }
    }
  }
}
</style>
