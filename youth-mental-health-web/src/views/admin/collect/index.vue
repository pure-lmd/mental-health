<template>
  <div class="collect-manage">
    <div class="page-title"><svg-icon name="collect" :size="24" /><span>收藏管理</span></div>
    <div class="search-form">
      <el-button type="primary" @click="fetchList">
        <svg-icon name="refresh" :size="14" />
      </el-button>
    </div>
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="用户" prop="userNickname" min-width="120" />
        <el-table-column label="收藏类型" width="120">
          <template #default="{ row }">{{ getCollectTypeText(row.type) }}</template>
        </el-table-column>
        <el-table-column label="收藏内容" prop="targetTitle" min-width="200" show-overflow-tooltip />
        <el-table-column label="收藏时间" prop="createTime" width="180">
          <template #default="{ row }">{{ formatDateTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button type="danger" link @click="handleDelete(row)">
              <svg-icon name="delete" :size="14" />
              <span>删除</span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-center"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" @size-change="fetchList" @current-change="fetchList" /></div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getCollectList, deleteCollect } from '@/api/admin/collect'
import { formatDateTime, getCollectTypeText } from '@/utils/common'

const loading = ref(false), tableData = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

async function fetchList() { loading.value = true; try { const res = await getCollectList(queryParams); tableData.value = res.data.records; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
function handleDelete(row) { ElMessageBox.confirm('确定要删除该收藏记录吗？', '提示', { type: 'warning' }).then(async () => { await deleteCollect(row.id); ElMessage.success('删除成功'); fetchList() }).catch(() => {}) }

fetchList()
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.collect-manage { .search-form { margin-bottom: $spacing-md; } }
</style>
