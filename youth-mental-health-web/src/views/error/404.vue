<template>
  <div class="error-page">
    <div class="error-content">
      <div class="error-code">404</div>
      <h2 class="error-title">页面不存在</h2>
      <p class="error-desc">抱歉，您访问的页面不存在或已被删除</p>
      <div class="error-actions">
        <el-button type="primary" @click="goHome">
          <svg-icon name="home" :size="16" />
          <span>返回首页</span>
        </el-button>
        <el-button @click="goBack">
          <svg-icon name="arrow-right" :size="16" style="transform: rotate(180deg)" />
          <span>返回上页</span>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { getUserType } from '@/utils/storage'

const router = useRouter()

function goHome() {
  const userType = getUserType()
  if (userType === 'admin') {
    router.push('/admin/home')
  } else if (userType === 'user') {
    router.push('/front/home')
  } else {
    router.push('/login')
  }
}

function goBack() {
  router.go(-1)
}
</script>

<style lang="scss" scoped>
.error-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, $bg-color 0%, #e8f4fc 100%);
}

.error-content {
  text-align: center;
  padding: $spacing-xl * 2;
  
  .error-code {
    font-size: 120px;
    font-weight: 700;
    background: linear-gradient(135deg, $primary-color 0%, $accent-color 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    line-height: 1;
    margin-bottom: $spacing-lg;
  }
  
  .error-title {
    font-size: $font-size-xxl;
    color: $text-primary;
    margin-bottom: $spacing-md;
  }
  
  .error-desc {
    font-size: $font-size-medium;
    color: $text-secondary;
    margin-bottom: $spacing-xl;
  }
  
  .error-actions {
    display: flex;
    justify-content: center;
    gap: $spacing-md;
    
    .el-button {
      display: flex;
      align-items: center;
      gap: $spacing-sm;
    }
  }
}
</style>
