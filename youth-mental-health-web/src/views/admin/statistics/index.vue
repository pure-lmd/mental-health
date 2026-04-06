<template>
  <div class="statistics-page">
    <div class="page-title">
      <svg-icon name="statistics" :size="24" />
      <span>数据统计</span>
    </div>

    <!-- 概览数据 -->
    <div class="overview-cards">
      <div class="stat-card" style="--card-color: #40A9FF">
        <div class="stat-icon">
          <svg-icon name="user" :size="32" />
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ overview.userCount || 0 }}</div>
          <div class="stat-label">用户总数</div>
        </div>
      </div>

      <div class="stat-card" style="--card-color: #FF7A45">
        <div class="stat-icon">
          <svg-icon name="test" :size="32" />
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ overview.testRecordCount || 0 }}</div>
          <div class="stat-label">测试记录</div>
        </div>
      </div>

      <div class="stat-card" style="--card-color: #67C23A">
        <div class="stat-icon">
          <svg-icon name="activity" :size="32" />
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ overview.activityCount || 0 }}</div>
          <div class="stat-label">活动数量</div>
        </div>
      </div>

      <div class="stat-card" style="--card-color: #E6A23C">
        <div class="stat-icon">
          <svg-icon name="registration" :size="32" />
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ overview.registrationCount || 0 }}</div>
          <div class="stat-label">报名人次</div>
        </div>
      </div>
    </div>

    <!-- 健康趋势分析 -->
    <div class="chart-card full-width">
      <h3 class="chart-title">
        <svg-icon name="chart" :size="18" />
        <span>近7天健康趋势</span>
      </h3>
      <div ref="healthTrendChartRef" class="chart-container"></div>
    </div>

    <!-- 图表区域 -->
    <div class="charts-row">
      <!-- 健康状态统计 -->
      <div class="chart-card">
        <h3 class="chart-title">
          <svg-icon name="mental" :size="18" />
          <span>用户健康状态分布</span>
        </h3>
        <div ref="healthChartRef" class="chart-container"></div>
      </div>

      <!-- 活动报名统计 -->
      <div class="chart-card">
        <h3 class="chart-title">
          <svg-icon name="activity" :size="18" />
          <span>活动报名统计</span>
        </h3>
        <div ref="activityChartRef" class="chart-container"></div>
      </div>
    </div>

    <!-- 测试统计 -->
    <div class="chart-card full-width">
      <h3 class="chart-title">
        <svg-icon name="test" :size="18" />
        <span>测试成绩分布</span>
      </h3>
      <div ref="testChartRef" class="chart-container"></div>
    </div>

    <!-- 高风险用户预警列表 -->
    <div class="chart-card full-width">
      <h3 class="chart-title">
        <svg-icon name="warning" :size="18" />
        <span>高风险用户预警</span>
        <el-tag type="danger" size="small" style="margin-left: auto">{{ warningList.length }} 人</el-tag>
      </h3>
      <el-table :data="warningList" stripe style="width: 100%" v-if="warningList.length > 0">
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column label="健康状态" width="120">
          <template #default="{ row }">
            <el-tag :type="row.healthStatus === 2 ? 'danger' : 'warning'" size="small">
              {{ row.healthStatus === 2 ? '建议沟通' : '良好' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="riskLevel" label="风险等级" width="100">
          <template #default="{ row }">
            <el-tag :type="row.riskLevel === 'HIGH' ? 'danger' : 'warning'" size="small">
              {{ row.riskLevel === 'HIGH' ? '高' : '中' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="keywords" label="触发关键词" show-overflow-tooltip />
        <el-table-column prop="createTime" label="预警时间" width="160" />
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" link @click="handleWarning(row)">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无高风险用户预警" />
    </div>

    <!-- 活动效果评估 -->
    <div class="charts-row">
      <div class="chart-card">
        <h3 class="chart-title">
          <svg-icon name="activity" :size="18" />
          <span>活动效果评估</span>
        </h3>
        <div class="effect-stats">
          <div class="effect-item">
            <div class="effect-label">活动总数</div>
            <div class="effect-value">{{ activityEffect.totalActivities || 0 }}</div>
          </div>
          <div class="effect-item">
            <div class="effect-label">总报名人次</div>
            <div class="effect-value">{{ activityEffect.totalRegistrations || 0 }}</div>
          </div>
          <div class="effect-item">
            <div class="effect-label">平均报名人数</div>
            <div class="effect-value">{{ activityEffect.avgRegistrations || 0 }}</div>
          </div>
        </div>
        <h4 style="margin: $spacing-md 0 $spacing-sm; font-size: $font-size-medium;">热门活动 TOP5</h4>
        <div class="top-list">
          <div class="top-item" v-for="(item, index) in activityEffect.topActivities" :key="index">
            <span class="top-rank" :class="'rank-' + (index + 1)">{{ index + 1 }}</span>
            <span class="top-name">{{ item.title }}</span>
            <span class="top-value">{{ item.registrationCount }} 人</span>
          </div>
        </div>
      </div>

      <div class="chart-card">
        <h3 class="chart-title">
          <svg-icon name="knowledge" :size="18" />
          <span>科普效果评估</span>
        </h3>
        <div class="effect-stats">
          <div class="effect-item">
            <div class="effect-label">科普总数</div>
            <div class="effect-value">{{ knowledgeEffect.totalKnowledge || 0 }}</div>
          </div>
          <div class="effect-item">
            <div class="effect-label">总浏览量</div>
            <div class="effect-value">{{ knowledgeEffect.totalViews || 0 }}</div>
          </div>
          <div class="effect-item">
            <div class="effect-label">平均浏览量</div>
            <div class="effect-value">{{ knowledgeEffect.avgViews || 0 }}</div>
          </div>
        </div>
        <h4 style="margin: $spacing-md 0 $spacing-sm; font-size: $font-size-medium;">热门科普 TOP5</h4>
        <div class="top-list">
          <div class="top-item" v-for="(item, index) in knowledgeEffect.topKnowledge" :key="index">
            <span class="top-rank" :class="'rank-' + (index + 1)">{{ index + 1 }}</span>
            <span class="top-name">{{ item.title }}</span>
            <span class="top-value">{{ item.viewCount }} 次浏览</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'
import { 
  getOverview, 
  getHealthStatistics, 
  getActivityStatistics, 
  getTestStatistics,
  getHealthTrend,
  getWarningList,
  getActivityEffect,
  getKnowledgeEffect
} from '@/api/admin/statistics'

const overview = ref({})
const warningList = ref([])
const activityEffect = ref({})
const knowledgeEffect = ref({})

const healthChartRef = ref(null)
const activityChartRef = ref(null)
const testChartRef = ref(null)
const healthTrendChartRef = ref(null)

let healthChart = null
let activityChart = null
let testChart = null
let healthTrendChart = null

// 获取概览数据
async function fetchOverview() {
  try {
    const res = await getOverview()
    overview.value = res.data
  } catch (error) {
    console.error(error)
  }
}

// 获取预警列表
async function fetchWarningList() {
  try {
    const res = await getWarningList()
    warningList.value = res.data || []
  } catch (error) {
    console.error(error)
  }
}

// 获取活动效果
async function fetchActivityEffect() {
  try {
    const res = await getActivityEffect()
    activityEffect.value = res.data || {}
  } catch (error) {
    console.error(error)
  }
}

// 获取科普效果
async function fetchKnowledgeEffect() {
  try {
    const res = await getKnowledgeEffect()
    knowledgeEffect.value = res.data || {}
  } catch (error) {
    console.error(error)
  }
}

// 处理预警
function handleWarning(row) {
  ElMessage.success('已标记为已处理')
  // 这里可以添加处理逻辑
  fetchWarningList()
}

// 初始化健康趋势图表
async function initHealthTrendChart() {
  if (!healthTrendChartRef.value) return

  healthTrendChart = echarts.init(healthTrendChartRef.value)

  try {
    const res = await getHealthTrend()
    const data = res.data

    healthTrendChart.setOption({
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['测试总数', '建议沟通'],
        top: 0
      },
      grid: {
        left: '50px',
        right: '30px',
        bottom: '30px',
        top: '40px',
        containLabel: false
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: data.dates || []
      },
      yAxis: {
        type: 'value',
        name: '人数',
        minInterval: 1
      },
      series: [
        {
          name: '测试总数',
          type: 'line',
          smooth: true,
          data: data.testCounts || [],
          itemStyle: { color: '#5DADE2' },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(93, 173, 226, 0.3)' },
              { offset: 1, color: 'rgba(93, 173, 226, 0.05)' }
            ])
          }
        },
        {
          name: '建议沟通',
          type: 'line',
          smooth: true,
          data: data.needTalkCounts || [],
          itemStyle: { color: '#EC7063' },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(236, 112, 99, 0.3)' },
              { offset: 1, color: 'rgba(236, 112, 99, 0.05)' }
            ])
          }
        }
      ]
    })
  } catch (error) {
    console.error('获取健康趋势数据失败:', error)
  }
}

