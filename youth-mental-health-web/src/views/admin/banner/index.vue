<template>
  <div class="banner-manage">
    <div class="page-title">
      <svg-icon name="banner" :size="24" />
      <span>轮播图管理</span>
    </div>
    
    <!-- 搜索和操作 -->
    <div class="search-form">
      <el-button type="primary" @click="handleAdd">
        <svg-icon name="add" :size="14" />
        <span>添加轮播图</span>
      </el-button>
      <el-button @click="fetchList">
        <svg-icon name="refresh" :size="14" />
        <span>刷新</span>
      </el-button>
    </div>
    
    <!-- 数据表格 -->
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="标题" prop="title" min-width="150" />
        <el-table-column label="图片" width="200">
          <template #default="{ row }">
            <el-image 
              v-if="row.image"
              :src="row.image" 
              style="width: 150px; height: 60px; border-radius: 4px; cursor: pointer;"
              fit="cover"
              @click="handlePreview(row.image)"
            />
            <span v-else>暂无图片</span>
          </template>
        </el-table-column>
        <el-table-column label="跳转链接" prop="url" min-width="200" show-overflow-tooltip />
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
      width="600px"
      destroy-on-close
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入轮播图标题" />
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <el-upload
            class="banner-uploader"
            :action="uploadUrl"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
            accept="image/*"
          >
            <el-image 
              v-if="formData.image" 
              :src="formData.image" 
              class="banner-image"
              fit="cover"
            />
            <div v-else class="upload-placeholder">
              <svg-icon name="upload" :size="32" />
              <span>点击上传图片</span>
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="跳转链接" prop="url">
          <el-input v-model="formData.url" placeholder="请输入跳转链接（可选）" />
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
    
    <!-- 图片预览对话框 -->
    <el-dialog v-model="previewVisible" title="图片预览" width="600px">
      <div style="text-align: center;">
        <img :src="previewImage" style="max-width: 100%; max-height: 400px; border-radius: 8px;" />
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { 
  getBannerList, 
  addBanner, 
  updateBanner, 
  deleteBanner,
  updateBannerStatus 
} from '@/api/admin/banner'
import { formatDateTime } from '@/utils/common'
import { getToken } from '@/utils/storage'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const previewVisible = ref(false)
const previewImage = ref('')
const submitLoading = ref(false)
const formRef = ref(null)

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10
})

const formData = reactive({
  id: null,
  title: '',
  image: '',
  url: '',
  sort: 0,
  status: 1
})

const formRules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  image: [{ required: true, message: '请上传图片', trigger: 'change' }]
}

const dialogTitle = computed(() => formData.id ? '编辑轮播图' : '添加轮播图')
const uploadUrl = '/api/file/upload'
const uploadHeaders = computed(() => ({
  Authorization: `Bearer ${getToken()}`
}))

// 图片预览
function handlePreview(url) {
  previewImage.value = url
  previewVisible.value = true
}

// 获取列表
async function fetchList() {
  loading.value = true
  try {
    const res = await getBannerList(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 添加
function handleAdd() {
  Object.assign(formData, {
    id: null,
    title: '',
    image: '',
    url: '',
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
  ElMessageBox.confirm('确定要删除该轮播图吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteBanner(row.id)
    ElMessage.success('删除成功')
    fetchList()
  }).catch(() => {})
}

// 状态改变
async function handleStatusChange(row) {
  try {
    await updateBannerStatus(row.id, row.status)
    ElMessage.success('状态更新成功')
  } catch (error) {
    row.status = row.status === 1 ? 0 : 1
  }
}

// 图片上传成功
function handleUploadSuccess(res) {
  if (res.code === 200) {
    formData.image = res.data
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
      await updateBanner(formData)
      ElMessage.success('更新成功')
    } else {
      await addBanner(formData)
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

.banner-manage {
  .banner-uploader {
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
    
    .banner-image {
      width: 300px;
      height: 120px;
    }
    
    .upload-placeholder {
      width: 300px;
      height: 120px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      gap: $spacing-sm;
      color: $text-secondary;
      background: $bg-color;
    }
  }
}
</style>
