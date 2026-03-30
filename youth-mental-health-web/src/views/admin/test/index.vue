<template>
  <div class="test-manage">
    <div class="page-title"><svg-icon name="test" :size="24" /><span>心理测试管理</span></div>
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="标题"><el-input v-model="queryParams.title" placeholder="请输入标题" clearable /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchList"><svg-icon name="search" :size="14" /><span>搜索</span></el-button>
          <el-button @click="resetQuery"><svg-icon name="refresh" :size="14" /><span>重置</span></el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="handleAdd"><svg-icon name="add" :size="14" /><span>添加测试卷</span></el-button>
    </div>
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="封面" width="120"><template #default="{ row }"><el-image v-if="row.cover" :src="row.cover" style="width: 80px; height: 60px; border-radius: 4px;" fit="cover" /><span v-else>-</span></template></el-table-column>
        <el-table-column label="标题" prop="title" min-width="180" show-overflow-tooltip />
        <el-table-column label="描述" prop="description" min-width="200" show-overflow-tooltip />
        <el-table-column label="总分" prop="totalScore" width="80" />
        <el-table-column label="健康阈值" prop="healthThreshold" width="100" />
        <el-table-column label="良好阈值" prop="goodThreshold" width="100" />
        <el-table-column label="状态" width="80"><template #default="{ row }"><el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="handleStatusChange(row)" /></template></el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180"><template #default="{ row }">{{ formatDateTime(row.createTime) }}</template></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="success" link @click="goToQuestions(row)"><svg-icon name="view" :size="14" /><span>题目</span></el-button>
            <el-button type="primary" link @click="handleEdit(row)"><svg-icon name="edit" :size="14" /><span>编辑</span></el-button>
            <el-button type="danger" link @click="handleDelete(row)"><svg-icon name="delete" :size="14" /><span>删除</span></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-center"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" @size-change="fetchList" @current-change="fetchList" /></div>
    </div>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px" destroy-on-close>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="标题" prop="title"><el-input v-model="formData.title" placeholder="请输入标题" /></el-form-item>
        <el-form-item label="封面"><el-upload class="cover-uploader" :action="uploadUrl" :headers="uploadHeaders" :show-file-list="false" :on-success="handleUploadSuccess" accept="image/*"><el-image v-if="formData.cover" :src="formData.cover" class="cover-image" fit="cover" /><div v-else class="upload-placeholder"><svg-icon name="upload" :size="32" /><span>上传封面</span></div></el-upload></el-form-item>
        <el-form-item label="描述"><el-input v-model="formData.description" type="textarea" :rows="3" placeholder="请输入描述" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="8"><el-form-item label="总分"><el-input-number v-model="formData.totalScore" :min="0" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="健康阈值"><el-input-number v-model="formData.healthThreshold" :min="0" /></el-form-item></el-col>
          <el-col :span="8"><el-form-item label="良好阈值"><el-input-number v-model="formData.goodThreshold" :min="0" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="排序"><el-input-number v-model="formData.sort" :min="0" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="formData.status"><el-radio :value="1">启用</el-radio><el-radio :value="0">禁用</el-radio></el-radio-group></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getTestList, addTest, updateTest, deleteTest, updateTestStatus } from '@/api/admin/test'
import { formatDateTime } from '@/utils/common'
import { getToken } from '@/utils/storage'

const router = useRouter()
const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), submitLoading = ref(false), formRef = ref(null)
const queryParams = reactive({ pageNum: 1, pageSize: 10, title: '' })
const formData = reactive({ id: null, title: '', cover: '', description: '', totalScore: 100, healthThreshold: 60, goodThreshold: 80, sort: 0, status: 1 })
const formRules = { title: [{ required: true, message: '请输入标题', trigger: 'blur' }] }
const dialogTitle = computed(() => formData.id ? '编辑测试卷' : '添加测试卷')
const uploadUrl = '/api/file/upload', uploadHeaders = computed(() => ({ Authorization: `Bearer ${getToken()}` }))

async function fetchList() { loading.value = true; try { const res = await getTestList(queryParams); tableData.value = res.data.records; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
function resetQuery() { queryParams.title = ''; queryParams.pageNum = 1; fetchList() }
function handleAdd() { Object.assign(formData, { id: null, title: '', cover: '', description: '', totalScore: 100, healthThreshold: 60, goodThreshold: 80, sort: 0, status: 1 }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(formData, { ...row }); dialogVisible.value = true }
function handleDelete(row) { ElMessageBox.confirm('确定要删除该测试卷吗？', '提示', { type: 'warning' }).then(async () => { await deleteTest(row.id); ElMessage.success('删除成功'); fetchList() }).catch(() => {}) }
async function handleStatusChange(row) { try { await updateTestStatus(row.id, row.status); ElMessage.success('状态更新成功') } catch (e) { row.status = row.status === 1 ? 0 : 1 } }
function handleUploadSuccess(res) { if (res.code === 200) { formData.cover = res.data; ElMessage.success('上传成功') } else { ElMessage.error(res.message || '上传失败') } }
function goToQuestions(row) { router.push(`/admin/test/question/${row.id}`) }
async function handleSubmit() { const valid = await formRef.value.validate().catch(() => false); if (!valid) return; submitLoading.value = true; try { if (formData.id) { await updateTest(formData); ElMessage.success('更新成功') } else { await addTest(formData); ElMessage.success('添加成功') }; dialogVisible.value = false; fetchList() } finally { submitLoading.value = false } }

fetchList()
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.test-manage { .search-form { display: flex; justify-content: space-between; align-items: flex-start; flex-wrap: wrap; gap: $spacing-md; }
  .cover-uploader { :deep(.el-upload) { border: 1px dashed $border-color; border-radius: $border-radius-base; cursor: pointer; &:hover { border-color: $primary-color; } } .cover-image { width: 200px; height: 120px; } .upload-placeholder { width: 200px; height: 120px; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: $spacing-sm; color: $text-secondary; background: $bg-color; } } }
</style>
