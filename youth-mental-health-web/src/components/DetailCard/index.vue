<template>
  <div class="detail-card">
    <div class="detail-header" v-if="title || $slots.extra">
      <h3>{{ title }}</h3>
      <div class="extra"><slot name="extra"></slot></div>
    </div>
    <div class="detail-content">
      <slot></slot>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: { type: String, default: '' }
})
</script>

<style lang="scss" scoped>
.detail-card {
  background: $bg-white;
  border-radius: $border-radius-base;
  box-shadow: $shadow-light;
  overflow: hidden;

  .detail-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: $spacing-md $spacing-lg;
    border-bottom: 1px solid $border-lighter;

    h3 {
      font-size: $font-size-medium;
      font-weight: 600;
      color: $text-primary;
      margin: 0;
    }
  }

  .detail-content {
    padding: $spacing-lg;
  }
}
</style>
