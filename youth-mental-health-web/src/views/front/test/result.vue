<template>
  <div class="test-result">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item :to="{ path: '/front/test' }">心理测试</el-breadcrumb-item><el-breadcrumb-item>测试结果</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="result-wrapper" v-loading="loading">
      <div class="result-card">
        <div class="result-header" :class="resultClass">
          <div class="score-circle">
            <span class="score">{{ record.totalScore || 0 }}</span>
            <span class="label">分</span>
          </div>
        </div>
        
        <div class="result-info">
          <h2>{{ record.testTitle }}</h2>
          <div class="result-status" :class="resultClass">
            <svg-icon :name="resultIcon" :size="24" />
            <span>{{ getHealthStatusText(record.result) }}</span>
          </div>
          <p class="result-desc">{{ resultDescription }}</p>
          <div class="meta">
            <span><svg-icon name="time" :size="14" /> 测试时间：{{ formatDateTime(record.createTime) }}</span>
          </div>
        </div>
        
        <div class="result-actions">
          <el-button type="primary" @click="$router.push('/front/test')"><svg-icon name="test" :size="14" /> 继续测试</el-button>
          <el-button @click="$router.push('/front/user/test-record')"><svg-icon name="record" :size="14" /> 查看记录</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getTestRecordDetail } from '@/api/front/test'
import { formatDateTime, getHealthStatusText } from '@/utils/common'

const route = useRoute()
const loading = ref(false)
const record = ref({})

const resultClass = computed(() => {
  const r = record.value.result
  if (r === 0) return 'healthy'
  if (r === 1) return 'good'
  return 'need-talk'
})

const resultIcon = computed(() => {
  const r = record.value.result
  if (r === 0) return 'success'
  if (r === 1) return 'warning'
  return 'error'
})

const resultDescription = computed(() => {
  const r = record.value.result
  if (r === 0) return '您的心理状态非常健康，请继续保持良好的心态！'
  if (r === 1) return '您的心理状态良好，建议适当关注自己的情绪变化。'
  return '建议您与专业人士进行沟通，我们会帮助您更好地了解自己。'
})

async function fetchRecord() { loading.value = true; try { const res = await getTestRecordDetail(route.params.id); record.value = res.data } catch (e) { console.error(e) } finally { loading.value = false } }

onMounted(() => { fetchRecord() })
</script>

<style lang="scss" scoped>
.test-result {
  .result-wrapper { display: flex; justify-content: center; }
  
  .result-card {
    background: $bg-white;
    border-radius: $border-radius-large;
    overflow: hidden;
    width: 500px;
    box-shadow: $shadow-medium;
    
    .result-header {
      padding: $spacing-xl * 2;
      display: flex;
      justify-content: center;
      
      &.healthy { background: linear-gradient(135deg, #36B37E, #57D9A3); }
      &.good { background: linear-gradient(135deg, #FFAB00, #FFD740); }
      &.need-talk { background: linear-gradient(135deg, #FF5630, #FF8F73); }
      
      .score-circle {
        width: 120px;
        height: 120px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.3);
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        color: #fff;
        
        .score { font-size: 42px; font-weight: 700; }
        .label { font-size: $font-size-small; }
      }
    }
    
    .result-info {
      padding: $spacing-xl;
      text-align: center;
      
      h2 { font-size: $font-size-xl; color: $text-primary; margin-bottom: $spacing-md; }
      
      .result-status {
        display: inline-flex;
        align-items: center;
        gap: $spacing-sm;
        padding: $spacing-sm $spacing-lg;
        border-radius: $border-radius-round;
        font-size: $font-size-large;
        font-weight: 600;
        margin-bottom: $spacing-md;
        
        &.healthy { background: rgba($success-color, 0.1); color: $success-color; }
        &.good { background: rgba($warning-color, 0.1); color: $warning-color; }
        &.need-talk { background: rgba($danger-color, 0.1); color: $danger-color; }
      }
      
      .result-desc { color: $text-secondary; line-height: 1.6; margin-bottom: $spacing-md; }
      .meta { color: $text-placeholder; font-size: $font-size-small; span { display: flex; align-items: center; justify-content: center; gap: 4px; } }
    }
    
    .result-actions { display: flex; justify-content: center; gap: $spacing-md; padding: 0 $spacing-xl $spacing-xl; }
  }
}
</style>
