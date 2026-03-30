<template>
  <div class="my-collect-page">
    <div class="breadcrumb-wrapper"><el-breadcrumb><el-breadcrumb-item :to="{ path: '/front/home' }">首页</el-breadcrumb-item><el-breadcrumb-item>我的收藏</el-breadcrumb-item></el-breadcrumb></div>
    
    <div class="collect-wrapper" v-loading="loading">
      <div class="collect-list" v-if="list.length">
        <div v-for="item in list" :key="item.id" class="collect-item">
          <div class="item-info" @click="goDetail(item)">
            <div class="cover" :style="{ backgroundImage: item.targetCover ? `url(${item.targetCover})` : 'none' }"></div>
            <div class="content">
              <h4>{{ item.targetTitle || '未知内容' }}</h4>
              <span class="type-tag"><el-tag size="small">{{ getTypeName(item.type) }}</el-tag></span>
            </div>
          </div>
          <div class="item-action">
            <el-button type="danger" link @click="handleCancel(item)"><svg-icon name="delete" :size="14" /> 取消收藏</el-button>
          </div>
        </div>
      </div>
      <el-empty v-else description="暂无收藏内容" />
    </div>
    <div class="pagination-center" v-if="total > 0"><el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize" :total="total" layout="prev, pager, next" @current-change="fetchList" /></div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyCollect, cancelCollect } from '@/api/front/collect'

const router = useRouter()
const loading = ref(false), list = ref([]), total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10 })

const typeMap = { 1: '心理科普', 2: '心理宣传', 3: '文化资讯' }
function getTypeName(type) { return typeMap[type] || type }

function goDetail(item) {
  const routes = { 1: '/front/knowledge/', 2: '/front/publicity/', 3: '/front/news/' }
  if (routes[item.type]) router.push(routes[item.type] + item.targetId)
}

async function fetchList() { loading.value = true; try { const res = await getMyCollect(queryParams); list.value = res.data.records || []; total.value = res.data.total } catch (e) { console.error(e) } finally { loading.value = false } }

async function handleCancel(item) {
  await ElMessageBox.confirm('确认取消收藏？', '提示', { type: 'warning' })
  try { await cancelCollect({ targetId: item.targetId, type: item.type }); ElMessage.success('已取消收藏'); fetchList() } catch (e) { console.error(e) }
}

onMounted(() => { fetchList() })
</script>

<style lang="scss" scoped>
.my-collect-page { .collect-wrapper { background: $bg-white; border-radius: $border-radius-base; box-shadow: $shadow-light; min-height: 300px; }
  .collect-list { .collect-item { display: flex; justify-content: space-between; align-items: center; padding: $spacing-md $spacing-lg; border-bottom: 1px solid $border-lighter; &:last-child { border-bottom: none; }
    .item-info { display: flex; gap: $spacing-md; cursor: pointer; flex: 1;
      .cover { width: 120px; height: 80px; border-radius: $border-radius-small; background-size: cover; background-position: center; background-color: $bg-color; flex-shrink: 0; }
      .content { h4 { font-size: $font-size-medium; color: $text-primary; margin-bottom: $spacing-xs; } p { font-size: $font-size-small; color: $text-secondary; margin-bottom: $spacing-xs; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; } } } } } }
</style>
