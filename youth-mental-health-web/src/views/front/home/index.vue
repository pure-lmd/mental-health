<template>
  <div class="front-home">
    <!-- 轮播图 -->
    <div class="banner-section">
      <el-carousel height="400px" indicator-position="outside">
        <el-carousel-item v-for="item in bannerList" :key="item.id">
          <div 
            class="banner-item" 
            :style="{ backgroundImage: `url(${item.image})`, cursor: item.url ? 'pointer' : 'default' }"
            @click="handleBannerClick(item)"
          >
            <div class="banner-content">
              <h2>{{ item.title }}</h2>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>
    
    <!-- 快捷入口 -->
    <div class="quick-entry">
      <div class="entry-item" @click="$router.push('/front/knowledge')">
        <div class="entry-icon" style="background: linear-gradient(135deg, #36B37E, #57D9A3)"><svg-icon name="knowledge" :size="32" color="#fff" /></div>
        <span>心理科普</span>
      </div>
      <div class="entry-item" @click="$router.push('/front/test')">
        <div class="entry-icon" style="background: linear-gradient(135deg, #00B8D9, #4DC9F6)"><svg-icon name="test" :size="32" color="#fff" /></div>
        <span>心理测试</span>
      </div>
      <div class="entry-item" @click="$router.push('/front/activity')">
        <div class="entry-icon" style="background: linear-gradient(135deg, #FFAB00, #FFD740)"><svg-icon name="activity" :size="32" color="#fff" /></div>
        <span>心理活动</span>
      </div>
      <div class="entry-item" @click="$router.push('/front/ai-chat')">
        <div class="entry-icon" style="background: linear-gradient(135deg, #6554C0, #8777D9)"><svg-icon name="ai" :size="32" color="#fff" /></div>
        <span>AI助手</span>
      </div>
      <div class="entry-item" @click="$router.push('/front/feedback')">
        <div class="entry-icon" style="background: linear-gradient(135deg, #FF8B00, #FFC400)"><svg-icon name="feedback" :size="32" color="#fff" /></div>
        <span>意见反馈</span>
      </div>
      <div class="entry-item" @click="$router.push('/front/notice')">
        <div class="entry-icon" style="background: linear-gradient(135deg, #FF5630, #FF8F73)"><svg-icon name="notice" :size="32" color="#fff" /></div>
        <span>通知公告</span>
      </div>
    </div>
    
    <!-- 热门内容 -->
    <div class="content-sections">
      <!-- 热门科普 -->
      <div class="section">
        <div class="section-header">
          <h3><svg-icon name="knowledge" :size="20" /><span>热门科普</span></h3>
          <router-link to="/front/knowledge" class="more-link">查看更多<svg-icon name="arrow-right" :size="14" /></router-link>
        </div>
        <div class="knowledge-grid">
          <div v-for="item in hotKnowledge" :key="item.id" class="knowledge-card" @click="$router.push(`/front/knowledge/${item.id}`)">
            <div class="cover" :style="{ backgroundImage: `url(${item.cover})` }"></div>
            <div class="info">
              <h4>{{ item.title }}</h4>
              <p>{{ item.summary }}</p>
              <div class="meta"><span><svg-icon name="view" :size="12" /> {{ item.viewCount || 0 }}</span><span><svg-icon name="collect" :size="12" /> {{ item.collectCount || 0 }}</span></div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 热门活动 -->
      <div class="section">
        <div class="section-header">
          <h3><svg-icon name="activity" :size="20" /><span>热门活动</span></h3>
          <router-link to="/front/activity" class="more-link">查看更多<svg-icon name="arrow-right" :size="14" /></router-link>
        </div>
        <div class="activity-grid">
          <div v-for="item in hotActivity" :key="item.id" class="activity-card" @click="$router.push(`/front/activity/${item.id}`)">
            <div class="cover" :style="{ backgroundImage: `url(${item.cover})` }">
              <el-tag :type="getActivityStatusType(item.status)" class="status-tag">{{ getActivityStatusText(item.status) }}</el-tag>
            </div>
            <div class="info">
              <h4>{{ item.title }}</h4>
              <p class="desc">{{ item.description }}</p>
              <div class="meta"><svg-icon name="location" :size="12" /> {{ item.address }}</div>
              <div class="meta"><svg-icon name="time" :size="12" /> {{ formatDateTime(item.startTime) }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getBannerList, getHotKnowledge, getHotActivity } from '@/api/front/home'
import { formatDateTime, getActivityStatusText, getActivityStatusType } from '@/utils/common'

const router = useRouter()
const bannerList = ref([])
const hotKnowledge = ref([])
const hotActivity = ref([])

