<template>
  <div class="rich-text-editor">
    <div class="editor-toolbar">
      <button type="button" @click="execCommand('bold')" title="加粗"><svg-icon name="bold" :size="16" /></button>
      <button type="button" @click="execCommand('italic')" title="斜体"><svg-icon name="italic" :size="16" /></button>
      <button type="button" @click="execCommand('underline')" title="下划线"><svg-icon name="underline" :size="16" /></button>
      <span class="divider"></span>
      <button type="button" @click="execCommand('insertUnorderedList')" title="无序列表"><svg-icon name="list" :size="16" /></button>
      <button type="button" @click="execCommand('insertOrderedList')" title="有序列表"><svg-icon name="list-ol" :size="16" /></button>
      <span class="divider"></span>
      <button type="button" @click="execCommand('justifyLeft')" title="左对齐"><svg-icon name="align-left" :size="16" /></button>
      <button type="button" @click="execCommand('justifyCenter')" title="居中"><svg-icon name="align-center" :size="16" /></button>
      <button type="button" @click="execCommand('justifyRight')" title="右对齐"><svg-icon name="align-right" :size="16" /></button>
    </div>
    <div
      ref="editorRef"
      class="editor-content"
      contenteditable="true"
      :style="{ minHeight: minHeight + 'px' }"
      @input="handleInput"
      @blur="handleBlur"
      v-html="content"
    ></div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'

const props = defineProps({
  modelValue: { type: String, default: '' },
  minHeight: { type: Number, default: 300 },
  placeholder: { type: String, default: '请输入内容...' }
})

const emit = defineEmits(['update:modelValue'])

const editorRef = ref(null)
const content = ref(props.modelValue)

watch(() => props.modelValue, (val) => {
  if (val !== editorRef.value?.innerHTML) {
    content.value = val
  }
})

function execCommand(command, value = null) {
  document.execCommand(command, false, value)
  editorRef.value?.focus()
}

function handleInput() {
  emit('update:modelValue', editorRef.value?.innerHTML || '')
}

function handleBlur() {
  emit('update:modelValue', editorRef.value?.innerHTML || '')
}

onMounted(() => {
  if (props.modelValue) {
    content.value = props.modelValue
  }
})
</script>

<style lang="scss" scoped>
.rich-text-editor {
  border: 1px solid $border-color;
  border-radius: $border-radius-base;
  overflow: hidden;

  .editor-toolbar {
    display: flex;
    align-items: center;
    gap: 4px;
    padding: $spacing-sm;
    background: $bg-color;
    border-bottom: 1px solid $border-color;

    button {
      width: 32px;
      height: 32px;
      border: none;
      background: transparent;
      border-radius: $border-radius-small;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      color: $text-regular;
      transition: all 0.2s;

      &:hover {
        background: $bg-white;
        color: $primary-color;
      }
    }

    .divider {
      width: 1px;
      height: 20px;
      background: $border-color;
      margin: 0 4px;
    }
  }

  .editor-content {
    padding: $spacing-md;
    background: $bg-white;
    outline: none;
    line-height: 1.8;
    color: $text-primary;

    &:empty:before {
      content: attr(data-placeholder);
      color: $text-placeholder;
    }

    &:focus {
      background: #fff;
    }
  }
}
</style>
