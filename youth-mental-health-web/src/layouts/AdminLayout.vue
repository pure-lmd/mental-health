<template>
  <div class="admin-layout">
    <!-- 侧边栏 -->
    <aside class="sidebar" :class="{ collapsed: isCollapsed }">
      <div class="logo">
        <svg-icon name="mental" :size="32" color="#36B37E" />
        <span v-show="!isCollapsed" class="logo-text">心理健康管理</span>
      </div>
      
      <el-menu
        :default-active="currentRoute"
        :collapse="isCollapsed"
        :collapse-transition="false"
        background-color="#F0F5F3"
        text-color="#546E7A"
        active-text-color="#36B37E"
        router
      >
        <el-menu-item index="/admin/home">
          <svg-icon name="home" :size="18" />
          <template #title>首页</template>
        </el-menu-item>
        
        <el-menu-item index="/admin/statistics">
          <svg-icon name="statistics" :size="18" />
          <template #title>数据统计</template>
        </el-menu-item>
        
        <el-sub-menu index="content">
          <template #title>
            <svg-icon name="knowledge" :size="18" />
            <span>内容管理</span>
          </template>
          <el-menu-item index="/admin/banner">
            <svg-icon name="image" :size="16" />
            <template #title>轮播图管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/category">
            <svg-icon name="category" :size="16" />
            <template #title>心理分类管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/knowledge">
            <svg-icon name="knowledge" :size="16" />
            <template #title>心理科普管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/publicity">
            <svg-icon name="publicity" :size="16" />
            <template #title>心理宣传管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/news">
            <svg-icon name="news" :size="16" />
            <template #title>资讯管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/notice">
            <svg-icon name="notice" :size="16" />
            <template #title>通知公告管理</template>
          </el-menu-item>
        </el-sub-menu>
        
        <el-menu-item index="/admin/test">
          <svg-icon name="test" :size="18" />
          <template #title>心理测试管理</template>
        </el-menu-item>
        
        <el-sub-menu index="activity">
          <template #title>
            <svg-icon name="activity" :size="18" />
            <span>活动管理</span>
          </template>
          <el-menu-item index="/admin/activity">
            <svg-icon name="activity" :size="16" />
            <template #title>活动信息管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/registration">
            <svg-icon name="registration" :size="16" />
            <template #title>报名管理</template>
          </el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="interact">
          <template #title>
            <svg-icon name="comment" :size="18" />
            <span>互动管理</span>
          </template>
          <el-menu-item index="/admin/collect">
            <svg-icon name="collect" :size="16" />
            <template #title>收藏管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/comment">
            <svg-icon name="comment" :size="16" />
            <template #title>评论管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/feedback">
            <svg-icon name="feedback" :size="16" />
            <template #title>反馈管理</template>
          </el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="system">
          <template #title>
            <svg-icon name="setting" :size="18" />
            <span>系统管理</span>
          </template>
          <el-menu-item index="/admin/admin">
            <svg-icon name="admin" :size="16" />
            <template #title>管理员管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/user">
            <svg-icon name="user" :size="16" />
            <template #title>用户管理</template>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </aside>
    
    <!-- 主体内容 -->
    <div class="main-container">
      <!-- 顶部导航 -->
      <header class="header">
        <div class="header-left">
          <div class="collapse-btn" @click="toggleCollapse">
            <svg-icon :name="isCollapsed ? 'menu-unfold' : 'menu-fold'" :size="20" />
          </div>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/admin/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentTitle">{{ currentTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <div class="user-info">
              <el-avatar :size="36" :src="adminInfo?.avatar">
                <svg-icon name="user" :size="20" />
              </el-avatar>
              <span class="username">{{ adminInfo?.nickname || adminInfo?.username || '管理员' }}</span>
              <svg-icon name="arrow-down" :size="14" />
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <svg-icon name="user" :size="14" />
                  <span>个人信息</span>
                </el-dropdown-item>
                <el-dropdown-item command="logout" divided>
                  <svg-icon name="logout" :size="14" />
                  <span>退出登录</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>
      
      <!-- 内容区域 -->
      <main class="content">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { useAdminStore } from '@/stores/admin'

const route = useRoute()
const router = useRouter()
const adminStore = useAdminStore()

const isCollapsed = ref(false)
const adminInfo = computed(() => adminStore.adminInfo)
const currentRoute = computed(() => route.path)
const currentTitle = computed(() => route.meta?.title || '')

// 切换折叠
function toggleCollapse() {
  isCollapsed.value = !isCollapsed.value
}

// 处理下拉菜单命令
function handleCommand(command) {
  if (command === 'profile') {
    router.push('/admin/profile')
  } else if (command === 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      adminStore.logout()
      router.push('/login')
    }).catch(() => {})
  }
}

// 获取管理员信息
onMounted(async () => {
  if (!adminInfo.value) {
    try {
      await adminStore.fetchAdminInfo()
    } catch (error) {
      router.push('/login')
    }
  }
})
</script>

<style lang="scss" scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background-color: $bg-color;
}

.sidebar {
  position: fixed;
  left: 0;
  top: 0;
  bottom: 0;
  width: $sidebar-width;
  background-color: $bg-color;
  border-right: 1px solid $border-light;
  transition: width 0.3s ease;
  z-index: 1000;
  overflow-y: auto;
  overflow-x: hidden;
  
  &.collapsed {
    width: $sidebar-collapsed-width;
    
    .logo-text {
      display: none;
    }
  }
  
  .logo {
    height: $header-height;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: $spacing-sm;
    border-bottom: 1px solid $border-light;
    
    .logo-text {
      color: $text-primary;
      font-size: $font-size-large;
      font-weight: 600;
      white-space: nowrap;
    }
  }
  
  .el-menu {
    border-right: none;
    
    .el-menu-item,
    .el-sub-menu__title {
      display: flex;
      align-items: center;
      
      .svg-icon {
        flex-shrink: 0;
        margin-right: $spacing-sm;
      }
    }
    
    .el-menu-item.is-active {
      background-color: rgba($primary-color, 0.12) !important;
      color: $primary-color !important;
      font-weight: 500;
    }
  }
}

.main-container {
  flex: 1;
  margin-left: $sidebar-width;
  transition: margin-left 0.3s ease;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  
  .sidebar.collapsed + & {
    margin-left: $sidebar-collapsed-width;
  }
}

.header {
  height: $header-height;
  background-color: $bg-white;
  box-shadow: $shadow-light;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 $spacing-lg;
  position: sticky;
  top: 0;
  z-index: 999;
  
  .header-left {
    display: flex;
    align-items: center;
    gap: $spacing-md;
    
    .collapse-btn {
      cursor: pointer;
      padding: $spacing-sm;
      border-radius: $border-radius-small;
      transition: $transition-fast;
      
      &:hover {
        background-color: $bg-color;
        color: $primary-color;
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
      border-radius: $border-radius-base;
      transition: $transition-fast;
      
      &:hover {
        background-color: $bg-color;
      }
      
      .username {
        color: $text-primary;
        font-weight: 500;
      }
    }
  }
}

.content {
  flex: 1;
  padding: $spacing-lg;
  overflow-y: auto;
}

// 修复侧边栏折叠时的margin
.sidebar.collapsed ~ .main-container {
  margin-left: $sidebar-collapsed-width;
}
</style>