function handleBannerClick(item) {
  if (!item.url) return
  // 判断是内部链接还是外部链接
  if (item.url.startsWith('http://') || item.url.startsWith('https://')) {
    window.open(item.url, '_blank')
  } else {
    router.push(item.url)
  }
}

async function fetchData() {
  try {
    const [bannerRes, knowledgeRes, activityRes] = await Promise.all([
      getBannerList(),
      getHotKnowledge(6),
      getHotActivity(4)
    ])
    bannerList.value = bannerRes.data || []
    hotKnowledge.value = knowledgeRes.data || []
    hotActivity.value = activityRes.data || []
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style lang="scss" scoped>
.front-home {
  .banner-section {
    margin-bottom: $spacing-lg;
    border-radius: $border-radius-large;
    overflow: hidden;
    
    .banner-item {
      height: 100%;
      background-size: cover;
      background-position: center;
      display: flex;
      align-items: flex-end;
      
      .banner-content {
        width: 100%;
        padding: $spacing-xl;
        background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
        
        h2 { color: #fff; font-size: $font-size-xxl; }
      }
    }
  }
  
  .quick-entry {
    display: grid;
    grid-template-columns: repeat(6, 1fr);
    gap: $spacing-md;
    margin-bottom: $spacing-xl;
    
    .entry-item {
      background: $bg-white;
      border-radius: $border-radius-base;
      padding: $spacing-lg;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: $spacing-sm;
      cursor: pointer;
      transition: $transition-base;
      box-shadow: $shadow-light;
      
      &:hover {
        transform: translateY(-4px);
        box-shadow: $shadow-medium;
      }
      
      .entry-icon {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      
      span { font-weight: 500; color: $text-primary; }
    }
  }
  
  .section {
    background: $bg-white;
    border-radius: $border-radius-base;
    padding: $spacing-lg;
    margin-bottom: $spacing-lg;
    box-shadow: $shadow-light;
    
    .section-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: $spacing-lg;
      padding-bottom: $spacing-md;
      border-bottom: 1px solid $border-lighter;
      
      h3 {
        display: flex;
        align-items: center;
        gap: $spacing-sm;
        font-size: $font-size-large;
        color: $text-primary;
      }
      
      .more-link {
        display: flex;
        align-items: center;
        gap: 4px;
        color: $text-secondary;
        &:hover { color: $primary-color; }
      }
    }
  }
  
  .knowledge-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: $spacing-md;
    
    .knowledge-card {
      border: 1px solid $border-lighter;
      border-radius: $border-radius-base;
      overflow: hidden;
      cursor: pointer;
      transition: $transition-base;
      
      &:hover {
        box-shadow: $shadow-medium;
        transform: translateY(-2px);
      }
      
      .cover {
        height: 140px;
        background-size: cover;
        background-position: center;
        background-color: $bg-color;
      }
      
      .info {
        padding: $spacing-md;
        
        h4 {
          font-size: $font-size-medium;
          color: $text-primary;
          margin-bottom: $spacing-sm;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        
        p {
          font-size: $font-size-small;
          color: $text-secondary;
          line-height: 1.6;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          overflow: hidden;
          margin-bottom: $spacing-sm;
        }
        
        .meta {
          display: flex;
          gap: $spacing-md;
          color: $text-placeholder;
          font-size: $font-size-small;
          
          span { display: flex; align-items: center; gap: 4px; }
        }
      }
    }
  }
  
  .activity-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: $spacing-md;
    
    .activity-card {
      display: flex;
      border: 1px solid $border-lighter;
      border-radius: $border-radius-base;
      overflow: hidden;
      cursor: pointer;
      transition: $transition-base;
      
      &:hover {
        box-shadow: $shadow-medium;
      }
      
      .cover {
        width: 200px;
        height: 150px;
        background-size: cover;
        background-position: center;
        background-color: $bg-color;
        flex-shrink: 0;
        position: relative;
        
        .status-tag { position: absolute; top: $spacing-sm; left: $spacing-sm; }
      }
      
      .info {
        flex: 1;
        padding: $spacing-md;
        
        h4 {
          font-size: $font-size-medium;
          color: $text-primary;
          margin-bottom: $spacing-sm;
        }
        
        .desc {
          font-size: $font-size-small;
          color: $text-secondary;
          margin-bottom: $spacing-sm;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        
        .meta {
          display: flex;
          align-items: center;
          gap: $spacing-sm;
          color: $text-placeholder;
          font-size: $font-size-small;
          margin-top: $spacing-xs;
        }
      }
    }
  }
}
</style>