// 初始化健康状态图表
async function initHealthChart() {
  if (!healthChartRef.value) return

  healthChart = echarts.init(healthChartRef.value)

  try {
    const res = await getHealthStatistics()
    const data = res.data

    const total = (data.healthCount || 0) + (data.goodCount || 0) + (data.needTalkCount || 0)
    if (total === 0) {
      healthChart.setOption({
        title: {
          text: '暂无数据',
          left: 'center',
          top: 'center',
          textStyle: { color: '#999', fontSize: 14 }
        }
      })
      return
    }

    healthChart.setOption({
      tooltip: {
        trigger: 'item',
        formatter: '{b}: {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        right: '10%',
        top: 'center'
      },
      color: ['#5DADE2', '#F8B739', '#EC7063'],
      series: [{
        name: '健康状态',
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['40%', '50%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: { show: false },
        emphasis: {
          label: { show: true, fontSize: 18, fontWeight: 'bold' }
        },
        data: [
          { value: data.healthCount || 0, name: '健康' },
          { value: data.goodCount || 0, name: '良好' },
          { value: data.needTalkCount || 0, name: '建议沟通' }
        ]
      }]
    })
  } catch (error) {
    console.error('获取健康统计数据失败:', error)
  }
}

// 初始化活动图表
async function initActivityChart() {
  if (!activityChartRef.value) return

  activityChart = echarts.init(activityChartRef.value)

  try {
    const res = await getActivityStatistics()
    const data = res.data

    if (!data.activityNames || data.activityNames.length === 0) {
      activityChart.setOption({
        title: {
          text: '暂无数据',
          left: 'center',
          top: 'center',
          textStyle: { color: '#999', fontSize: 14 }
        }
      })
      return
    }

    activityChart.setOption({
      tooltip: {
        trigger: 'axis',
        axisPointer: { type: 'shadow' }
      },
      grid: {
        left: '50px',
        right: '30px',
        bottom: '80px',
        top: '30px',
        containLabel: false
      },
      xAxis: {
        type: 'category',
        data: data.activityNames || [],
        axisLabel: {
          rotate: 45,
          interval: 0,
          fontSize: 11,
          formatter: (value) => value.length > 8 ? value.substring(0, 8) + '...' : value
        }
      },
      yAxis: {
        type: 'value',
        name: '人数',
        minInterval: 1
      },
      series: [{
        name: '报名人数',
        type: 'bar',
        barWidth: '50%',
        data: data.registrationCounts || [],
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#5DADE2' },
            { offset: 1, color: '#85C1E9' }
          ]),
          borderRadius: [6, 6, 0, 0]
        },
        label: { show: true, position: 'top' }
      }]
    })
  } catch (error) {
    console.error('获取活动统计数据失败:', error)
  }
}

