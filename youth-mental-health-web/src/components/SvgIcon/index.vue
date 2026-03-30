<template>
  <svg
    class="svg-icon"
    :class="className"
    :style="{ width: size + 'px', height: size + 'px', color: color }"
    aria-hidden="true"
  >
    <use :xlink:href="symbolId" />
  </svg>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  name: {
    type: String,
    required: true
  },
  size: {
    type: [Number, String],
    default: 16
  },
  color: {
    type: String,
    default: 'currentColor'
  },
  className: {
    type: String,
    default: ''
  }
})

const symbolId = computed(() => `#icon-${props.name}`)
</script>

<style scoped>
.svg-icon {
  display: inline-block;
  vertical-align: -0.15em;
  overflow: hidden;
}
</style>
