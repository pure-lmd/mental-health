<template>
  <div class="growth-trajectory">
    <div class="page-header">
      <h2>📈 我的成长轨迹</h2>
      <p>记录你的心理健康成长历程</p>
    </div>

    <div class="content" v-loading="loading">
      <!-- 学习统计卡片 -->
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-icon" style="background: #409EFF;">📚</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.knowledgeViewCount || 0 }}</div>
            <div class="stat-label">科普浏览</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: #67C23A;">⭐</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.knowledgeCollectCount || 0 }}</div>
            <div class="stat-label">科普收藏</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: #E6A23C;">🎯</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.activityJoinCount || 0 }}</div>
            <div class="stat-label">活动参与</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: #F56C6C;">💬</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.aiChatCount || 0 }}</div>
            <div class="stat-label">AI咨询</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: #909399;">⏱️</div>
          <div class="stat-info">
            <div class="stat-value">{{ formatDuration(stats.totalDuration) }}</div>
            <div class="stat-label">学习时长</div>
          </div>
        </div>
        <div class="stat-card highlight">
          <div class="stat-icon" style="background: #E6A23C;">🏆</div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.currentPoints || 0 }}</div>
            <div class="stat-label">当前积分</div>
          </div>
        </div>
      </div>

      <!-- 测试成绩趋势图 -->
      <div class="chart-section" v-if="trajectory.testTrend && trajectory.testTrend.length > 0">
        <h3>📊 测试成绩趋势</h3>
        <div ref="testTrendChart" style="width: 100%; height: 400px;"></div>
      </div>

      <!-- 健康状态历史 -->
      <div class="history-section" v-if="trajectory.healthStatusHistory && trajectory.healthStatusHistory.length > 0">
        <h3>🏥 健康状态历史</h3>
        <div class="timeline">
          <div class="timeline-item" v-for="(item, index) in trajectory.healthStatusHistory" :key="index">
            <div class="timeline-dot" :class="getStatusClass(item.healthStatus)"></div>
            <div class="timeline-content">
              <div class="timeline-date">{{ item.date }}</div>
              <div class="timeline-status" :class="getStatusClass(item.healthStatus)">
                {{ item.statusText }}
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <el-empty v-if="!loading && (!trajectory.testTrend || trajectory.testTrend.length === 0)" description="暂无测试记录，快去完成一次心理测试吧！">
        <el-button type="primary" @click="$router.push('/front/test')">开始测试</el-button>
      </el-empty>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { getGrowthTrajectory } from '@/api/front/growth'

const loading = ref(false)
const trajectory = ref({})
const stats = ref({})
const testTrendChart = ref(null)

function formatDuration(seconds) {
  if (!seconds) return '0分钟'
  const hours = Math.floor(seconds / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  if (hours > 0) {
    return `${hours}小时${minutes}分钟`
  }
  return `${minutes}分钟`
}

function getStatusClass(status) {
  if (status === 0) return 'healthy'
  if (status === 1) return 'good'
  return 'need-talk'
}

function getHealthStatusText(status) {
  if (status === 0) return '健康'
  if (status === 1) return '良好'
  return '建议沟通'
}

function initTestTrendChart() {
  if (!testTrendChart.value || !trajectory.value.testTrend || trajectory.value.testTrend.length === 0) {
    return
  }

  const chart = echarts.init(testTrendChart.value)
  const option = {
    tooltip: {
      trigger: 'axis',
      formatter: function(params) {
        const data = params[0]
        return `${data.name}<br/>分数: ${data.value}<br/>状态: ${getHealthStatusText(data.data.result)}`
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: trajectory.value.testTrend.map(item => item.testDate),
      boundaryGap: false
    },
    yAxis: {
      type: 'value',
      min: 0,
      max: 100
    },
    series: [
      {
        name: '测试分数',
        type: 'line',
        data: trajectory.value.testTrend.map(item => ({
          value: item.score,
          result: item.result
        })),
        smooth: true,
        itemStyle: {
          color: '#409EFF'
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
            { offset: 1, color: 'rgba(64, 158, 255, 0.1)' }
          ])
        },
        markLine: {
          data: [
            { yAxis: 60, name: '健康', lineStyle: { color: '#67C23A' } },
            { yAxis: 80, name: '良好', lineStyle: { color: '#E6A23C' } }
          ]
        }
      }
    ]
  }

  chart.setOption(option)
  window.addEventListener('resize', () => chart.resize())
}

