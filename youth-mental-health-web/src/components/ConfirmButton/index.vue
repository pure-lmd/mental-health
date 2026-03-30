<template>
  <el-popconfirm
    :title="title"
    :confirm-button-text="confirmText"
    :cancel-button-text="cancelText"
    @confirm="handleConfirm"
  >
    <template #reference>
      <el-button :type="type" :link="link" :size="size" :loading="loading">
        <svg-icon v-if="icon" :name="icon" :size="14" />
        <span v-if="$slots.default"><slot></slot></span>
      </el-button>
    </template>
  </el-popconfirm>
</template>

<script setup>
defineProps({
  title: { type: String, default: '确认执行此操作？' },
  confirmText: { type: String, default: '确定' },
  cancelText: { type: String, default: '取消' },
  type: { type: String, default: 'danger' },
  link: { type: Boolean, default: true },
  size: { type: String, default: 'default' },
  icon: { type: String, default: 'delete' },
  loading: { type: Boolean, default: false }
})

const emit = defineEmits(['confirm'])

function handleConfirm() {
  emit('confirm')
}
</script>
