<template>
  <div class="image-upload">
    <el-upload
      :action="uploadUrl"
      :show-file-list="false"
      :on-success="handleSuccess"
      :on-error="handleError"
      :before-upload="beforeUpload"
      :headers="headers"
      accept="image/*"
    >
      <div class="upload-box" :style="{ width: width + 'px', height: height + 'px' }">
        <img v-if="modelValue" :src="modelValue" class="upload-image" />
        <div v-else class="upload-placeholder">
          <svg-icon name="upload" :size="32" />
          <span>点击上传</span>
        </div>
        <div v-if="modelValue" class="upload-mask">
          <svg-icon name="edit" :size="20" />
        </div>
      </div>
    </el-upload>
    <p class="upload-tip" v-if="tip">{{ tip }}</p>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useAdminStore } from '@/stores/admin'
import { useUserStore } from '@/stores/user'

const props = defineProps({
  modelValue: { type: String, default: '' },
  width: { type: Number, default: 150 },
  height: { type: Number, default: 150 },
  maxSize: { type: Number, default: 5 }, // MB
  tip: { type: String, default: '' }
})

const emit = defineEmits(['update:modelValue'])

const uploadUrl = '/api/common/upload'

const headers = computed(() => {
  const adminStore = useAdminStore()
  const userStore = useUserStore()
  const token = adminStore.token || userStore.token
  return token ? { Authorization: `Bearer ${token}` } : {}
})

function beforeUpload(file) {
  const isImage = file.type.startsWith('image/')
  const isLt = file.size / 1024 / 1024 < props.maxSize

  if (!isImage) {
    ElMessage.error('只能上传图片文件!')
    return false
  }
  if (!isLt) {
    ElMessage.error(`图片大小不能超过 ${props.maxSize}MB!`)
    return false
  }
  return true
}

function handleSuccess(response) {
  if (response.code === 200) {
    emit('update:modelValue', response.data)
    ElMessage.success('上传成功')
  } else {
    ElMessage.error(response.message || '上传失败')
  }
}

function handleError() {
  ElMessage.error('上传失败，请重试')
}
</script>

<style lang="scss" scoped>
.image-upload {
  .upload-box {
    position: relative;
    border: 1px dashed $border-color;
    border-radius: $border-radius-base;
    cursor: pointer;
    overflow: hidden;
    transition: border-color 0.3s;

    &:hover {
      border-color: $primary-color;
      .upload-mask { opacity: 1; }
    }

    .upload-image {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .upload-placeholder {
      width: 100%;
      height: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      color: $text-placeholder;
      background: $bg-color;

      span {
        margin-top: $spacing-sm;
        font-size: $font-size-small;
      }
    }

    .upload-mask {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.5);
      display: flex;
      align-items: center;
      justify-content: center;
      color: #fff;
      opacity: 0;
      transition: opacity 0.3s;
    }
  }

  .upload-tip {
    margin-top: $spacing-xs;
    font-size: $font-size-small;
    color: $text-placeholder;
  }
}
</style>
