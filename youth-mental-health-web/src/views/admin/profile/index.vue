<template>
  <div class="profile-page">
    <div class="page-title">
      <svg-icon name="user" :size="24" />
      <span>个人信息</span>
    </div>
    
    <div class="profile-container">
      <!-- 基本信息 -->
      <div class="profile-card">
        <h3 class="card-title">基本信息</h3>
        <el-form
          ref="infoFormRef"
          :model="infoForm"
          :rules="infoRules"
          label-width="100px"
        >
          <el-form-item label="头像">
            <el-upload
              class="avatar-uploader"
              :action="uploadUrl"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              accept="image/*"
            >
              <el-avatar :size="80" :src="infoForm.avatar">
                <svg-icon name="user" :size="40" />
              </el-avatar>
              <div class="upload-tip">点击更换头像</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="infoForm.username" disabled>
              <template #prefix><svg-icon name="user" :size="16" /></template>
            </el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="infoForm.nickname" placeholder="请输入昵称">
              <template #prefix><svg-icon name="edit" :size="16" /></template>
            </el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="infoForm.phone" placeholder="请输入手机号">
              <template #prefix><svg-icon name="phone" :size="16" /></template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="infoLoading" @click="handleUpdateInfo">
              <svg-icon name="check" :size="14" />
              <span>保存修改</span>
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <!-- 修改密码 -->
      <div class="profile-card">
        <h3 class="card-title">修改密码</h3>
        <el-form
          ref="passwordFormRef"
          :model="passwordForm"
          :rules="passwordRules"
          label-width="100px"
        >
          <el-form-item label="原密码" prop="oldPassword">
            <el-input v-model="passwordForm.oldPassword" type="password" show-password placeholder="请输入原密码">
              <template #prefix><svg-icon name="lock" :size="16" /></template>
            </el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="passwordForm.newPassword" type="password" show-password placeholder="请输入新密码">
              <template #prefix><svg-icon name="lock" :size="16" /></template>
            </el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="passwordForm.confirmPassword" type="password" show-password placeholder="请再次输入新密码">
              <template #prefix><svg-icon name="lock" :size="16" /></template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="passwordLoading" @click="handleUpdatePassword">
              <svg-icon name="check" :size="14" />
              <span>修改密码</span>
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useAdminStore } from '@/stores/admin'
import { updateAdminInfo, updateAdminPassword } from '@/api/admin/admin'
import { getToken } from '@/utils/storage'

const adminStore = useAdminStore()

const infoFormRef = ref(null)
const passwordFormRef = ref(null)
const infoLoading = ref(false)
const passwordLoading = ref(false)

const infoForm = reactive({
  username: '',
  nickname: '',
  avatar: '',
  phone: ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const infoRules = {
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const uploadUrl = '/api/file/upload'
const uploadHeaders = computed(() => ({
  Authorization: `Bearer ${getToken()}`
}))

// 初始化表单数据
function initFormData() {
  const adminInfo = adminStore.adminInfo
  if (adminInfo) {
    infoForm.username = adminInfo.username
    infoForm.nickname = adminInfo.nickname
    infoForm.avatar = adminInfo.avatar
    infoForm.phone = adminInfo.phone
  }
}

// 头像上传成功
function handleAvatarSuccess(res) {
  if (res.code === 200) {
    infoForm.avatar = res.data
    ElMessage.success('上传成功')
  } else {
    ElMessage.error(res.message || '上传失败')
  }
}

// 更新信息
async function handleUpdateInfo() {
  const valid = await infoFormRef.value.validate().catch(() => false)
  if (!valid) return
  
  infoLoading.value = true
  try {
    await updateAdminInfo({
      nickname: infoForm.nickname,
      avatar: infoForm.avatar,
      phone: infoForm.phone
    })
    await adminStore.fetchAdminInfo()
    ElMessage.success('修改成功')
  } finally {
    infoLoading.value = false
  }
}

// 修改密码
async function handleUpdatePassword() {
  const valid = await passwordFormRef.value.validate().catch(() => false)
  if (!valid) return
  
  passwordLoading.value = true
  try {
    await updateAdminPassword({
      oldPassword: passwordForm.oldPassword,
      newPassword: passwordForm.newPassword
    })
    ElMessage.success('密码修改成功')
    passwordForm.oldPassword = ''
    passwordForm.newPassword = ''
    passwordForm.confirmPassword = ''
  } finally {
    passwordLoading.value = false
  }
}

onMounted(() => {
  initFormData()
})
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.profile-page {
  .profile-container {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: $spacing-lg;
  }
  
  .profile-card {
    background: $bg-white;
    border-radius: $border-radius-base;
    padding: $spacing-lg;
    box-shadow: $shadow-light;
    
    .card-title {
      font-size: $font-size-large;
      font-weight: 600;
      color: $text-primary;
      margin-bottom: $spacing-lg;
      padding-bottom: $spacing-md;
      border-bottom: 1px solid $border-lighter;
    }
    
    .el-form {
      max-width: 400px;
    }
  }
  
  .avatar-uploader {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: $spacing-sm;
    
    :deep(.el-upload) {
      cursor: pointer;
    }
    
    .upload-tip {
      font-size: $font-size-small;
      color: $text-secondary;
    }
  }
}
</style>
