<template>
  <div class="login-page">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="circle circle-4"></div>
    </div>

    <div class="login-container">
      <!-- 左侧介绍 -->
      <div class="intro-section">
        <div class="intro-content">
          <div class="logo">
            <svg-icon name="mental" :size="56" color="#fff" />
          </div>
          <h1 class="title">大学生心理健康管理系统</h1>
          <p class="subtitle">关爱大学生心理健康，守护成长每一步</p>
          <div class="features">
            <div class="feature-item">
              <svg-icon name="knowledge" :size="22" color="#fff" />
              <span>心理科普知识</span>
            </div>
            <div class="feature-item">
              <svg-icon name="test" :size="22" color="#fff" />
              <span>专业心理测试</span>
            </div>
            <div class="feature-item">
              <svg-icon name="ai" :size="22" color="#fff" />
              <span>AI心理助手</span>
            </div>
            <div class="feature-item">
              <svg-icon name="activity" :size="22" color="#fff" />
              <span>心理活动参与</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧登录表单 -->
      <div class="form-section">
        <div class="form-wrapper">
          <h2 class="form-title">欢迎登录</h2>
          <p class="form-subtitle">请选择您的身份进行登录</p>

          <!-- 身份选择 -->
          <div class="role-selector">
            <div
              class="role-item"
              :class="{ active: loginForm.role === 'user' }"
              @click="loginForm.role = 'user'"
            >
              <el-icon :size="26"><User /></el-icon>
              <span>普通用户</span>
            </div>
            <div
              class="role-item"
              :class="{ active: loginForm.role === 'admin' }"
              @click="loginForm.role = 'admin'"
            >
              <el-icon :size="26"><Setting /></el-icon>
              <span>管理员</span>
            </div>
          </div>

          <!-- 登录表单 -->
          <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            class="login-form"
          >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名"
                size="large"
                :prefix-icon="UserIcon"
              />
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                size="large"
                show-password
                :prefix-icon="LockIcon"
                @keyup.enter="handleLogin"
              />
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                class="login-btn"
                size="large"
                :loading="loading"
                @click="handleLogin"
              >
                {{ loading ? '登录中...' : '立即登录' }}
              </el-button>
            </el-form-item>
          </el-form>

          <!-- 注册链接（仅用户显示） -->
          <div v-if="loginForm.role === 'user'" class="register-link">
            还没有账号？
            <router-link to="/register">立即注册</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, Setting } from '@element-plus/icons-vue'
import { adminLogin } from '@/api/admin/admin'
import { login as userLogin } from '@/api/front/auth'
import { useAdminStore } from '@/stores/admin'
import { useUserStore } from '@/stores/user'

const UserIcon = User
const LockIcon = Lock

const router = useRouter()
const adminStore = useAdminStore()
const userStore = useUserStore()

const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  role: 'user',
  username: '',
  password: ''
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

