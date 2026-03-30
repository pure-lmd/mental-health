<template>
  <div class="category-manage">
    <div class="page-title">
      <svg-icon name="category" :size="24" />
      <span>心理分类管理</span>
    </div>
    
    <!-- 搜索和操作 -->
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="分类名称">
          <el-input v-model="queryParams.name" placeholder="请输入分类名称" clearable @keyup.enter="fetchList" />
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
        <span>添加分类</span>
      </el-button>
    </div>
    
    <!-- 数据表格 -->
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="分类名称" prop="name" min-width="150" />
        <el-table-column label="图标" width="80">
          <template #default="{ row }">
            <el-image 
              v-if="row.icon"
              :src="row.icon" 
              style="width: 40px; height: 40px;"
              fit="cover"
            />
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="排序" prop="sort" width="80" />
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
        <el-table-column label="创建时间" prop="createTime" width="180">
          <template #default="{ row }">
            {{ formatDateTime(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">
              <svg-icon name="edit" :size="14" />
              <span>编辑</span>
            </el-button>
            <el-button type="danger" link @click="handleDelete(row)">
              <svg-icon name="delete" :size="14" />
              <span>删除</span>
            </el-button>
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
      width="500px"
      destroy-on-close
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-upload
            class="icon-uploader"
            :action="uploadUrl"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
            accept="image/*"
          >
            <el-image 
              v-if="formData.icon" 
              :src="formData.icon" 
              class="icon-image"
              fit="cover"
            />
            <div v-else class="upload-placeholder">
              <svg-icon name="upload" :size="24" />
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="formData.sort" :min="0" :max="999" />
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
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { 
  getCategoryList, 
  addCategory, 
  updateCategory, 
  deleteCategory,
  updateCategoryStatus 
} from '@/api/admin/category'
import { formatDateTime } from '@/utils/common'
import { getToken } from '@/utils/storage'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const submitLoading = ref(false)
const formRef = ref(null)

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  name: ''
})

const formData = reactive({
  id: null,
  name: '',
  icon: '',
  sort: 0,
  status: 1
})

const formRules = {
  name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
}

const dialogTitle = computed(() => formData.id ? '编辑分类' : '添加分类')
const uploadUrl = '/api/file/upload'
const uploadHeaders = computed(() => ({
  Authorization: `Bearer ${getToken()}`
}))

// 获取列表
async function fetchList() {
  loading.value = true
  try {
    const res = await getCategoryList(queryParams)
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
  queryParams.name = ''
  queryParams.pageNum = 1
  fetchList()
}

// 添加
function handleAdd() {
  Object.assign(formData, {
    id: null,
    name: '',
    icon: '',
    sort: 0,
    status: 1
  })
  dialogVisible.value = true
}

// 编辑
function handleEdit(row) {
  Object.assign(formData, { ...row })
  dialogVisible.value = true
}

// 删除
function handleDelete(row) {
  ElMessageBox.confirm('确定要删除该分类吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteCategory(row.id)
    ElMessage.success('删除成功')
    fetchList()
  }).catch(() => {})
}

// 状态改变
async function handleStatusChange(row) {
  try {
    await updateCategoryStatus(row.id, row.status)
    ElMessage.success('状态更新成功')
  } catch (error) {
    row.status = row.status === 1 ? 0 : 1
  }
}

// 图标上传成功
function handleUploadSuccess(res) {
  if (res.code === 200) {
    formData.icon = res.data
    ElMessage.success('上传成功')
  } else {
    ElMessage.error(res.message || '上传失败')
  }
}

// 提交表单
async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  
  submitLoading.value = true
  try {
    if (formData.id) {
      await updateCategory(formData)
      ElMessage.success('更新成功')
    } else {
      await addCategory(formData)
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
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.category-manage {
  .search-form {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    flex-wrap: wrap;
    gap: $spacing-md;
  }
  
  .icon-uploader {
    :deep(.el-upload) {
      border: 1px dashed $border-color;
      border-radius: $border-radius-base;
      cursor: pointer;
      overflow: hidden;
      transition: $transition-fast;
      
      &:hover {
        border-color: $primary-color;
      }
    }
    
    .icon-image {
      width: 80px;
      height: 80px;
    }
    
    .upload-placeholder {
      width: 80px;
      height: 80px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: $text-secondary;
      background: $bg-color;
    }
  }
}
</style>
