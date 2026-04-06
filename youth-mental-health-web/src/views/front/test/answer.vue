<template>
  <div class="test-answer">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item :to="{ path: '/front/test' }">心理测试</el-breadcrumb-item><el-breadcrumb-item>答题</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="answer-wrapper" v-loading="loading">
      <div class="test-header">
        <h2>{{ testDetail.title }}</h2>
        <p>{{ testDetail.description }}</p>
        <div class="progress-info">
          <span>进度：{{ currentIndex + 1 }} / {{ questions.length }}</span>
          <el-progress :percentage="progressPercent" :show-text="false" />
        </div>
      </div>
      
      <div class="question-card" v-if="currentQuestion">
        <div class="question-num">第 {{ currentIndex + 1 }} 题</div>
        <h3 class="question-title">{{ currentQuestion.title }}</h3>
        <div class="options">
          <div 
            v-for="opt in currentQuestion.options" 
            :key="opt.id" 
            class="option-item"
            :class="{ selected: answers[currentQuestion.id] === opt.id }"
            @click="selectOption(currentQuestion.id, opt.id)"
          >
            <span class="option-label">{{ opt.label }}</span>
            <span class="option-content">{{ opt.content }}</span>
          </div>
        </div>
      </div>
      
      <div class="action-btns">
        <el-button @click="prevQuestion" :disabled="currentIndex === 0"><svg-icon name="arrow-right" :size="14" style="transform: rotate(180deg)" /> 上一题</el-button>
        <el-button v-if="currentIndex < questions.length - 1" type="primary" @click="nextQuestion" :disabled="!answers[currentQuestion?.id]">下一题 <svg-icon name="arrow-right" :size="14" /></el-button>
        <el-button v-else type="primary" @click="handleSubmit" :loading="submitLoading" :disabled="!canSubmit">提交测试</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getTestDetail, submitTest } from '@/api/front/test'

const route = useRoute()
const router = useRouter()
const loading = ref(false)
const submitLoading = ref(false)
const testDetail = ref({})
const questions = ref([])
const currentIndex = ref(0)
const answers = ref({})

const currentQuestion = computed(() => questions.value[currentIndex.value])
const progressPercent = computed(() => Math.round(((currentIndex.value + 1) / questions.value.length) * 100))
const canSubmit = computed(() => Object.keys(answers.value).length === questions.value.length)

async function fetchTest() {
  loading.value = true
  try {
    const res = await getTestDetail(route.params.id)
    testDetail.value = res.data
    questions.value = res.data.questions || []
  } catch (e) { console.error(e) } finally { loading.value = false }
}

function selectOption(questionId, optionId) { answers.value[questionId] = optionId }
function prevQuestion() { if (currentIndex.value > 0) currentIndex.value-- }
function nextQuestion() { if (currentIndex.value < questions.value.length - 1 && answers.value[currentQuestion.value?.id]) currentIndex.value++ }

async function handleSubmit() {
  if (!canSubmit.value) { ElMessage.warning('请完成所有题目'); return }

  ElMessageBox.confirm('确定要提交测试吗？', '提示', { type: 'warning' }).then(async () => {
    submitLoading.value = true
    try {
      const answerDetail = Object.entries(answers.value).map(([qId, oId]) => ({ questionId: Number(qId), optionId: Number(oId) }))
      const res = await submitTest({ testId: testDetail.value.id, answers: answerDetail })
      ElMessage.success('提交成功')
      
      // 保存推荐数据到 sessionStorage
      if (res.data.recommend) {
        sessionStorage.setItem('testRecommend_' + res.data.id, JSON.stringify(res.data.recommend))
      }
      
      router.push(`/front/test/result/${res.data.id}`)
    } catch (e) { console.error(e) } finally { submitLoading.value = false }
  }).catch(() => {})
}

onMounted(() => { fetchTest() })
</script>

<style lang="scss" scoped>
.test-answer {
  .answer-wrapper {
    background: $bg-white;
    border-radius: $border-radius-base;
    padding: $spacing-xl;
    box-shadow: $shadow-light;
  }
  
  .test-header {
    text-align: center;
    margin-bottom: $spacing-xl;
    padding-bottom: $spacing-lg;
    border-bottom: 1px solid $border-lighter;
    
    h2 { font-size: $font-size-xxl; color: $text-primary; margin-bottom: $spacing-sm; }
    p { color: $text-secondary; margin-bottom: $spacing-lg; }
    .progress-info { max-width: 400px; margin: 0 auto; span { display: block; text-align: center; color: $text-secondary; font-size: $font-size-small; margin-bottom: $spacing-sm; } }
  }
  
  .question-card {
    max-width: 700px;
    margin: 0 auto $spacing-xl;
    
    .question-num { color: $primary-color; font-weight: 600; margin-bottom: $spacing-sm; }
    .question-title { font-size: $font-size-large; color: $text-primary; margin-bottom: $spacing-lg; line-height: 1.6; }
    
    .options {
      .option-item {
        display: flex;
        align-items: flex-start;
        gap: $spacing-md;
        padding: $spacing-md;
        border: 2px solid $border-lighter;
        border-radius: $border-radius-base;
        margin-bottom: $spacing-sm;
        cursor: pointer;
        transition: $transition-fast;
        
        &:hover { border-color: $primary-light; background: rgba($primary-color, 0.05); }
        &.selected { border-color: $primary-color; background: rgba($primary-color, 0.1); .option-label { background: $primary-color; color: #fff; } }
        
        .option-label { width: 28px; height: 28px; border-radius: 50%; background: $bg-color; display: flex; align-items: center; justify-content: center; font-weight: 600; color: $text-secondary; flex-shrink: 0; }
        .option-content { flex: 1; line-height: 28px; color: $text-regular; }
      }
    }
  }
  
  .action-btns { display: flex; justify-content: center; gap: $spacing-md; .el-button { min-width: 120px; } }
}
</style>