// 初始化测试图表
async function initTestChart() {
  if (!testChartRef.value) return

  testChart = echarts.init(testChartRef.value)

  try {
    const res = await getTestStatistics()
    const data = res.data

    if (!data.counts || data.counts.every(count => count === 0)) {
      testChart.setOption({
        title: {
          text: '暂无数据',
          left: 'center',
          top: 'center',
          textStyle: { color: '#999', fontSize: 14 }
        }
      })
      return
    }

    testChart.setOption({
      tooltip: { trigger: 'axis' },
      grid: {
        left: '50px',
        right: '30px',
        bottom: '30px',
        top: '40px',
        containLabel: false
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: data.scoreRanges || ['0-20', '20-40', '40-60', '60-80', '80-100']
      },
      yAxis: {
        type: 'value',
        name: '人数',
        minInterval: 1
      },
      series: [{
        name: '测试人数',
        type: 'line',
        smooth: true,
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(93, 173, 226, 0.4)' },
            { offset: 1, color: 'rgba(93, 173, 226, 0.1)' }
          ])
        },
        itemStyle: { color: '#5DADE2' },
        lineStyle: { width: 3 },
        data: data.counts || [0, 0, 0, 0, 0]
      }]
    })
  } catch (error) {
    console.error('获取测试统计数据失败:', error)
  }
}

