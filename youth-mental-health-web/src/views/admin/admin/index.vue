<template>
  <div class="admin-manage">
    <div class="page-title"><svg-icon name="admin" :size="24" /><span>管理员管理</span></div>
    <div class="search-form">
      <el-button type="primary" @click="handleAdd"><svg-icon name="add" :size="14" /><span>添加管理员</span></el-button>
      <el-button @click="fetchList"><svg-icon name="refresh" :size="14" /><span>刷新</span></el-button>
    </div>
    <div class="table-container">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="头像" width="80"><template #default="{ row }"><el-avatar :size="40" :src="row.avatar"><svg-icon name="admin" :size="20" /></el-avatar></template></el-table-column>
        <el-table-column label="用户名" prop="username" min-width="120" />
        <el-table-column label="昵称" prop="nickname" min-width="120" />
        <el-table-column label="手机号" prop="phone" width="130" />
        <el-table-column label="状态" width="100"><template #default="{ row }"><el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="handleStatusChange(row)" /></template></el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180"><template #default="{ row }">{{ formatDateTime(row.createTime) }}</template></el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)"><svg-icon name="edit" :size="14" /><span>编辑</span></el-button>
            <el-button type="danger" link @click="handleDelete(row)"><svg-icon name="delete" :size="14" /><span>删除</span></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-center"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper" @size-change="fetchList" @current-change="fetchList" /></div>
    </div>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" destroy-on-close>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="用户名" prop="username"><el-input v-model="formData.username" placeholder="请输入用户名" :disabled="!!formData.id" :prefix-icon="User" /></el-form-item>
        <el-form-item label="昵称" prop="nickname"><el-input v-model="formData.nickname" placeholder="请输入昵称" :prefix-icon="EditPen" /></el-form-item>
        <el-form-item v-if="!formData.id" label="密码" prop="password"><el-input v-model="formData.password" type="password" show-password placeholder="请输入密码" :prefix-icon="Lock" /></el-form-item>
        <el-form-item label="手机号" prop="phone"><el-input v-model="formData.phone" placeholder="请输入手机号" :prefix-icon="Phone" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="formData.status"><el-radio :value="1">启用</el-radio><el-radio :value="0">禁用</el-radio></el-radio-group></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAdminList, addAdmin, updateAdmin, deleteAdmin, updateAdminStatus } from '@/api/admin/admin'
import { formatDateTime } from '@/utils/common'
import { User, EditPen, Lock, Phone } from '@element-plus/icons-vue'

const loading = ref(false), tableData = ref([]), total = ref(0), dialogVisible = ref(false), submitLoading = ref(false), formRef = ref(null)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })
const formData = reactive({ id: null, username: '', nickname: '', password: '', phone: '', status: 1 })
const formRules = { username: [{ required: true, message: '请输入用户名', trigger: 'blur' }], nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }], password: [{ required: true, message: '请输入密码', trigger: 'blur' }] }
const dialogTitle = computed(() => formData.id ? '编辑管理员' : '添加管理员')

async function fetchList() { loading.value = true; try { const res = await getAdminList(queryParams); tableData.value = res.data.records; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
function handleAdd() { Object.assign(formData, { id: null, username: '', nickname: '', password: '', phone: '', status: 1 }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(formData, { ...row, password: '' }); dialogVisible.value = true }
function handleDelete(row) { ElMessageBox.confirm('确定要删除该管理员吗？', '提示', { type: 'warning' }).then(async () => { await deleteAdmin(row.id); ElMessage.success('删除成功'); fetchList() }).catch(() => {}) }
async function handleStatusChange(row) { try { await updateAdminStatus(row.id, row.status); ElMessage.success('状态更新成功') } catch (e) { row.status = row.status === 1 ? 0 : 1 } }
async function handleSubmit() { const valid = await formRef.value.validate().catch(() => false); if (!valid) return; submitLoading.value = true; try { if (formData.id) { await updateAdmin(formData); ElMessage.success('更新成功') } else { await addAdmin(formData); ElMessage.success('添加成功') }; dialogVisible.value = false; fetchList() } finally { submitLoading.value = false } }

fetchList()
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.admin-manage { .search-form { display: flex; gap: $spacing-md; margin-bottom: $spacing-md; } }
</style>
