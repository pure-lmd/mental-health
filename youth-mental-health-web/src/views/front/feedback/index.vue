<template>
  <div class="feedback-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>意见反馈</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="feedback-wrapper">
      <div class="feedback-form-card">
        <h3><svg-icon name="feedback" :size="20" /> 提交反馈</h3>
        <el-form ref="formRef" :model="formData" :rules="formRules" label-position="top">
          <el-form-item label="反馈标题" prop="title"><el-input v-model="formData.title" placeholder="请输入反馈标题" /></el-form-item>
          <el-form-item label="反馈内容" prop="content"><el-input v-model="formData.content" type="textarea" :rows="6" placeholder="请详细描述您的问题或建议" /></el-form-item>
          <el-form-item><el-button type="primary" :loading="submitLoading" @click="handleSubmit"><svg-icon name="send" :size="14" /> 提交反馈</el-button></el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { submitFeedback } from '@/api/front/feedback'

const formRef = ref(null)
const submitLoading = ref(false)
const formData = reactive({ title: '', content: '' })
const formRules = { title: [{ required: true, message: '请输入反馈标题', trigger: 'blur' }], content: [{ required: true, message: '请输入反馈内容', trigger: 'blur' }] }

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitLoading.value = true
  try {
    await submitFeedback(formData)
    ElMessage.success('反馈提交成功')
    formData.title = ''
    formData.content = ''
  } catch (e) { console.error(e) } finally { submitLoading.value = false }
}
</script>

<style lang="scss" scoped>
.feedback-page { .feedback-wrapper { max-width: 600px; margin: 0 auto; }
  .feedback-form-card { background: $bg-white; border-radius: $border-radius-base; padding: $spacing-xl; box-shadow: $shadow-light;
    h3 { display: flex; align-items: center; gap: $spacing-sm; font-size: $font-size-large; color: $text-primary; margin-bottom: $spacing-lg; padding-bottom: $spacing-md; border-bottom: 1px solid $border-lighter; } } }
</style>
