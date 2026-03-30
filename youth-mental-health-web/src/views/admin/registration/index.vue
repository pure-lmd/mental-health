<template>
  <div class="registration-manage">
    <div class="page-title"><svg-icon name="registration" :size="24" /><span>报名管理</span></div>
    <div class="search-form">
      <el-form :inline="true" :model="queryParams">
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 150px">
            <el-option label="待审核" :value="0" />
            <el-option label="已通过" :value="1" />
            <el-option label="已拒绝" :value="2" />
            <el-option label="已取消" :value="3" />
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
    </div>
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="用户" prop="userNickname" min-width="120" />
        <el-table-column label="活动" prop="activityTitle" min-width="180" show-overflow-tooltip />
        <el-table-column label="备注" prop="remark" min-width="150" show-overflow-tooltip />
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getRegistrationStatusType(row.status)">{{ getRegistrationStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" fixed="right">
          <template #default="{ row }">
            <div class="action-buttons">
              <el-button type="info" link @click="handleView(row)">
                <svg-icon name="view" :size="14" />
                <span>查看</span>
              </el-button>
              <template v-if="row.status === 0">
                <el-button type="success" link @click="handleAudit(row, 1)">
                  <svg-icon name="check" :size="14" />
                  <span>通过</span>
                </el-button>
                <el-button type="danger" link @click="handleAudit(row, 2)">
                  <svg-icon name="close" :size="14" />
                  <span>拒绝</span>
                </el-button>
              </template>
              <el-button type="danger" link @click="handleDelete(row)">
                <svg-icon name="delete" :size="14" />
                <span>删除</span>
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-center"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" @size-change="fetchList" @current-change="fetchList" /></div>
    </div>
    
    <!-- 报名详情对话框 -->
    <el-dialog v-model="viewDialogVisible" title="报名详情" width="600px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="用户">{{ currentRow.userNickname }}</el-descriptions-item>
        <el-descriptions-item label="活动">{{ currentRow.activityTitle }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{ currentRow.remark || '-' }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getRegistrationStatusType(currentRow.status)">{{ getRegistrationStatusText(currentRow.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="报名时间">{{ formatDateTime(currentRow.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="审核备注" v-if="currentRow.auditRemark">{{ currentRow.auditRemark }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRegistrationList, auditRegistration, deleteRegistration } from '@/api/admin/registration'
import { formatDateTime, getRegistrationStatusText, getRegistrationStatusType } from '@/utils/common'

const loading = ref(false), tableData = ref([]), total = ref(0), viewDialogVisible = ref(false)
const currentRow = ref({})
const queryParams = reactive({ pageNum: 1, pageSize: 10, status: null })

async function fetchList() { loading.value = true; try { const res = await getRegistrationList(queryParams); tableData.value = res.data.records; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
function resetQuery() { queryParams.status = null; queryParams.pageNum = 1; fetchList() }
function handleView(row) { currentRow.value = row; viewDialogVisible.value = true }
function handleAudit(row, status) {
  const text = status === 1 ? '通过' : '拒绝'
  ElMessageBox.prompt(`确定${text}该报名申请吗？`, '审核', { inputPlaceholder: '请输入审核备注（可选）' }).then(async ({ value }) => {
    await auditRegistration({ id: row.id, status, auditRemark: value }); ElMessage.success(`${text}成功`); fetchList()
  }).catch(() => {})
}
function handleDelete(row) { ElMessageBox.confirm('确定要删除该报名记录吗？', '提示', { type: 'warning' }).then(async () => { await deleteRegistration(row.id); ElMessage.success('删除成功'); fetchList() }).catch(() => {}) }

fetchList()
</script>

<style lang="scss" scoped>
.registration-manage {
  .search-form {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: $spacing-md;
  }
  
  .action-buttons {
    display: flex;
    gap: $spacing-sm;
    flex-wrap: wrap;
  }
}
</style>
