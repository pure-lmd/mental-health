<template>
  <div class="comment-manage">
    <div class="page-title"><svg-icon name="comment" :size="24" /><span>评论管理</span></div>
    <div class="search-form">
      <el-button type="primary" @click="fetchList">
        <svg-icon name="refresh" :size="14" />
        <span>刷新</span>
      </el-button>
    </div>
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="用户" prop="userNickname" min-width="120" />
        <el-table-column label="评论类型" width="120">
          <template #default="{ row }">{{ getCollectTypeText(row.type) }}</template>
        </el-table-column>
        <el-table-column label="评论内容" prop="content" min-width="250" show-overflow-tooltip />
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)" size="small">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="审核时间" prop="auditTime" width="180">
          <template #default="{ row }">{{ row.auditTime ? formatDateTime(row.auditTime) : '-' }}</template>
        </el-table-column>
        <el-table-column label="评论时间" prop="createTime" width="180">
          <template #default="{ row }">{{ formatDateTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button 
              v-if="row.status === 0" 
              type="success" 
              size="small" 
              link 
              @click="handleAudit(row, 1)"
            >
              <svg-icon name="check" :size="14" />
              <span>通过</span>
            </el-button>
            <el-button 
              v-if="row.status === 0" 
              type="danger" 
              size="small" 
              link 
              @click="handleAudit(row, 2)"
            >
              <svg-icon name="close" :size="14" />
              <span>拒绝</span>
            </el-button>
            <el-button type="danger" link @click="handleDelete(row)">
              <svg-icon name="delete" :size="14" />
              <span>删除</span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
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

    <!-- 审核对话框 -->
    <el-dialog v-model="auditDialogVisible" title="审核评论" width="400px">
      <el-form :model="auditForm" label-width="80px">
        <el-form-item label="审核结果">
          <el-radio-group v-model="auditForm.status">
            <el-radio :label="1">通过</el-radio>
            <el-radio :label="2">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核备注">
          <el-input v-model="auditForm.auditRemark" type="textarea" :rows="3" placeholder="请输入审核备注（可选）" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="auditDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmAudit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getCommentList, deleteComment, auditComment } from '@/api/admin/comment'
import { formatDateTime, getCollectTypeText } from '@/utils/common'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })
const auditDialogVisible = ref(false)
const auditForm = reactive({
  id: null,
  status: 1,
  auditRemark: ''
})

async function fetchList() {
  loading.value = true
  try {
    const res = await getCommentList(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

function getStatusType(status) {
  const types = { 0: 'warning', 1: 'success', 2: 'danger' }
  return types[status] || 'info'
}

function getStatusText(status) {
  const texts = { 0: '待审核', 1: '已通过', 2: '已拒绝' }
  return texts[status] || '未知'
}

function handleAudit(row, status) {
  auditForm.id = row.id
  auditForm.status = status
  auditForm.auditRemark = ''
  auditDialogVisible.value = true
}

async function confirmAudit() {
  try {
    await auditComment(auditForm.id, auditForm.status, auditForm.auditRemark)
    ElMessage.success('审核成功')
    auditDialogVisible.value = false
    fetchList()
  } catch (e) {
    console.error(e)
  }
}

async function handleDelete(row) {
  ElMessageBox.confirm('确定要删除该评论吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteComment(row.id)
      ElMessage.success('删除成功')
      fetchList()
    })
    .catch(() => {})
}

fetchList()
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.comment-manage {
  .search-form {
    margin-bottom: $spacing-md;
  }
}
</style>
