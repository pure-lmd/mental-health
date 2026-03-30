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
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getOverview, getHealthStatistics, getActivityStatistics, getTestStatistics } from '@/api/admin/statistics'

const overview = ref({})
const healthChartRef = ref(null)
const activityChartRef = ref(null)
const testChartRef = ref(null)

let healthChart = null
let activityChart = null
let testChart = null

// 获取概览数据
async function fetchOverview() {
  try {
    const res = await getOverview()
    overview.value = res.data
  } catch (error) {
    console.error(error)
  }
}

// 初始化健康状态图表
async function initHealthChart() {
  if (!healthChartRef.value) return
  
  healthChart = echarts.init(healthChartRef.value)
  
  try {
    const res = await getHealthStatistics()
    const data = res.data
    
    // 如果没有数据，显示空状态
    const total = (data.healthCount || 0) + (data.goodCount || 0) + (data.needTalkCount || 0)
    if (total === 0) {
      healthChart.setOption({
        title: {
          text: '暂无数据',
          left: 'center',
          top: 'center',
          textStyle: {
            color: '#999',
            fontSize: 14
          }
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
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 18,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
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
    healthChart.setOption({
      title: {
        text: '数据加载失败',
        left: 'center',
        top: 'center',
        textStyle: {
          color: '#999',
          fontSize: 14
        }
      }
    })
  }
}

// 初始化活动图表
async function initActivityChart() {
  if (!activityChartRef.value) return
  
  activityChart = echarts.init(activityChartRef.value)
  
  try {
    const res = await getActivityStatistics()
    const data = res.data
    
    // 如果没有数据，显示空状态
    if (!data.activityNames || data.activityNames.length === 0) {
      activityChart.setOption({
        title: {
          text: '暂无数据',
          left: 'center',
          top: 'center',
          textStyle: {
            color: '#999',
            fontSize: 14
          }
        }
      })
      return
    }
    
    activityChart.setOption({
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        },
        formatter: '{b}<br/>报名人数: {c}'
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
          formatter: function(value) {
            // 限制标签长度，超过8个字符显示省略号
            if (value.length > 8) {
              return value.substring(0, 8) + '...'
            }
            return value
          }
        },
        axisTick: {
          alignWithLabel: true
        }
      },
      yAxis: {
        type: 'value',
        name: '人数',
        nameTextStyle: {
          fontSize: 12,
          padding: [0, 0, 0, 0]
        },
        minInterval: 1,
        axisLabel: {
          fontSize: 11
        }
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
        label: {
          show: true,
          position: 'top',
          fontSize: 11,
          color: '#333',
          fontWeight: 'bold'
        }
      }]
    })
  } catch (error) {
    console.error('获取活动统计数据失败:', error)
    activityChart.setOption({
      title: {
        text: '数据加载失败',
        left: 'center',
        top: 'center',
        textStyle: {
          color: '#999',
          fontSize: 14
        }
      }
    })
  }
}

// 初始化测试图表
async function initTestChart() {
  if (!testChartRef.value) return
  
  testChart = echarts.init(testChartRef.value)
  
  try {
    const res = await getTestStatistics()
    const data = res.data
    
    // 如果没有数据，显示空状态
    if (!data.counts || data.counts.every(count => count === 0)) {
      testChart.setOption({
        title: {
          text: '暂无数据',
          left: 'center',
          top: 'center',
          textStyle: {
            color: '#999',
            fontSize: 14
          }
        }
      })
      return
    }
    
    testChart.setOption({
      tooltip: {
        trigger: 'axis',
        formatter: '{b}<br/>测试人数: {c}'
      },
      legend: {
        data: ['测试人数'],
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
        data: data.scoreRanges || ['0-20', '20-40', '40-60', '60-80', '80-100'],
        axisLabel: {
          fontSize: 11
        }
      },
      yAxis: {
        type: 'value',
        name: '人数',
        nameTextStyle: {
          fontSize: 12,
          padding: [0, 0, 0, 0]
        },
        minInterval: 1,
        axisLabel: {
          fontSize: 11
        }
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
        itemStyle: {
          color: '#5DADE2'
        },
        lineStyle: {
          width: 3
        },
        data: data.counts || [0, 0, 0, 0, 0]
      }]
    })
  } catch (error) {
    console.error('获取测试统计数据失败:', error)
    testChart.setOption({
      title: {
        text: '数据加载失败',
        left: 'center',
        top: 'center',
        textStyle: {
          color: '#999',
          fontSize: 14
        }
      }
    })
  }
}

// 处理窗口大小变化
function handleResize() {
  healthChart?.resize()
  activityChart?.resize()
  testChart?.resize()
}

onMounted(() => {
  fetchOverview()
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
  }
}
</style>
