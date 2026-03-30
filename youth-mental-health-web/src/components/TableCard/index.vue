<template>
  <div class="table-card">
    <div class="table-header" v-if="$slots.header || title">
      <div class="header-left">
        <h3 v-if="title">{{ title }}</h3>
        <slot name="header"></slot>
      </div>
      <div class="header-right">
        <slot name="toolbar"></slot>
      </div>
    </div>
    <div class="table-content">
      <slot></slot>
    </div>
    <div class="table-footer" v-if="$slots.footer || showPagination">
      <slot name="footer">
        <Pagination
          v-if="showPagination"
          :total="total"
          :page="page"
          :limit="limit"
          @pagination="handlePagination"
        />
      </slot>
    </div>
  </div>
</template>

<script setup>
import Pagination from '@/components/Pagination/index.vue'

defineProps({
  title: { type: String, default: '' },
  showPagination: { type: Boolean, default: false },
  total: { type: Number, default: 0 },
  page: { type: Number, default: 1 },
  limit: { type: Number, default: 10 }
})

const emit = defineEmits(['pagination'])

function handlePagination(data) {
  emit('pagination', data)
}
</script>

<style lang="scss" scoped>
.table-card {
  background: $bg-white;
  border-radius: $border-radius-base;
  box-shadow: $shadow-light;
  overflow: hidden;

  .table-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: $spacing-md $spacing-lg;
    border-bottom: 1px solid $border-lighter;

    .header-left {
      display: flex;
      align-items: center;
      gap: $spacing-md;

      h3 {
        font-size: $font-size-medium;
        font-weight: 600;
        color: $text-primary;
        margin: 0;
      }
    }

    .header-right {
      display: flex;
      gap: $spacing-sm;
    }
  }

  .table-content {
    padding: $spacing-md;
  }

  .table-footer {
    border-top: 1px solid $border-lighter;
  }
}
</style>
