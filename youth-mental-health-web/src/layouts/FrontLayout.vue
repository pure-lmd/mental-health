<template>
  <div class="front-layout">
    <!-- 顶部导航 -->
    <header class="header">
      <div class="header-container">
        <div class="logo" @click="$router.push('/front/home')">
          <svg-icon name="mental" :size="32" color="#36B37E" />
          <span class="logo-text">大学生心理健康</span>
        </div>

        <nav class="nav-menu">
          <router-link to="/front/home" class="nav-item" :class="{ active: isActive('/front/home') }">
            <svg-icon name="home" :size="15" />
            <span>首页</span>
          </router-link>
          <router-link to="/front/knowledge" class="nav-item" :class="{ active: isActive('/front/knowledge') }">
            <svg-icon name="knowledge" :size="15" />
            <span>心理科普</span>
          </router-link>
          <router-link to="/front/test" class="nav-item" :class="{ active: isActive('/front/test') }">
            <svg-icon name="test" :size="15" />
            <span>心理测试</span>
          </router-link>
          <router-link to="/front/activity" class="nav-item" :class="{ active: isActive('/front/activity') }">
            <svg-icon name="activity" :size="15" />
            <span>心理活动</span>
          </router-link>
          <router-link to="/front/news" class="nav-item" :class="{ active: isActive('/front/news') }">
            <svg-icon name="news" :size="15" />
            <span>文化资讯</span>
          </router-link>
          <router-link to="/front/publicity" class="nav-item" :class="{ active: isActive('/front/publicity') }">
            <svg-icon name="publicity" :size="15" />
            <span>心理宣传</span>
          </router-link>
          <router-link to="/front/ai-chat" class="nav-item" :class="{ active: isActive('/front/ai-chat') }">
            <svg-icon name="ai" :size="15" />
            <span>AI助手</span>
          </router-link>
        </nav>

        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <div class="user-info">
              <el-avatar :size="34" :src="userInfo?.avatar">
                <svg-icon name="user" :size="18" />
              </el-avatar>
              <span class="username">{{ userInfo?.nickname || userInfo?.username || '用户' }}</span>
              <svg-icon name="arrow-down" :size="12" />
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <svg-icon name="user" :size="14" />
                  <span>个人信息</span>
                </el-dropdown-item>
                <el-dropdown-item command="collect">
                  <svg-icon name="collect" :size="14" />
                  <span>我的收藏</span>
                </el-dropdown-item>
                <el-dropdown-item command="registration">
                  <svg-icon name="registration" :size="14" />
                  <span>我的预约</span>
                </el-dropdown-item>
                <el-dropdown-item command="feedback">
                  <svg-icon name="feedback" :size="14" />
                  <span>我的反馈</span>
                </el-dropdown-item>
                <el-dropdown-item command="testRecord">
                  <svg-icon name="record" :size="14" />
                  <span>测试记录</span>
                </el-dropdown-item>
                <el-dropdown-item command="logout" divided>
                  <svg-icon name="logout" :size="14" />
                  <span>退出登录</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>

    <!-- 内容区域 -->
    <main class="main-content">
      <router-view />
    </main>

    <!-- 底部 -->
    <footer class="footer">
      <div class="footer-container">
        <p>大学生心理健康管理系统 - 关爱大学生心理健康</p>
        <p class="copyright">Copyright 2026 Ti. All Rights Reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const userInfo = computed(() => userStore.userInfo)

function isActive(path) {
  return route.path.startsWith(path)
}

function handleCommand(command) {
  const commandMap = {
    profile: '/front/user/profile',
    collect: '/front/user/collect',
    registration: '/front/user/registration',
    feedback: '/front/user/feedback',
    testRecord: '/front/user/test-record'
  }

  if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      userStore.logout()
      router.push('/login')
    }).catch(() => {})
  } else if (commandMap[command]) {
    router.push(commandMap[command])
  }
}

onMounted(async () => {
  if (!userInfo.value) {
    try {
      await userStore.fetchUserInfo()
    } catch (error) {
      router.push('/login')
    }
  }
})
</script>

<style lang="scss" scoped>
.front-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: $bg-color;
}

.header {
  height: 64px;
  background: $bg-white;
  border-bottom: 1px solid $border-light;
  box-shadow: 0 1px 8px rgba(54, 179, 126, 0.06);
  position: sticky;
  top: 0;
  z-index: 1000;

  .header-container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 $spacing-lg;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .logo {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    cursor: pointer;

    .logo-text {
      color: $primary-color;
      font-size: $font-size-large;
      font-weight: 700;
    }
  }

  .nav-menu {
    display: flex;
    align-items: center;
    gap: 2px;

    .nav-item {
      display: flex;
      align-items: center;
      gap: 4px;
      padding: 6px 14px;
      color: $text-regular;
      border-radius: $border-radius-round;
      transition: $transition-fast;
      font-size: $font-size-base;

      &:hover {
        background-color: rgba($primary-color, 0.06);
        color: $primary-color;
      }

      &.active {
        background-color: rgba($primary-color, 0.1);
        color: $primary-color;
        font-weight: 600;
      }
    }
  }

  .header-right {
    .user-info {
      display: flex;
      align-items: center;
      gap: $spacing-sm;
      cursor: pointer;
      padding: $spacing-xs $spacing-sm;
      border-radius: $border-radius-round;
      transition: $transition-fast;

      &:hover {
        background-color: rgba($primary-color, 0.06);
      }

      .username {
        color: $text-primary;
        font-weight: 500;
        font-size: $font-size-base;
      }

      .svg-icon {
        color: $text-secondary;
      }
    }
  }
}

.main-content {
  flex: 1;
  max-width: 1400px;
  margin: 0 auto;
  padding: $spacing-lg;
  width: 100%;
}

.footer {
  background-color: $bg-dark;
  padding: $spacing-xl 0;
  margin-top: auto;

  .footer-container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 $spacing-lg;
    text-align: center;

    p {
      color: rgba(255, 255, 255, 0.6);
      margin: 0;
      line-height: 1.8;
    }

    .copyright {
      font-size: $font-size-small;
      margin-top: $spacing-sm;
    }
  }
}
</style>