// 处理登录
async function handleLogin() {
  const valid = await loginFormRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true

  try {
    const { username, password, role } = loginForm
    let res

    if (role === 'admin') {
      res = await adminLogin({ username, password })
      adminStore.handleLoginSuccess(res.data.token)
      // 获取管理员信息，失败不阻断跳转
      await adminStore.fetchAdminInfo().catch(() => {})
      ElMessage.success('登录成功')
      router.push('/admin/home')
    } else {
      res = await userLogin({ username, password })
      userStore.handleLoginSuccess(res.data.token)
      // 获取用户信息，失败不阻断跳转
      await userStore.fetchUserInfo().catch(() => {})
      ElMessage.success('登录成功')
      router.push('/front/home')
    }
  } catch (error) {
    console.error('登录失败:', error)
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url('/images/login.jpg') no-repeat center center;
  background-size: cover;
  position: relative;
  overflow: hidden;
}

// 背景装饰 - 柔和的绿色圆形
.bg-decoration {
  position: absolute;
  width: 100%;
  height: 100%;
  overflow: hidden;

  .circle {
    position: absolute;
    border-radius: 50%;

    &.circle-1 {
      width: 500px;
      height: 500px;
      top: -150px;
      right: -100px;
      background: radial-gradient(circle, rgba($primary-light, 0.15), transparent 70%);
    }

    &.circle-2 {
      width: 400px;
      height: 400px;
      bottom: -100px;
      left: -80px;
      background: radial-gradient(circle, rgba($accent-color, 0.12), transparent 70%);
    }

    &.circle-3 {
      width: 250px;
      height: 250px;
      top: 40%;
      left: 15%;
      background: radial-gradient(circle, rgba($warm-color, 0.1), transparent 70%);
    }

    &.circle-4 {
      width: 300px;
      height: 300px;
      bottom: 20%;
      right: 10%;
      background: radial-gradient(circle, rgba($primary-color, 0.08), transparent 70%);
    }
  }
}

.login-container {
  display: flex;
  width: 1020px;
  min-height: 540px;
  background: $bg-white;
  border-radius: $border-radius-large;
  box-shadow: 0 12px 40px rgba(54, 179, 126, 0.12);
  overflow: hidden;
  position: relative;
  z-index: 1;
}

// 左侧介绍区 - 清新绿渐变
.intro-section {
  width: 48%;
  background: linear-gradient(160deg, $primary-color 0%, $accent-color 100%);
  padding: $spacing-xl * 2;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    width: 200px;
    height: 200px;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.08);
    top: -40px;
    right: -40px;
  }

  &::after {
    content: '';
    position: absolute;
    width: 150px;
    height: 150px;
    border-radius: 50%;
    background: rgba(255, 255, 255, 0.06);
    bottom: -30px;
    left: -30px;
  }

  .intro-content {
    text-align: center;
    color: #fff;
    position: relative;
    z-index: 1;

    .logo {
      margin-bottom: $spacing-lg;
    }

    .title {
      font-size: 26px;
      font-weight: 700;
      margin-bottom: $spacing-md;
      line-height: 1.3;
    }

    .subtitle {
      font-size: $font-size-base;
      opacity: 0.9;
      margin-bottom: $spacing-xl * 1.5;
    }

    .features {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: $spacing-sm;

      .feature-item {
        display: flex;
        align-items: center;
        gap: $spacing-sm;
        padding: $spacing-sm $spacing-md;
        background: rgba(255, 255, 255, 0.15);
        border-radius: $border-radius-round;
        font-size: $font-size-small;
        backdrop-filter: blur(4px);
        white-space: nowrap;

        .svg-icon {
          flex-shrink: 0;
        }
      }
    }
  }
}

// 右侧表单区
.form-section {
  flex: 1;
  padding: $spacing-xl * 2;
  display: flex;
  align-items: center;
  justify-content: center;

  .form-wrapper {
    width: 100%;
    max-width: 360px;
  }

  .form-title {
    font-size: $font-size-xxl;
    font-weight: 600;
    color: $text-primary;
    margin-bottom: $spacing-sm;
  }

  .form-subtitle {
    color: $text-secondary;
    margin-bottom: $spacing-lg;
  }
}

// 身份选择
.role-selector {
  display: flex;
  gap: $spacing-md;
  margin-bottom: $spacing-xl;

  .role-item {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: $spacing-sm;
    padding: $spacing-md;
    border: 2px solid $border-color;
    border-radius: $border-radius-base;
    cursor: pointer;
    transition: $transition-base;
    color: $text-secondary;

    &:hover {
      border-color: $primary-light;
      color: $primary-color;
    }

    &.active {
      border-color: $primary-color;
      background: rgba($primary-color, 0.06);
      color: $primary-color;
    }

    span {
      font-weight: 500;
      font-size: $font-size-small;
    }
  }
}

// 登录表单
.login-form {
  .el-form-item {
    margin-bottom: $spacing-lg;
  }

  .login-btn {
    width: 100%;
    height: 46px;
    font-size: $font-size-medium;
    background: linear-gradient(135deg, $primary-color 0%, $accent-color 100%);
    border: none;
    border-radius: $border-radius-base;
    letter-spacing: 2px;

    &:hover {
      opacity: 0.9;
    }
  }
}

// 注册链接
.register-link {
  text-align: center;
  color: $text-secondary;
  font-size: $font-size-base;

  a {
    color: $primary-color;
    font-weight: 500;

    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
