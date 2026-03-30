<template>
  <div class="my-registration-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>我的预约</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="registration-wrapper" v-loading="loading">
      <el-table :data="list" style="width: 100%" v-if="list.length">
        <el-table-column prop="activityTitle" label="活动名称" />
        <el-table-column prop="createTime" label="报名时间" width="180">
          <template #default="{ row }">{{ formatDateTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusName(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="danger" link @click="handleCancel(row)"><svg-icon name="delete" :size="14" /> 取消</el-button>
            <el-button type="primary" link @click="goDetail(row)"><svg-icon name="view" :size="14" /> 查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无预约记录" />
    </div>
    <div class="pagination-center" v-if="total > 0"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" /></div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyRegistration, cancelRegistration } from '@/api/front/registration'
import { formatDateTime } from '@/utils/common'

const router = useRouter()
const loading = ref(false), list = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

const statusMap = { 0: '待审核', 1: '已通过', 2: '已拒绝', 3: '已取消' }
const statusTypeMap = { 0: 'warning', 1: 'success', 2: 'danger', 3: 'info' }
function statusName(s) { return statusMap[s] || '未知' }
function statusType(s) { return statusTypeMap[s] || 'info' }
function goDetail(row) { router.push(`/front/activity/${row.activityId}`) }

async function fetchList() { loading.value = true; try { const res = await getMyRegistration(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }

async function handleCancel(row) {
  await ElMessageBox.confirm('确认取消此预约？', '提示', { type: 'warning' })
  try { await cancelRegistration(row.id); ElMessage.success('已取消'); fetchList() } catch (e) { console.error(e) }
}

onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.my-registration-page { .registration-wrapper { background: $bg-white; border-radius: $border-radius-base; box-shadow: $shadow-light; padding: $spacing-md; min-height: 300px; } }
</style>