async function fetchData() {
  loading.value = true
  try {
    const res = await getGrowthTrajectory()
    trajectory.value = res.data || {}
    stats.value = res.data.learningStats || {}
    
    // 初始化图表
    setTimeout(() => {
      initTestTrendChart()
    }, 100)
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style lang="scss" scoped>
.growth-trajectory {
  .page-header {
    margin-bottom: $spacing-xl;
    h2 {
      font-size: $font-size-xxl;
      color: $text-primary;
      margin-bottom: $spacing-xs;
    }
    p {
      color: $text-secondary;
      font-size: $font-size-base;
    }
  }

  .content {
    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: $spacing-lg;
      margin-bottom: $spacing-xl;

      .stat-card {
        background: $bg-white;
        border-radius: $border-radius-large;
        padding: $spacing-lg;
        display: flex;
        align-items: center;
        gap: $spacing-md;
        box-shadow: $shadow-base;
        transition: all 0.3s;

        &:hover {
          transform: translateY(-4px);
          box-shadow: $shadow-medium;
        }

        &.highlight {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          color: #fff;

          .stat-label {
            color: rgba(255, 255, 255, 0.9);
          }
        }

        .stat-icon {
          width: 60px;
          height: 60px;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 28px;
          flex-shrink: 0;
        }

        .stat-info {
          flex: 1;

          .stat-value {
            font-size: $font-size-xxl;
            font-weight: 700;
            color: $text-primary;
            margin-bottom: $spacing-xs;
          }

          .stat-label {
            font-size: $font-size-small;
            color: $text-secondary;
          }
        }
      }
    }

    .chart-section, .history-section {
      background: $bg-white;
      border-radius: $border-radius-large;
      padding: $spacing-xl;
      margin-bottom: $spacing-xl;
      box-shadow: $shadow-base;

      h3 {
        font-size: $font-size-xl;
        color: $text-primary;
        margin-bottom: $spacing-lg;
        padding-bottom: $spacing-md;
        border-bottom: 2px solid $primary-color;
      }
    }

    .history-section {
      .timeline {
        .timeline-item {
          display: flex;
          gap: $spacing-md;
          padding-bottom: $spacing-lg;
          position: relative;

          &:not(:last-child)::before {
            content: '';
            position: absolute;
            left: 11px;
            top: 30px;
            bottom: 0;
            width: 2px;
            background: $border-color;
          }

          .timeline-dot {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            flex-shrink: 0;
            position: relative;
            z-index: 1;

            &.healthy {
              background: $success-color;
            }

            &.good {
              background: $warning-color;
            }

            &.need-talk {
              background: $danger-color;
            }
          }

          .timeline-content {
            flex: 1;

            .timeline-date {
              font-size: $font-size-small;
              color: $text-secondary;
              margin-bottom: $spacing-xs;
            }

            .timeline-status {
              display: inline-block;
              padding: $spacing-xs $spacing-md;
              border-radius: $border-radius-round;
              font-size: $font-size-base;
              font-weight: 600;

              &.healthy {
                background: rgba($success-color, 0.1);
                color: $success-color;
              }

              &.good {
                background: rgba($warning-color, 0.1);
                color: $warning-color;
              }

              &.need-talk {
                background: rgba($danger-color, 0.1);
                color: $danger-color;
              }
            }
          }
        }
      }
    }
  }
}
</style>
