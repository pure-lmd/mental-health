<template>
  <div class="profile-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>个人信息</el-breadcrumb-item></el-breadcrumb></div>

    <!-- 新增: 积分和快捷入口卡片 -->
    <div class="quick-access">
      <div class="points-card">
        <div class="points-icon">🏆</div>
        <div class="points-info">
          <div class="points-value">{{ formData.points || 0 }}</div>
          <div class="points-label">当前积分</div>
        </div>
      </div>
      <div class="quick-links">
        <el-button @click="$router.push('/front/user/growth')">
          <svg-icon name="chart" :size="14" /> 成长轨迹
        </el-button>
        <el-button @click="$router.push('/front/user/test-record')">
          <svg-icon name="record" :size="14" /> 测试记录
        </el-button>
        <el-button @click="$router.push('/front/test')">
          <svg-icon name="test" :size="14" /> 开始测试
        </el-button>
      </div>
    </div>

    <div class="profile-wrapper">
      <el-card class="profile-card">
        <template #header><div class="card-header"><svg-icon name="user" :size="18" /><span>个人信息</span></div></template>
        <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px" v-loading="loading">
          <el-form-item label="头像">
            <el-upload class="avatar-uploader" :action="uploadUrl" :headers="uploadHeaders" :show-file-list="false" :on-success="handleAvatarSuccess" accept="image/*">
              <img v-if="formData.avatar" :src="formData.avatar" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item label="用户名"><el-input v-model="formData.username" disabled :prefix-icon="User" /></el-form-item>
          <el-form-item label="昵称" prop="nickname"><el-input v-model="formData.nickname" placeholder="请输入昵称" :prefix-icon="EditPen" /></el-form-item>
          <el-form-item label="性别"><el-radio-group v-model="formData.gender"><el-radio :label="1">男</el-radio><el-radio :label="2">女</el-radio><el-radio :label="0">保密</el-radio></el-radio-group></el-form-item>
          <el-form-item label="年龄" prop="age"><el-input-number v-model="formData.age" :min="1" :max="150" placeholder="请输入年龄" /></el-form-item>
          <el-form-item label="健康状态">
            <el-tag :type="healthStatusType" size="large" effect="plain">{{ healthStatusText }}</el-tag>
          </el-form-item>
          <el-form-item label="邮箱" prop="email"><el-input v-model="formData.email" placeholder="请输入邮箱" :prefix-icon="Message" /></el-form-item>
          <el-form-item label="手机号" prop="phone"><el-input v-model="formData.phone" placeholder="请输入手机号" :prefix-icon="Phone" /></el-form-item>
          <el-form-item><el-button type="primary" :loading="submitLoading" @click="handleSubmit"><svg-icon name="save" :size="14" /> 保存修改</el-button></el-form-item>
        </el-form>
      </el-card>

      <el-card class="password-card">
        <template #header><div class="card-header"><svg-icon name="lock" :size="18" /><span>修改密码</span></div></template>
        <el-form ref="pwdFormRef" :model="pwdData" :rules="pwdRules" label-width="100px">
          <el-form-item label="原密码" prop="oldPassword"><el-input v-model="pwdData.oldPassword" type="password" show-password placeholder="请输入原密码" :prefix-icon="Lock" /></el-form-item>
          <el-form-item label="新密码" prop="newPassword"><el-input v-model="pwdData.newPassword" type="password" show-password placeholder="请输入新密码" :prefix-icon="Lock" /></el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword"><el-input v-model="pwdData.confirmPassword" type="password" show-password placeholder="请再次输入新密码" :prefix-icon="Lock" /></el-form-item>
          <el-form-item><el-button type="primary" :loading="pwdLoading" @click="handleChangePwd"><svg-icon name="lock" :size="14" /> 修改密码</el-button></el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus, Message, Phone, User, EditPen, Lock } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'
import { getUserInfo, updateUserInfo, updateUserPassword } from '@/api/front/user'
import { getToken } from '@/utils/storage'

