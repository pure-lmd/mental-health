<template>
  <div class="register-page">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>

    <div class="register-container">
      <!-- 左侧介绍 -->
      <div class="intro-section">
        <div class="intro-content">
          <div class="logo">
            <svg-icon name="mental" :size="56" color="#fff" />
          </div>
          <h1 class="title">加入我们</h1>
          <p class="subtitle">开启心理健康之旅</p>
          <ul class="benefits">
            <li>
              <svg-icon name="check" :size="18" color="#fff" />
              <span>免费心理健康科普知识</span>
            </li>
            <li>
              <svg-icon name="check" :size="18" color="#fff" />
              <span>专业心理测试评估</span>
            </li>
            <li>
              <svg-icon name="check" :size="18" color="#fff" />
              <span>AI智能心理咨询</span>
            </li>
            <li>
              <svg-icon name="check" :size="18" color="#fff" />
              <span>丰富心理活动参与</span>
            </li>
          </ul>
        </div>
      </div>

      <!-- 右侧注册表单 -->
      <div class="form-section">
        <div class="form-wrapper">
          <h2 class="form-title">用户注册</h2>
          <p class="form-subtitle">请填写以下信息完成注册</p>

          <el-form
            ref="registerFormRef"
            :model="registerForm"
            :rules="registerRules"
            class="register-form"
          >
            <el-form-item prop="username">
              <el-input
                v-model="registerForm.username"
                placeholder="请输入用户名"
                size="large"
                :prefix-icon="UserIcon"
              />
            </el-form-item>

            <el-form-item prop="nickname">
              <el-input
                v-model="registerForm.nickname"
                placeholder="请输入昵称"
                size="large"
                :prefix-icon="UserIcon"
              />
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="registerForm.password"
                type="password"
                placeholder="请输入密码"
                size="large"
                show-password
                :prefix-icon="LockIcon"
              />
            </el-form-item>

            <el-form-item prop="confirmPassword">
              <el-input
                v-model="registerForm.confirmPassword"
                type="password"
                placeholder="请确认密码"
                size="large"
                show-password
                :prefix-icon="LockIcon"
              />
            </el-form-item>

            <el-form-item prop="email">
              <el-input
                v-model="registerForm.email"
                placeholder="请输入邮箱（选填）"
                size="large"
                :prefix-icon="MessageIcon"
              />
            </el-form-item>

            <el-form-item prop="phone">
              <el-input
                v-model="registerForm.phone"
                placeholder="请输入手机号（选填）"
                size="large"
                :prefix-icon="PhoneIcon"
              />
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                class="register-btn"
                size="large"
                :loading="loading"
                @click="handleRegister"
              >
                {{ loading ? '注册中...' : '立即注册' }}
              </el-button>
            </el-form-item>
          </el-form>

          <div class="login-link">
            已有账号？
            <router-link to="/login">立即登录</router-link>
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
import { User, Lock, Message, Phone } from '@element-plus/icons-vue'
import { register } from '@/api/front/auth'

const UserIcon = User
const LockIcon = Lock
const MessageIcon = Message
const PhoneIcon = Phone

const router = useRouter()
const registerFormRef = ref(null)
const loading = ref(false)

const registerForm = reactive({
  username: '',
  nickname: '',
  password: '',
  confirmPassword: '',
  email: '',
  phone: ''
})

// 确认密码校验
const validateConfirmPassword = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const registerRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在3到20个字符', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ]
}

// 处理注册
async function handleRegister() {
  const valid = await registerFormRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true

  try {
    const { username, nickname, password, email, phone } = registerForm
    await register({ username, nickname, password, email, phone })
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (error) {
    console.error('注册失败:', error)
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url('/images/register.jpg') no-repeat center center;
  background-size: cover;
  position: relative;
  overflow: hidden;
  padding: $spacing-lg 0;
}

// 背景装饰
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
  }
}

.register-container {
  display: flex;
  width: 920px;
  background: $bg-white;
  border-radius: $border-radius-large;
  box-shadow: 0 12px 40px rgba(54, 179, 126, 0.12);
  overflow: hidden;
  position: relative;
  z-index: 1;
}

// 左侧介绍区
.intro-section {
  width: 38%;
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
      margin-bottom: $spacing-sm;
    }

    .subtitle {
      font-size: $font-size-base;
      opacity: 0.9;
      margin-bottom: $spacing-xl * 1.5;
    }

    .benefits {
      text-align: left;

      li {
        display: flex;
        align-items: center;
        gap: $spacing-sm;
        margin-bottom: $spacing-md;
        font-size: $font-size-base;
        opacity: 0.95;

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
  padding: $spacing-xl * 1.5;
  display: flex;
  align-items: center;
  justify-content: center;

  .form-wrapper {
    width: 100%;
    max-width: 380px;
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

// 注册表单
.register-form {
  .el-form-item {
    margin-bottom: $spacing-md;
  }

  .register-btn {
    width: 100%;
    height: 46px;
    font-size: $font-size-medium;
    background: linear-gradient(135deg, $primary-color 0%, $accent-color 100%);
    border: none;
    border-radius: $border-radius-base;
    letter-spacing: 2px;
    margin-top: $spacing-sm;

    &:hover {
      opacity: 0.9;
    }
  }
}

// 登录链接
.login-link {
  text-align: center;
  color: $text-secondary;
  margin-top: $spacing-md;

  a {
    color: $primary-color;
    font-weight: 500;

    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