// 处理窗口大小变化
function handleResize() {
  healthChart?.resize()
  activityChart?.resize()
  testChart?.resize()
  healthTrendChart?.resize()
}

onMounted(() => {
  fetchOverview()
  fetchWarningList()
  fetchActivityEffect()
  fetchKnowledgeEffect()
  initHealthTrendChart()
  initHealthChart()
  initActivityChart()
  initTestChart()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  healthChart?.dispose()
  activityChart?.dispose()
  testChart?.dispose()
  healthTrendChart?.dispose()
})
</script>

<style lang="scss" scoped>
.action-buttons {
  display: flex;
  gap: $spacing-sm;
  flex-wrap: wrap;
}

.statistics-page {
  .overview-cards {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: $spacing-md;
    margin-bottom: $spacing-lg;

    .stat-card {
      background: $bg-white;
      border-radius: $border-radius-base;
      padding: $spacing-lg;
      display: flex;
      align-items: center;
      gap: $spacing-md;
      box-shadow: $shadow-light;
      transition: $transition-base;

      &:hover {
        box-shadow: $shadow-medium;
        transform: translateY(-2px);
      }

      .stat-icon {
        width: 60px;
        height: 60px;
        border-radius: $border-radius-base;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: color-mix(in srgb, var(--card-color) 10%, transparent);

        .svg-icon {
          color: var(--card-color);
        }
      }

      .stat-info {
        .stat-value {
          font-size: 28px;
          font-weight: 700;
          color: $text-primary;
        }

        .stat-label {
          color: $text-secondary;
          margin-top: $spacing-xs;
        }
      }
    }
  }

  .charts-row {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: $spacing-lg;
    margin-bottom: $spacing-lg;
  }

  .chart-card {
    background: $bg-white;
    border-radius: $border-radius-base;
    padding: $spacing-lg;
    box-shadow: $shadow-light;

    &.full-width {
      width: 100%;
      margin-bottom: $spacing-lg;
    }

    .chart-title {
      display: flex;
      align-items: center;
      gap: $spacing-sm;
      font-size: $font-size-large;
      font-weight: 600;
      color: $text-primary;
      margin-bottom: $spacing-md;
      padding-bottom: $spacing-md;
      border-bottom: 1px solid $border-lighter;
    }

    .chart-container {
      height: 300px;
    }

    .effect-stats {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: $spacing-md;
      margin-bottom: $spacing-lg;

      .effect-item {
        text-align: center;
        padding: $spacing-md;
        background: $bg-color;
        border-radius: $border-radius-base;

        .effect-label {
          font-size: $font-size-small;
          color: $text-secondary;
          margin-bottom: $spacing-xs;
        }

        .effect-value {
          font-size: $font-size-xxl;
          font-weight: 700;
          color: $primary-color;
        }
      }
    }

    .top-list {
      .top-item {
        display: flex;
        align-items: center;
        padding: $spacing-sm $spacing-md;
        border-radius: $border-radius-small;
        margin-bottom: $spacing-xs;
        transition: background 0.2s;

        &:hover {
          background: $bg-color;
        }

        .top-rank {
          width: 24px;
          height: 24px;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: $font-size-small;
          font-weight: 600;
          margin-right: $spacing-sm;
          background: $bg-color;
          color: $text-secondary;

          &.rank-1 {
            background: #FFD700;
            color: #fff;
          }

          &.rank-2 {
            background: #C0C0C0;
            color: #fff;
          }

          &.rank-3 {
            background: #CD7F32;
            color: #fff;
          }
        }

        .top-name {
          flex: 1;
          font-size: $font-size-base;
          color: $text-primary;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        .top-value {
          font-size: $font-size-small;
          color: $text-secondary;
          font-weight: 600;
        }
      }
    }
  }
}
</style>