const userStore = useUserStore()
const formRef = ref(null), pwdFormRef = ref(null)
const loading = ref(false), submitLoading = ref(false), pwdLoading = ref(false)
const formData = reactive({ avatar: '', username: '', nickname: '', gender: 0, age: null, healthStatus: 0, email: '', phone: '' })

const uploadUrl = '/api/file/upload'
const uploadHeaders = computed(() => ({ Authorization: getToken() }))

const healthStatusText = computed(() => {
  const map = { 0: '健康', 1: '良好', 2: '建议沟通' }
  return map[formData.healthStatus] ?? '未知'
})
const healthStatusType = computed(() => {
  const map = { 0: 'success', 1: 'warning', 2: 'danger' }
  return map[formData.healthStatus] ?? 'info'
})
const pwdData = reactive({ oldPassword: '', newPassword: '', confirmPassword: '' })

const formRules = { email: [{ type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }] }
const pwdRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }],
  confirmPassword: [{ required: true, message: '请确认新密码', trigger: 'blur' }, { validator: (r, v, cb) => v !== pwdData.newPassword ? cb(new Error('两次密码不一致')) : cb(), trigger: 'blur' }]
}

async function fetchUserInfo() { loading.value = true; try { const res = await getUserInfo(); Object.assign(formData, res.data) } catch (e) { console.error(e) } finally { loading.value = false } }

function handleAvatarSuccess(res) { if (res.code === 200) formData.avatar = res.data }

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitLoading.value = true
  try { await updateUserInfo(formData); ElMessage.success('保存成功'); userStore.updateUserInfo(formData) } catch (e) { console.error(e) } finally { submitLoading.value = false }
}

async function handleChangePwd() {
  const valid = await pwdFormRef.value.validate().catch(() => false)
  if (!valid) return
  pwdLoading.value = true
  try { await updateUserPassword(pwdData); ElMessage.success('密码修改成功'); pwdData.oldPassword = ''; pwdData.newPassword = ''; pwdData.confirmPassword = '' } catch (e) { console.error(e) } finally { pwdLoading.value = false }
}

onMounted(() => { fetchUserInfo() })
</script>

<style lang="scss" scoped>
.profile-page { 
  // 新增: 快捷入口样式
  .quick-access {
    margin-bottom: $spacing-lg;
    display: flex;
    gap: $spacing-lg;
    align-items: stretch;

    .points-card {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: $border-radius-large;
      padding: $spacing-xl;
      display: flex;
      align-items: center;
      gap: $spacing-lg;
      color: #fff;
      min-width: 250px;
      box-shadow: $shadow-medium;

      .points-icon {
        font-size: 48px;
      }

      .points-info {
        flex: 1;

        .points-value {
          font-size: 36px;
          font-weight: 700;
          margin-bottom: $spacing-xs;
        }

        .points-label {
          font-size: $font-size-base;
          opacity: 0.9;
        }
      }
    }

    .quick-links {
      flex: 1;
      background: $bg-white;
      border-radius: $border-radius-large;
      padding: $spacing-xl;
      display: flex;
      align-items: center;
      gap: $spacing-md;
      box-shadow: $shadow-base;

      .el-button {
        flex: 1;
      }
    }
  }

  .profile-wrapper { display: grid; grid-template-columns: repeat(2, 1fr); gap: $spacing-lg; }
  .card-header { display: flex; align-items: center; gap: $spacing-sm; font-size: $font-size-medium; font-weight: 600; }
  .avatar-uploader { :deep(.el-upload) { border: 1px dashed $border-color; border-radius: 6px; cursor: pointer; overflow: hidden; transition: border-color 0.3s; &:hover { border-color: $primary-color; } } .avatar { width: 100px; height: 100px; display: block; object-fit: cover; } .avatar-uploader-icon { width: 100px; height: 100px; font-size: 28px; color: $text-placeholder; } } }
@media (max-width: 768px) { 
  .profile-page {
    .quick-access {
      flex-direction: column;
    }
    .profile-wrapper { grid-template-columns: 1fr; } 
  }
}
</style>
