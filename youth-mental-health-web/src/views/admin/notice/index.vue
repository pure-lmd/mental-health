<template>
  <div class="notice-manage">
    <div class="page-title">
      <svg-icon name="notice" :size="24" />
      <span>通知公告管理</span>
    </div>
    
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="标题">
          <el-input v-model="queryParams.title" placeholder="请输入标题" clearable @keyup.enter="fetchList" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchList"><svg-icon name="search" :size="14" /><span>搜索</span></el-button>
          <el-button @click="resetQuery"><svg-icon name="refresh" :size="14" /><span>重置</span></el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="handleAdd"><svg-icon name="add" :size="14" /><span>添加公告</span></el-button>
    </div>
    
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="标题" prop="title" min-width="200" show-overflow-tooltip />
        <el-table-column label="置顶" width="80">
          <template #default="{ row }">
            <el-switch v-model="row.isTop" :active-value="1" :inactive-value="0" @change="handleTopChange(row)" />
          </template>
        </el-table-column>
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="handleStatusChange(row)" />
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180">
          <template #default="{ row }">{{ formatDateTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)"><svg-icon name="edit" :size="14" /><span>编辑</span></el-button>
            <el-button type="danger" link @click="handleDelete(row)"><svg-icon name="delete" :size="14" /><span>删除</span></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-center">
        <el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" @size-change="fetchList" @current-change="fetchList" />
      </div>
    </div>
    
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px" destroy-on-close>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <RichTextEditor v-model="formData.content" :min-height="250" placeholder="请输入内容" />
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
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getNoticeList, addNotice, updateNotice, deleteNotice, updateNoticeStatus, setNoticeTop } from '@/api/admin/notice'
import { formatDateTime } from '@/utils/common'
import RichTextEditor from '@/components/RichTextEditor/index.vue'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const submitLoading = ref(false)
const formRef = ref(null)

const queryParams = reactive({ pageNum: 1, pageSize: 10, title: '' })
const formData = reactive({ id: null, title: '', content: '', status: 1 })
const formRules = { title: [{ required: true, message: '请输入标题', trigger: 'blur' }], content: [{ required: true, message: '请输入内容', trigger: 'blur' }] }
const dialogTitle = computed(() => formData.id ? '编辑公告' : '添加公告')

async function fetchList() {
  loading.value = true
  try {
    const res = await getNoticeList(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (error) { console.error(error) } finally { loading.value = false }
}

function resetQuery() { queryParams.title = ''; queryParams.pageNum = 1; fetchList() }
function handleAdd() { Object.assign(formData, { id: null, title: '', content: '', status: 1 }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(formData, { ...row }); dialogVisible.value = true }

function handleDelete(row) {
  ElMessageBox.confirm('确定要删除该公告吗？', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }).then(async () => {
    await deleteNotice(row.id); ElMessage.success('删除成功'); fetchList()
  }).catch(() => {})
}

async function handleStatusChange(row) { try { await updateNoticeStatus(row.id, row.status); ElMessage.success('状态更新成功') } catch (error) { row.status = row.status === 1 ? 0 : 1 } }
async function handleTopChange(row) { try { await setNoticeTop(row.id, row.isTop); ElMessage.success('设置成功') } catch (error) { row.isTop = row.isTop === 1 ? 0 : 1 } }

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitLoading.value = true
  try {
    if (formData.id) { await updateNotice(formData); ElMessage.success('更新成功') }
    else { await addNotice(formData); ElMessage.success('添加成功') }
    dialogVisible.value = false; fetchList()
  } finally { submitLoading.value = false }
}

fetchList()
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.notice-manage { .search-form { display: flex; justify-content: space-between; align-items: flex-start; flex-wrap: wrap; gap: $spacing-md; } }
</style>
