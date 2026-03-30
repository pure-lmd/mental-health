<template>
  <div class="activity-manage">
    <div class="page-title"><svg-icon name="activity" :size="24" /><span>活动管理</span></div>
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="标题"><el-input v-model="queryParams.title" placeholder="请输入标题" clearable /></el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 150px">
            <el-option label="下架" :value="0" />
            <el-option label="报名中" :value="1" />
            <el-option label="进行中" :value="2" />
            <el-option label="已结束" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchList">
            <svg-icon name="search" :size="14" />
          </el-button>
          <el-button @click="resetQuery">
            <svg-icon name="refresh" :size="14" />
          </el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="handleAdd"><svg-icon name="add" :size="14" /><span>添加活动</span></el-button>
    </div>
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="封面" width="120">
          <template #default="{ row }"><el-image v-if="row.cover" :src="row.cover" style="width: 80px; height: 60px; border-radius: 4px;" fit="cover" /><span v-else>-</span></template>
        </el-table-column>
        <el-table-column label="标题" prop="title" min-width="180" show-overflow-tooltip />
        <el-table-column label="地址" prop="address" min-width="150" show-overflow-tooltip />
        <el-table-column label="报名人数" width="120">
          <template #default="{ row }">{{ row.currentNum || 0 }} / {{ row.maxNum || '-' }}</template>
        </el-table-column>
        <el-table-column label="状态" width="120">
          <template #default="{ row }"><el-tag :type="getActivityStatusType(row.status)">{{ getActivityStatusText(row.status) }}</el-tag></template>
        </el-table-column>
        <el-table-column label="热门" width="80"><template #default="{ row }"><el-switch v-model="row.isHot" :active-value="1" :inactive-value="0" @change="handleHotChange(row)" /></template></el-table-column>
        <el-table-column label="时间" width="180">
          <template #default="{ row }">{{ formatDateTime(row.startTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <div class="action-buttons">
              <el-button type="primary" link @click="handleEdit(row)"><svg-icon name="edit" :size="14" /><span>编辑</span></el-button>
              <el-button type="warning" link @click="handleChangeStatus(row)"><svg-icon name="setting" :size="14" /><span>状态</span></el-button>
              <el-button type="danger" link @click="handleDelete(row)"><svg-icon name="delete" :size="14" /><span>删除</span></el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-center"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" @size-change="fetchList" @current-change="fetchList" /></div>
    </div>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px" top="5vh" destroy-on-close>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="标题" prop="title"><el-input v-model="formData.title" placeholder="请输入标题" /></el-form-item>
        <el-form-item label="封面"><el-upload class="cover-uploader" :action="uploadUrl" :headers="uploadHeaders" :show-file-list="false" :on-success="handleUploadSuccess" accept="image/*"><el-image v-if="formData.cover" :src="formData.cover" class="cover-image" fit="cover" /><div v-else class="upload-placeholder"><svg-icon name="upload" :size="32" /><span>上传封面</span></div></el-upload></el-form-item>
        <el-form-item label="描述"><el-input v-model="formData.description" type="textarea" :rows="3" placeholder="请输入简短描述" /></el-form-item>
        <el-form-item label="活动地址" prop="address"><el-input v-model="formData.address" placeholder="请输入活动地址" /></el-form-item>
        <el-row :gutter="20">
          <el-col :span="12"><el-form-item label="开始时间" prop="startTime"><el-date-picker v-model="formData.startTime" type="datetime" placeholder="选择开始时间" style="width: 100%;" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="结束时间" prop="endTime"><el-date-picker v-model="formData.endTime" type="datetime" placeholder="选择结束时间" style="width: 100%;" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="最大人数" prop="maxNum"><el-input-number v-model="formData.maxNum" :min="1" :max="9999" /></el-form-item>
        <el-form-item label="详细内容" prop="content"><RichTextEditor v-model="formData.content" :min-height="250" placeholder="请输入详细内容" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getActivityList, addActivity, updateActivity, deleteActivity, updateActivityStatus, setActivityHot } from '@/api/admin/activity'
import { formatDateTime, getActivityStatusText, getActivityStatusType } from '@/utils/common'
import { getToken } from '@/utils/storage'
import RichTextEditor from '@/components/RichTextEditor/index.vue'

const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), submitLoading = ref(false), formRef = ref(null)
const queryParams = reactive({ pageNum: 1, pageSize: 10, title: '', status: null })
const formData = reactive({ id: null, title: '', cover: '', description: '', content: '', address: '', startTime: null, endTime: null, maxNum: 100 })
const formRules = { title: [{ required: true, message: '请输入标题', trigger: 'blur' }], address: [{ required: true, message: '请输入地址', trigger: 'blur' }] }
const dialogTitle = computed(() => formData.id ? '编辑活动' : '添加活动')
const uploadUrl = '/api/file/upload', uploadHeaders = computed(() => ({ Authorization: `Bearer ${getToken()}` }))

async function fetchList() { loading.value = true; try { const res = await getActivityList(queryParams); tableData.value = res.data.records; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
function resetQuery() { queryParams.title = ''; queryParams.status = null; queryParams.pageNum = 1; fetchList() }
function handleAdd() { Object.assign(formData, { id: null, title: '', cover: '', description: '', content: '', address: '', startTime: null, endTime: null, maxNum: 100 }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(formData, { ...row }); dialogVisible.value = true }
function handleDelete(row) { ElMessageBox.confirm('确定要删除该活动吗？', '提示', { type: 'warning' }).then(async () => { await deleteActivity(row.id); ElMessage.success('删除成功'); fetchList() }).catch(() => {}) }
function handleChangeStatus(row) { ElMessageBox.prompt('请输入状态: 0下架/1报名中/2进行中/3已结束', '修改状态', { inputValue: String(row.status) }).then(async ({ value }) => { await updateActivityStatus(row.id, Number(value)); ElMessage.success('状态更新成功'); fetchList() }).catch(() => {}) }
async function handleHotChange(row) { try { await setActivityHot(row.id, row.isHot); ElMessage.success('设置成功') } catch (e) { row.isHot = row.isHot === 1 ? 0 : 1 } }
function handleUploadSuccess(res) { if (res.code === 200) { formData.cover = res.data; ElMessage.success('上传成功') } else { ElMessage.error(res.message || '上传失败') } }
async function handleSubmit() { const valid = await formRef.value.validate().catch(() => false); if (!valid) return; submitLoading.value = true; try { if (formData.id) { await updateActivity(formData); ElMessage.success('更新成功') } else { await addActivity(formData); ElMessage.success('添加成功') }; dialogVisible.value = false; fetchList() } finally { submitLoading.value = false } }

fetchList()
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.activity-manage { .search-form { display: flex; justify-content: space-between; align-items: flex-start; flex-wrap: wrap; gap: $spacing-md; }
  .cover-uploader { :deep(.el-upload) { border: 1px dashed $border-color; border-radius: $border-radius-base; cursor: pointer; &:hover { border-color: $primary-color; } } .cover-image { width: 200px; height: 120px; } .upload-placeholder { width: 200px; height: 120px; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: $spacing-sm; color: $text-secondary; background: $bg-color; } } }
</style>
