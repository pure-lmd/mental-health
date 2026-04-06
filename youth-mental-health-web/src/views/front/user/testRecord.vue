<template>
  <div class="test-record-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>测试记录</el-breadcrumb-item></el-breadcrumb></div>

    <div class="record-wrapper" v-loading="loading">
      <el-table :data="list" style="width: 100%" v-if="list.length">
        <el-table-column prop="testTitle" label="测试名称" min-width="200" />
        <el-table-column prop="totalScore" label="得分" width="100">
          <template #default="{ row }"><span class="score">{{ row.totalScore }}</span></template>
        </el-table-column>
        <el-table-column prop="result" label="结果" width="120">
          <template #default="{ row }"><el-tag :type="getResultType(row.result)">{{ getResultText(row.result) }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="createTime" label="测试时间" width="180">
          <template #default="{ row }">{{ formatDateTime(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="viewResult(row)"><svg-icon name="view" :size="14" /> 查看结果</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无测试记录" />
    </div>
    <div class="pagination-center" v-if="total > 0"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" /></div>

    <el-dialog v-model="dialogVisible" title="测试结果详情" width="700px">
      <div class="result-detail" v-if="currentRecord">
        <div class="result-header">
          <div class="score-circle"><span>{{ currentRecord.totalScore }}</span>分</div>
          <div class="result-info">
            <h3>{{ currentRecord.testTitle }}</h3>
            <el-tag :type="getResultType(currentRecord.result)" size="large">{{ getResultText(currentRecord.result) }}</el-tag>
          </div>
        </div>
        <div class="result-content">
          <h4>结果分析</h4>
          <p>{{ getResultAnalysis(currentRecord.result) }}</p>
        </div>

        <!-- 新增：系统推荐内容 -->
        <div class="result-suggestion" v-loading="recommendLoading">
          <h4>📚 系统为您推荐</h4>
          
          <template v-if="recommendData">
            <!-- 推荐科普 -->
            <div class="recommend-section" v-if="recommendData.knowledgeList && recommendData.knowledgeList.length > 0">
              <h5>推荐阅读</h5>
              <div class="recommend-list">
                <div class="recommend-item" v-for="item in recommendData.knowledgeList" :key="item.id" @click="goToKnowledge(item.id)">
                  <img v-if="item.cover" :src="item.cover" :alt="item.title" />
                  <div class="item-info">
                    <h6>{{ item.title }}</h6>
                    <p>{{ item.summary }}</p>
                  </div>
                </div>
              </div>
            </div>

            <!-- 推荐活动 -->
            <div class="recommend-section" v-if="recommendData.activityList && recommendData.activityList.length > 0">
              <h5>推荐活动</h5>
              <div class="recommend-list">
                <div class="recommend-item" v-for="item in recommendData.activityList" :key="item.id" @click="goToActivity(item.id)">
                  <img v-if="item.cover" :src="item.cover" :alt="item.title" />
                  <div class="item-info">
                    <h6>{{ item.title }}</h6>
                    <p>开始时间: {{ item.startTime }}</p>
                  </div>
                </div>
              </div>
            </div>

            <!-- AI咨询建议 -->
            <div class="ai-suggest" v-if="recommendData.suggestAiChat">
              <el-alert :title="recommendData.suggestMessage" type="warning" :closable="false" show-icon />
              <el-button type="primary" style="margin-top: 12px" @click="goToAiChat">💬 立即与AI咨询师聊聊</el-button>
            </div>
          </template>
          
          <el-empty v-else-if="!recommendLoading" description="暂无推荐内容" />
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getMyTestRecords as getMyTestRecord } from '@/api/front/test'
import { getRecommendByHealthStatus } from '@/api/front/recommend'
import { formatDateTime } from '@/utils/common'

const router = useRouter()
const loading = ref(false), list = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })
const dialogVisible = ref(false)
const currentRecord = ref(null)
const recommendData = ref(null)
const recommendLoading = ref(false)

