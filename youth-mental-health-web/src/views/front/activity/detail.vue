<template>
  <div class="activity-detail">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item :to="{ path: '/front/activity' }">心理活动</el-breadcrumb-item><el-breadcrumb-item>{{ detail.title }}</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="detail-wrapper" v-loading="loading">
      <div class="detail-header">
        <div class="cover" :style="{ backgroundImage: `url(${detail.cover})` }"></div>
        <div class="info">
          <el-tag :type="getActivityStatusType(detail.status)" size="large">{{ getActivityStatusText(detail.status) }}</el-tag>
          <h1>{{ detail.title }}</h1>
          <p class="desc">{{ detail.description }}</p>
          <div class="meta-list">
            <div class="meta-item"><svg-icon name="location" :size="18" /><span>{{ detail.address }}</span></div>
            <div class="meta-item"><svg-icon name="time" :size="18" /><span>{{ formatDateTime(detail.startTime) }} - {{ formatDateTime(detail.endTime) }}</span></div>
            <div class="meta-item"><svg-icon name="user" :size="18" /><span>报名人数：{{ detail.currentNum || 0 }} / {{ detail.maxNum || '-' }}</span></div>
          </div>
          <el-button v-if="detail.status === 1 && !hasRegistered" type="primary" size="large" @click="handleRegister" :loading="registerLoading">
            <svg-icon name="registration" :size="16" /> 立即报名
          </el-button>
          <el-button v-else-if="hasRegistered" type="success" size="large" disabled>
            <svg-icon name="check" :size="16" /> 已报名
          </el-button>
          <el-button v-else type="info" size="large" disabled>{{ detail.status === 0 ? '活动未开放' : detail.status === 2 ? '报名已截止' : '活动已结束' }}</el-button>
        </div>
      </div>
      
      <div class="detail-content">
        <h3>活动详情</h3>
        <div class="content-body" v-html="detail.content"></div>
      </div>
    </div>
    
    <el-dialog v-model="dialogVisible" title="活动报名" width="500px">
      <el-form :model="registerForm" label-width="80px">
        <el-form-item label="备注"><el-input v-model="registerForm.remark" type="textarea" :rows="3" placeholder="请输入报名备注（可选）" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="registerLoading" @click="submitRegister">确认报名</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getActivityDetail, registerActivity } from '@/api/front/activity'
import { formatDateTime, getActivityStatusText, getActivityStatusType } from '@/utils/common'

const route = useRoute()
const loading = ref(false), registerLoading = ref(false), dialogVisible = ref(false)
const detail = ref({})
const hasRegistered = ref(false)
const registerForm = reactive({ activityId: null, remark: '' })

async function fetchDetail() { loading.value = true; try { const res = await getActivityDetail(route.params.id); detail.value = res.data.activity; hasRegistered.value = res.data.hasRegistered || false } catch (e) { console.error(e) } finally { loading.value = false } }
function handleRegister() { registerForm.activityId = detail.value.id; registerForm.remark = ''; dialogVisible.value = true }
async function submitRegister() { registerLoading.value = true; try { await registerActivity(registerForm); ElMessage.success('报名成功，请等待审核'); dialogVisible.value = false; hasRegistered.value = true } catch (e) { console.error(e) } finally { registerLoading.value = false } }

onMounted(() => { fetchDetail() })
</script>

<style lang="scss" scoped>
.activity-detail {
  .detail-wrapper { background: $bg-white; border-radius: $border-radius-base; box-shadow: $shadow-light; overflow: hidden; }
  
  .detail-header {
    display: flex;
    gap: $spacing-xl;
    padding: $spacing-xl;
    border-bottom: 1px solid $border-lighter;
    
    .cover { width: 400px; height: 280px; background-size: cover; background-position: center; background-color: $bg-color; border-radius: $border-radius-base; flex-shrink: 0; }
    
    .info {
      flex: 1;
      
      .el-tag { margin-bottom: $spacing-md; }
      h1 { font-size: 28px; color: $text-primary; margin-bottom: $spacing-md; }
      .desc { color: $text-secondary; line-height: 1.6; margin-bottom: $spacing-lg; }
      .meta-list { margin-bottom: $spacing-lg; .meta-item { display: flex; align-items: center; gap: $spacing-sm; color: $text-regular; margin-bottom: $spacing-sm; } }
    }
  }
  
  .detail-content {
    padding: $spacing-xl;
    h3 { font-size: $font-size-large; color: $text-primary; margin-bottom: $spacing-lg; padding-bottom: $spacing-md; border-bottom: 1px solid $border-lighter; }
    .content-body { line-height: 1.8; color: $text-regular; white-space: pre-wrap; }
  }
}
</style>
