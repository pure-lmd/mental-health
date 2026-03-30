<template>
  <div class="my-feedback-page">
    <div class="breadcrumb-wrapper">
      <el-breadcrumb>
        <el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我的反馈</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="primary" @click="handleAdd"><svg-icon name="add" :size="14" /> 新增反馈</el-button>
    </div>
    
    <div class="feedback-wrapper" v-loading="loading">
      <div class="feedback-list" v-if="list.length">
        <div v-for="item in list" :key="item.id" class="feedback-item">
          <div class="feedback-header">
            <h4>{{ item.title }}</h4>
            <div class="header-right">
              <el-tag :type="item.status === 1 ? 'success' : 'warning'" size="small">{{ item.status === 1 ? '已回复' : '待处理' }}</el-tag>
              <div class="actions">
                <el-button v-if="item.status !== 1" type="primary" link size="small" @click="handleEdit(item)"><svg-icon name="edit" :size="14" /> 编辑</el-button>
                <el-button type="danger" link size="small" @click="handleDelete(item)"><svg-icon name="delete" :size="14" /> 删除</el-button>
              </div>
            </div>
          </div>
          <p class="content">{{ item.content }}</p>
          <p class="time"><svg-icon name="time" :size="12" /> {{ formatDateTime(item.createTime) }}</p>
          <div class="reply-box" v-if="item.reply">
            <p class="reply-label"><svg-icon name="reply" :size="14" /> 管理员回复：</p>
            <p class="reply-content">{{ item.reply }}</p>
            <p class="reply-time">{{ formatDateTime(item.replyTime) }}</p>
          </div>
        </div>
      </div>
      <el-empty v-else description="暂无反馈记录" />
    </div>
    <div class="pagination-center" v-if="total > 0">
      <el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" />
    </div>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑反馈' : '新增反馈'" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入反馈标题" maxlength="50" show-word-limit />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="4" placeholder="请输入反馈内容" maxlength="500" show-word-limit />
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyFeedback, submitFeedback, updateFeedback, deleteFeedback } from '@/api/front/feedback'
import { formatDateTime } from '@/utils/common'

const loading = ref(false), list = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })
const dialogVisible = ref(false), submitLoading = ref(false), isEdit = ref(false)
const formRef = ref(null)
const form = reactive({ id: null, title: '', content: '' })
const rules = {
  title: [{ required: true, message: '请输入反馈标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入反馈内容', trigger: 'blur' }]
}

async function fetchList() {
  loading.value = true
  try {
    const res = await getMyFeedback(queryParams)
    list.value = res.data.records || []
    total.value = res.data.total
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

function handleAdd() {
  isEdit.value = false
  form.id = null
  form.title = ''
  form.content = ''
  dialogVisible.value = true
}

function handleEdit(row) {
  isEdit.value = true
  form.id = row.id
  form.title = row.title
  form.content = row.content
  dialogVisible.value = true
}

async function handleSubmit() {
  await formRef.value.validate()
  submitLoading.value = true
  try {
    if (isEdit.value) {
      await updateFeedback(form.id, { title: form.title, content: form.content })
      ElMessage.success('修改成功')
    } else {
      await submitFeedback({ title: form.title, content: form.content })
      ElMessage.success('提交成功')
    }
    dialogVisible.value = false
    fetchList()
  } catch (e) {
    console.error(e)
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm('确定要删除该反馈吗？', '提示', { type: 'warning' })
  try {
    await deleteFeedback(row.id)
    ElMessage.success('删除成功')
    fetchList()
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.my-feedback-page {
  .breadcrumb-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .feedback-wrapper {
    background: $bg-white;
    border-radius: $border-radius-base;
    box-shadow: $shadow-light;
    min-height: 300px;
  }
  
  .feedback-list {
    .feedback-item {
      padding: $spacing-lg;
      border-bottom: 1px solid $border-lighter;
      &:last-child { border-bottom: none; }
      
      .feedback-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: $spacing-sm;
        
        h4 { font-size: $font-size-medium; color: $text-primary; }
        
        .header-right {
          display: flex;
          align-items: center;
          gap: $spacing-md;
          
          .actions {
            display: flex;
            gap: $spacing-xs;
          }
        }
      }
      
      .content { color: $text-regular; line-height: 1.6; margin-bottom: $spacing-sm; }
      .time { color: $text-placeholder; font-size: $font-size-small; display: flex; align-items: center; gap: 4px; }
      
      .reply-box {
        margin-top: $spacing-md;
        padding: $spacing-md;
        background: $bg-color;
        border-radius: $border-radius-small;
        border-left: 3px solid $primary-color;
        
        .reply-label { color: $primary-color; font-size: $font-size-small; margin-bottom: $spacing-xs; display: flex; align-items: center; gap: 4px; }
        .reply-content { color: $text-regular; line-height: 1.6; }
        .reply-time { color: $text-placeholder; font-size: $font-size-small; margin-top: $spacing-xs; }
      }
    }
  }
}
</style>
