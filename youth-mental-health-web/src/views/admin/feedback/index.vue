<template>
  <div class="feedback-manage">
    <div class="page-title"><svg-icon name="feedback" :size="24" /><span>反馈管理</span></div>
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="状态"><el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 150px"><el-option label="待回复" :value="0" /><el-option label="已回复" :value="1" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="fetchList"><svg-icon name="search" :size="14" /><span>搜索</span></el-button><el-button @click="resetQuery"><svg-icon name="refresh" :size="14" /><span>重置</span></el-button></el-form-item>
      </el-form>
    </div>
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="用户" prop="userNickname" min-width="120" />
        <el-table-column label="标题" prop="title" min-width="150" show-overflow-tooltip />
        <el-table-column label="状态" width="100"><template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'warning'">{{ row.status === 1 ? '已回复' : '待回复' }}</el-tag></template></el-table-column>
        <el-table-column label="反馈时间" prop="createTime" width="180"><template #default="{ row }">{{ formatDateTime(row.createTime) }}</template></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="primary" link @click="handleReply(row)"><svg-icon name="comment" :size="14" /><span>回复</span></el-button>
            <el-button type="info" link @click="handleView(row)"><svg-icon name="view" :size="14" /><span>查看</span></el-button>
            <el-button type="danger" link @click="handleDelete(row)"><svg-icon name="delete" :size="14" /><span>删除</span></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-center"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" @size-change="fetchList" @current-change="fetchList" /></div>
    </div>
    <el-dialog v-model="viewDialogVisible" title="反馈详情" width="600px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="标题">{{ currentFeedback.title }}</el-descriptions-item>
        <el-descriptions-item label="内容">{{ currentFeedback.content }}</el-descriptions-item>
        <el-descriptions-item label="回复" v-if="currentFeedback.reply">{{ currentFeedback.reply }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
    <el-dialog v-model="replyDialogVisible" title="回复反馈" width="500px">
      <el-form ref="replyFormRef" :model="replyForm" :rules="replyRules" label-width="80px">
        <el-form-item label="回复内容" prop="reply"><el-input v-model="replyForm.reply" type="textarea" :rows="5" placeholder="请输入回复内容" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="replyDialogVisible = false">取消</el-button><el-button type="primary" :loading="replyLoading" @click="handleSubmitReply">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getFeedbackList, replyFeedback, deleteFeedback } from '@/api/admin/feedback'
import { formatDateTime } from '@/utils/common'

const loading = ref(false), tableData = ref([]), total = ref(0), viewDialogVisible = ref(false), replyDialogVisible = ref(false), replyLoading = ref(false), replyFormRef = ref(null)
const queryParams = reactive({ pageNum: 1, pageSize: 10, status: null })
const currentFeedback = ref({})
const replyForm = reactive({ id: null, reply: '' })
const replyRules = { reply: [{ required: true, message: '请输入回复内容', trigger: 'blur' }] }

async function fetchList() { loading.value = true; try { const res = await getFeedbackList(queryParams); tableData.value = res.data.records; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
function resetQuery() { queryParams.status = null; queryParams.pageNum = 1; fetchList() }
function handleView(row) { currentFeedback.value = row; viewDialogVisible.value = true }
function handleReply(row) { replyForm.id = row.id; replyForm.reply = ''; replyDialogVisible.value = true }
async function handleSubmitReply() { const valid = await replyFormRef.value.validate().catch(() => false); if (!valid) return; replyLoading.value = true; try { await replyFeedback(replyForm); ElMessage.success('回复成功'); replyDialogVisible.value = false; fetchList() } finally { replyLoading.value = false } }
function handleDelete(row) { ElMessageBox.confirm('确定要删除该反馈吗？', '提示', { type: 'warning' }).then(async () => { await deleteFeedback(row.id); ElMessage.success('删除成功'); fetchList() }).catch(() => {}) }

fetchList()
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.feedback-manage { .search-form { display: flex; justify-content: space-between; align-items: flex-start; gap: $spacing-md; } }
</style>
