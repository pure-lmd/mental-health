<template>
  <div class="search-form">
    <el-form :inline="true" :model="modelValue" @submit.prevent="handleSearch">
      <slot></slot>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">
          <svg-icon name="search" :size="14" /> 搜索
        </el-button>
        <el-button @click="handleReset">
          <svg-icon name="refresh" :size="14" /> 重置
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
const props = defineProps({
  modelValue: { type: Object, required: true }
})

const emit = defineEmits(['search', 'reset'])

function handleSearch() {
  emit('search')
}

function handleReset() {
  emit('reset')
}
</script>

<style lang="scss" scoped>
.search-form {
  background: $bg-white;
  padding: $spacing-md;
  border-radius: $border-radius-base;
  margin-bottom: $spacing-md;
  box-shadow: $shadow-light;

  :deep(.el-form-item) {
    margin-bottom: 0;
    margin-right: $spacing-md;
  }
}
</style>