function getResultType(result) { const map = { 0: 'success', 1: 'primary', 2: 'warning' }; return map[result] || 'info' }
function getResultText(result) { const map = { 0: '健康', 1: '良好', 2: '建议沟通' }; return map[result] || '未知' }
function getResultAnalysis(result) {
  const map = {
    0: '您的心理状态非常健康，请继续保持积极乐观的心态！',
    1: '您的心理状态良好，建议适当关注自身情绪变化，保持健康的生活方式。',
    2: '建议您与家长、老师或心理咨询师进行沟通，及时疏导情绪。'
  }
  return map[result] || '暂无详细分析'
}

// 从后端获取推荐数据
async function fetchRecommend(result) {
  recommendLoading.value = true
  try {
    const res = await getRecommendByHealthStatus(result)
    recommendData.value = res.data
  } catch (e) {
    console.error('获取推荐数据失败:', e)
    recommendData.value = null
  } finally {
    recommendLoading.value = false
  }
}

async function viewResult(row) {
  currentRecord.value = row
  recommendData.value = null
  dialogVisible.value = true
  // 获取推荐数据
  await fetchRecommend(row.result)
}

function goToKnowledge(id) {
  router.push(`/front/knowledge/${id}`)
  dialogVisible.value = false
}

function goToActivity(id) {
  router.push(`/front/activity/${id}`)
  dialogVisible.value = false
}

function goToAiChat() {
  router.push('/front/ai-chat')
  dialogVisible.value = false
}

async function fetchList() { loading.value = true; try { const res = await getMyTestRecord(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }
onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.test-record-page { 
  .record-wrapper { background: $bg-white; border-radius: $border-radius-base; box-shadow: $shadow-light; padding: $spacing-md; min-height: 300px; }
  .score { font-size: $font-size-large; font-weight: 600; color: $primary-color; } 
}

.result-detail { 
  .result-header { display: flex; align-items: center; gap: $spacing-lg; padding-bottom: $spacing-lg; border-bottom: 1px solid $border-lighter;
    .score-circle { width: 80px; height: 80px; border-radius: 50%; background: linear-gradient(135deg, $primary-color, $accent-color); color: #fff; display: flex; flex-direction: column; align-items: center; justify-content: center; span { font-size: 28px; font-weight: 700; } }
    .result-info { h3 { font-size: $font-size-large; color: $text-primary; margin-bottom: $spacing-sm; } } 
  }
  .result-content, .result-suggestion { margin-top: $spacing-lg; h4 { font-size: $font-size-medium; color: $text-primary; margin-bottom: $spacing-sm; } p { color: $text-regular; line-height: 1.8; } }
  .result-suggestion { 
    background: $bg-color; 
    padding: $spacing-md; 
    border-radius: $border-radius-small; 
    border-left: 3px solid $accent-color;
    
    h5 {
      font-size: $font-size-base;
      color: $text-primary;
      margin: $spacing-md 0 $spacing-sm;
      padding-bottom: $spacing-xs;
      border-bottom: 2px solid $primary-color;
    }
    
    .recommend-section {
      margin-bottom: $spacing-md;
    }
    
    .recommend-list {
      display: flex;
      flex-direction: column;
      gap: $spacing-sm;
    }
    
    .recommend-item {
      display: flex;
      gap: $spacing-sm;
      padding: $spacing-sm;
      border-radius: $border-radius-small;
      background: $bg-white;
      cursor: pointer;
      transition: all 0.3s;

      &:hover {
        box-shadow: $shadow-base;
        transform: translateY(-2px);
      }

      img {
        width: 60px;
        height: 60px;
        object-fit: cover;
        border-radius: $border-radius-small;
        flex-shrink: 0;
        background: $bg-color;
      }

      .item-info {
        flex: 1;
        overflow: hidden;

        h6 {
          font-size: $font-size-base;
          color: $text-primary;
          margin-bottom: $spacing-xs;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        p {
          font-size: $font-size-small;
          color: $text-secondary;
          line-height: 1.5;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }
      }
    }
    
    .ai-suggest {
      margin-top: $spacing-md;
    }
  } 
}
</style>
