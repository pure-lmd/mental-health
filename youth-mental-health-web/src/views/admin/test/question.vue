<template>
  <div class="question-manage">
    <div class="page-title"><svg-icon name="test" :size="24" /><span>题目管理 - {{ testInfo.title }}</span></div>
    <div class="search-form">
      <el-button @click="$router.back()"><svg-icon name="arrow-right" :size="14" style="transform: rotate(180deg)" /><span>返回</span></el-button>
      <el-button type="primary" @click="handleAdd"><svg-icon name="add" :size="14" /><span>添加题目</span></el-button>
    </div>
    <div class="table-container">
      <el-table :data="questionList" v-loading="loading" stripe>
        <el-table-column label="序号" type="index" width="80" />
        <el-table-column label="题目内容" prop="title" min-width="300" show-overflow-tooltip />
        <el-table-column label="选项数" width="100"><template #default="{ row }">{{ row.options?.length || 0 }}</template></el-table-column>
        <el-table-column label="排序" prop="sort" width="80" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)"><svg-icon name="edit" :size="14" /><span>编辑</span></el-button>
            <el-button type="danger" link @click="handleDelete(row)"><svg-icon name="delete" :size="14" /><span>删除</span></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px" destroy-on-close>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="题目内容" prop="title"><el-input v-model="formData.title" type="textarea" :rows="3" placeholder="请输入题目内容" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="formData.sort" :min="0" /></el-form-item>
        <el-form-item label="选项">
          <div class="options-list">
            <div v-for="(opt, index) in formData.options" :key="index" class="option-item">
              <el-input v-model="opt.label" placeholder="标签" style="width: 80px;" />
              <el-input v-model="opt.content" placeholder="选项内容" style="flex: 1;" />
              <el-input-number v-model="opt.score" :min="0" placeholder="分值" style="width: 120px;" />
              <el-button type="danger" circle @click="removeOption(index)"><svg-icon name="delete" :size="14" /></el-button>
            </div>
            <el-button type="primary" @click="addOption"><svg-icon name="add" :size="14" /><span>添加选项</span></el-button>
          </div>
        </el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getTestDetail, getTestQuestions, addQuestion, updateQuestion, deleteQuestion } from '@/api/admin/test'

const route = useRoute()
const testId = computed(() => route.params.id)
const loading = ref(false), questionList = ref([]), dialogVisible = ref(false), submitLoading = ref(false), formRef = ref(null)
const testInfo = ref({})
const formData = reactive({ id: null, testId: null, title: '', sort: 0, options: [] })
const formRules = { title: [{ required: true, message: '请输入题目内容', trigger: 'blur' }] }
const dialogTitle = computed(() => formData.id ? '编辑题目' : '添加题目')

async function fetchTestInfo() { try { const res = await getTestDetail(testId.value); testInfo.value = res.data } catch (e) { console.error(e) } }
async function fetchQuestions() { loading.value = true; try { const res = await getTestQuestions(testId.value); questionList.value = res.data } catch (e) { console.error(e) } finally { loading.value = false } }
function handleAdd() { Object.assign(formData, { id: null, testId: Number(testId.value), title: '', sort: questionList.value.length + 1, options: [{ label: 'A', content: '', score: 1 }] }); dialogVisible.value = true }
function handleEdit(row) { Object.assign(formData, { ...row, options: row.options?.length ? [...row.options] : [{ label: 'A', content: '', score: 1 }] }); dialogVisible.value = true }
function handleDelete(row) { ElMessageBox.confirm('确定要删除该题目吗？', '提示', { type: 'warning' }).then(async () => { await deleteQuestion(row.id); ElMessage.success('删除成功'); fetchQuestions() }).catch(() => {}) }
function addOption() { const labels = 'ABCDEFGHIJ'; formData.options.push({ label: labels[formData.options.length] || '', content: '', score: 1 }) }
function removeOption(index) { formData.options.splice(index, 1) }
async function handleSubmit() { const valid = await formRef.value.validate().catch(() => false); if (!valid) return; if (formData.options.length === 0) { ElMessage.warning('请至少添加一个选项'); return }; submitLoading.value = true; try { if (formData.id) { await updateQuestion(formData); ElMessage.success('更新成功') } else { await addQuestion(formData); ElMessage.success('添加成功') }; dialogVisible.value = false; fetchQuestions() } finally { submitLoading.value = false } }

onMounted(() => { fetchTestInfo(); fetchQuestions() })
</script>

<style lang="scss" scoped>
.question-manage { .search-form { display: flex; justify-content: space-between; gap: $spacing-md; }
  .options-list { width: 100%; .option-item { display: flex; align-items: center; gap: $spacing-sm; margin-bottom: $spacing-sm; } } }
</style>
