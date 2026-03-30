<template>
  <div class="user-manage">
    <div class="page-title">
      <svg-icon name="user" :size="24" />
      <span>用户管理</span>
    </div>
    
    <!-- 搜索和操作 -->
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="用户名">
          <el-input v-model="queryParams.username" placeholder="请输入用户名" clearable @keyup.enter="fetchList" />
        </el-form-item>
        <el-form-item label="健康状况">
          <el-select v-model="queryParams.healthStatus" placeholder="请选择" clearable style="width: 150px">
            <el-option label="未评估" :value="0" />
            <el-option label="健康" :value="1" />
            <el-option label="一般" :value="2" />
            <el-option label="需关注" :value="3" />
            <el-option label="需干预" :value="4" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchList">
            <svg-icon name="search" :size="14" />
            <span>搜索</span>
          </el-button>
          <el-button @click="resetQuery">
            <svg-icon name="refresh" :size="14" />
            <span>重置</span>
          </el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="handleAdd">
        <svg-icon name="add" :size="14" />
        <span>添加用户</span>
      </el-button>
    </div>
    
    <!-- 数据表格 -->
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="头像" width="80">
          <template #default="{ row }">
            <el-avatar :size="40" :src="row.avatar">
              <svg-icon name="user" :size="20" />
            </el-avatar>
          </template>
        </el-table-column>
        <el-table-column label="用户名" prop="username" min-width="100" />
        <el-table-column label="昵称" prop="nickname" min-width="80" />
        <el-table-column label="性别" prop="gender" width="80">
          <template #default="{ row }">
            {{ row.gender === 1 ? '男' : row.gender === 0 ? '女' : '-' }}
          </template>
        </el-table-column>
        <el-table-column label="年龄" prop="age" width="80" />
        <el-table-column label="健康状况" width="120">
          <template #default="{ row }">
            <el-tag :type="getHealthStatusType(row.healthStatus)">
              {{ getHealthStatusText(row.healthStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300">
          <template #default="{ row }">
            <div class="action-buttons">
              <el-button type="info" link @click="handleView(row)">
                <svg-icon name="view" :size="14" />
                <span>查看</span>
              </el-button>
              <el-button type="primary" link @click="handleEdit(row)">
                <svg-icon name="edit" :size="14" />
                <span>编辑</span>
              </el-button>
              <el-button type="warning" link @click="handleResetPassword(row)">
                <svg-icon name="lock" :size="14" />
                <span>重置密码</span>
              </el-button>
              <el-button type="danger" link @click="handleDelete(row)">
                <svg-icon name="delete" :size="14" />
                <span>删除</span>
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <div class="pagination-center">
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="fetchList"
          @current-change="fetchList"
        />
      </div>
    </div>
    
    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      destroy-on-close
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名" :disabled="!!formData.id" :prefix-icon="User" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="formData.nickname" placeholder="请输入昵称" :prefix-icon="EditPen" />
        </el-form-item>
        <el-form-item v-if="!formData.id" label="密码" prop="password">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码" show-password :prefix-icon="Lock" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="formData.gender">
            <el-radio :value="1">男</el-radio>
            <el-radio :value="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="formData.age" :min="1" :max="120" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入手机号" :prefix-icon="Phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" :prefix-icon="Message" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :value="1">启用</el-radio>
            <el-radio :value="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">
          确定
        </el-button>
      </template>
    </el-dialog>
    
    <!-- 用户详情对话框 -->
    <el-dialog v-model="viewDialogVisible" title="用户详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="用户名">{{ currentUser.username }}</el-descriptions-item>
        <el-descriptions-item label="昵称">{{ currentUser.nickname }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ currentUser.gender === 1 ? '男' : currentUser.gender === 0 ? '女' : '-' }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ currentUser.age || '-' }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ currentUser.phone || '-' }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ currentUser.email || '-' }}</el-descriptions-item>
        <el-descriptions-item label="健康状况">
          <el-tag :type="getHealthStatusType(currentUser.healthStatus)">{{ getHealthStatusText(currentUser.healthStatus) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="状态">{{ currentUser.status === 1 ? '启用' : '禁用' }}</el-descriptions-item>
        <el-descriptions-item label="注册时间" :span="2">{{ formatDateTime(currentUser.createTime) }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { 
  getUserList, 
  addUser, 
  updateUser, 
  deleteUser,
  updateUserStatus,
  resetUserPassword
} from '@/api/admin/user'
import { formatDateTime, getHealthStatusText, getHealthStatusType } from '@/utils/common'
import { User, EditPen, Lock, Phone, Message } from '@element-plus/icons-vue'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const viewDialogVisible = ref(false)
const submitLoading = ref(false)
const formRef = ref(null)
const currentUser = ref({})

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  username: '',
  healthStatus: null
})

const formData = reactive({
  id: null,
  username: '',
  nickname: '',
  password: '',
  gender: 1,
  age: null,
  phone: '',
  email: '',
  status: 1
})

const formRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }]
}

const dialogTitle = computed(() => formData.id ? '编辑用户' : '添加用户')

// 获取列表
async function fetchList() {
  loading.value = true
  try {
    const res = await getUserList(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 重置查询
function resetQuery() {
  queryParams.username = ''
  queryParams.healthStatus = null
  queryParams.pageNum = 1
  fetchList()
}

// 查看详情
function handleView(row) {
  currentUser.value = row
  viewDialogVisible.value = true
}

// 添加
function handleAdd() {
  Object.assign(formData, {
    id: null,
    username: '',
    nickname: '',
    password: '',
    gender: 1,
    age: null,
    phone: '',
    email: '',
    status: 1
  })
  dialogVisible.value = true
}

// 编辑
function handleEdit(row) {
  Object.assign(formData, { ...row, password: '' })
  dialogVisible.value = true
}

// 删除
function handleDelete(row) {
  ElMessageBox.confirm('确定要删除该用户吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    fetchList()
  }).catch(() => {})
}

// 重置密码
function handleResetPassword(row) {
  ElMessageBox.confirm('确定要重置该用户的密码为123456吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await resetUserPassword(row.id)
    ElMessage.success('密码已重置为123456')
  }).catch(() => {})
}

// 状态改变
async function handleStatusChange(row) {
  try {
    await updateUserStatus(row.id, row.status)
    ElMessage.success('状态更新成功')
  } catch (error) {
    row.status = row.status === 1 ? 0 : 1
  }
}

// 提交表单
async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  
  submitLoading.value = true
  try {
    if (formData.id) {
      await updateUser(formData)
      ElMessage.success('更新成功')
    } else {
      await addUser(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    fetchList()
  } finally {
    submitLoading.value = false
  }
}

// 初始化
fetchList()
</script>

<style lang="scss" scoped>
.user-manage {
  .search-form {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    flex-wrap: wrap;
    gap: $spacing-md;
  }
  
  .action-buttons {
    display: flex;
    gap: $spacing-sm;
    flex-wrap: nowrap;
  }
}
</style>
