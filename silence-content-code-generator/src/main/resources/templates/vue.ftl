<#-- Vue 页面模板（SFC） -->
<!-- ${entityLabel}管理页面 -->
<template>
    <div class="${camelName}-page">
        <div class="${camelName}-card">
            <div class="card-header">
                <h2 class="card-title">${entityLabel}管理</h2>
            </div>

            <SearchPanel
                    :model-value="searchParams"
                    :fields="searchFields"
                    @search="handleSearch"
                    @reset="resetSearch"
                    @update:model-value="handleSearchFormUpdate"
            />

            <div class="table-toolbar">
                <a-button type="primary" class="action-button" @click="showCreateModal = true">
                    <plus-outlined />
                    新增${entityLabel}
                </a-button>
                <a-button class="action-button" @click="fetchItems">刷新</a-button>
                <ColumnSettings :columns="allColumns" v-model="checkedKeys" @update:columns="val => allColumns = val as any" />
            </div>

            <div class="table-container">
                <CommonPagination
                        :columns="displayColumns"
                        :dataSource="items"
                        :loading="loading"
                        rowKey="id"
                        v-model:pageNo="pagination.current"
                        v-model:pageSize="pagination.pageSize"
                        :total="pagination.total"
                        @change="handlePaginationChange"
                        @cell-click="handleCellClick"
                >
                    <template #bodyCell="{ column, record }">
                        <#list columns as col>
                        <#-- 查找字段类型是否为布尔类型 -->
                            <#assign fieldType = ''>
                            <#list fields as f>
                                <#if f.name == col.key>
                                    <#assign fieldType = f.type?lower_case>
                                </#if>
                            </#list>
                            <#if fieldType == 'boolean' || fieldType == 'bool'>
                                <template v-if="column.key === '${col.key}'">
                                    <a-tag :color="record.${col.key} ? 'success' : 'error'">
                                        {{ record.${col.key} ? '是' : '否' }}
                                    </a-tag>
                                </template>
                            </#if>
                        </#list>
                        <template v-else-if="column.key === 'action'">
                            <a-space>
                                <a-button type="link" size="small" @click="handleEdit(record)">编辑</a-button>
                                <a-popconfirm title="确定要删除这个${entityLabel}吗？" @confirm="handleDelete(record.id)">
                                    <a-button type="link" size="small" danger>删除</a-button>
                                </a-popconfirm>
                            </a-space>
                        </template>
                    </template>
                </CommonPagination>
            </div>
        </div>

        <DetailDrawer v-model:visible="drawerVisible" :record="selectedRecord" :columns="detailColumns" title="${entityLabel}详情" />

        <a-modal v-model:open="showCreateModal" :title="modalTitle" ok-text="确定" cancel-text="取消" @ok="handleModalOk" :confirmLoading="createLoading" @cancel="handleModalCancel">
            <a-form :model="formData" :rules="rules" ref="formRef" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
                <#list formFields?default(fields) as f>
                    <a-form-item label="${f.label! f.name}" name="${f.name}">
                        <#if f.type?lower_case == 'boolean' || f.type?lower_case == 'bool'>
                            <a-switch v-model:checked="formData.${f.name}" />
                        <#elseif f.type?lower_case == 'number'>
                            <a-input-number v-model:value="formData.${f.name}" :min="0" :precision="0" style="width: 100%;" placeholder="请输入${f.label! f.name}" />
                        <#else>
                            <a-input v-model:value="formData.${f.name}" placeholder="请输入${f.label! f.name}" />
                        </#if>
                    </a-form-item>
                </#list>
            </a-form>
        </a-modal>
    </div>
</template>

<script lang="ts" setup>
    import { ref, reactive, computed, onMounted } from 'vue'
    import { message } from 'ant-design-vue'
    import { PlusOutlined } from '@ant-design/icons-vue'
    import type { FormInstance } from 'ant-design-vue'
    import CommonPagination from '@/components/CommonPagination.vue'
    import SearchPanel from '@/components/SearchPanel.vue'
    import ColumnSettings from '@/components/ColumnSettings.vue'
    import DetailDrawer from '@/components/DetailDrawer.vue'
    import {
      get${typeName}List,
  create${typeName},
  update${typeName},
  delete${typeName},
  get${typeName}ById
} from '@/api/${camelName}'
import type { ${typeName}, ${queryParamsName} } from '@/types/${camelName}'
import type { PaginationResponse } from '@/types/common'

const searchParams = reactive({
    <#list searchFields as sf>
        ${sf.key}: <#if sf.paramType?lower_case == 'number'>undefined as number | undefined<#elseif sf.type == 'date'>undefined as [string, string] | undefined<#elseif sf.type == 'select'>undefined as string | number | undefined<#else>'' as string</#if><#if sf_has_next>,</#if>
    </#list>
    })

    const items = ref<${typeName}[]>([])
const loading = ref(false)
const pagination = reactive({ current: 1, pageSize: 10, total: 0 })

const allColumns = ref([
    <#list columns as col>
  {
    title: '${col.title}',
    dataIndex: '${col.dataIndex}',
    key: '${col.key}',
    width: '${col.width}',
    visible: true<#if col.clickable?? && col.clickable>, clickable: true</#if>
  }<#if col_has_next>,</#if>
    </#list>
    ])

    const checkedKeys = ref(allColumns.value.filter(c => c.visible).map(c => c.key))
    const displayColumns = computed(() => allColumns.value.filter(col => checkedKeys.value.includes(col.key)))

    const drawerVisible = ref(false)
    const selectedRecord = ref<${typeName} | null>(null)

const detailColumns = [
    <#list detailColumns as dc>
        ${dc}<#if dc_has_next>,</#if>
    </#list>
    ]

    const showCreateModal = ref(false)
    const createLoading = ref(false)
    const modalTitle = ref('新增${entityLabel}')
const formRef = ref<FormInstance>()
const formData = reactive({
  id: undefined as number | undefined,
    <#list formFields?default(fields) as f>
        ${f.name}: <#if f.type?lower_case == 'boolean' || f.type?lower_case == 'bool'>false<#elseif f.type?lower_case == 'number'>${(f.name?lower_case?contains('order') || f.name?lower_case?contains('sort'))?string('1','0')}<#else>''</#if><#if f_has_next>,</#if>
    </#list>
    })

    const rules: Record<string, any> = {
    <#list requiredFields?default([]) as rf>
        ${rf}: [{ required: true, message: '请输入${rf}', trigger: 'blur' }]<#if rf_has_next>,</#if>
    </#list>
    }

    const searchFields = [
    <#list searchFields as sf>
  {
    key: '${sf.key}',
    label: '${sf.label}',
    type: 'input' as const,
    placeholder: '${sf.placeholder}',
    style: 'width: 200px'
  }<#if sf_has_next>,</#if>
    </#list>
    ]

    const fetchItems = async () => {
      loading.value = true
      try {
        const params: ${queryParamsName} = { pageNo: pagination.current, pageSize: pagination.pageSize }
    <#list searchFields as sf>
        <#if sf.key == 'id'>
    if (searchParams.id) { params.id = searchParams.id }
    <#elseif sf.type == 'date'>
    if (searchParams.${sf.key} && Array.isArray(searchParams.${sf.key}) && searchParams.${sf.key}.length === 2) {
      params.${sf.key}Start = searchParams.${sf.key}[0]
      params.${sf.key}End = searchParams.${sf.key}[1]
    }
    <#else>
    if (searchParams.${sf.key}) { params.${sf.key} = searchParams.${sf.key} }
    </#if>
    </#list>
    const response = await get${typeName}List(params) as unknown as PaginationResponse<${typeName}>
    items.value = response.data || []
    pagination.total = response.total || 0
  } catch (error) {
    console.error('获取列表失败:', error)
    message.error('获取列表失败')
    items.value = []
  } finally {
    loading.value = false
  }
}

const handlePaginationChange = (pageNo: number, pageSize: number) => {
  pagination.current = pageNo
  pagination.pageSize = pageSize
  fetchItems()
}

const handleSearch = () => { pagination.current = 1; fetchItems() }

const resetSearch = () => {
    <#list searchFields as sf>
        <#if sf.paramType?lower_case == 'number' || sf.type == 'date' || sf.type == 'select'>
  searchParams.${sf.key} = undefined
  <#else>
  searchParams.${sf.key} = ''
  </#if>
    </#list>
    pagination.current = 1
    fetchItems()
  }

  const handleSearchFormUpdate = (newForm: any) => { Object.assign(searchParams, newForm) }

  const handleCellClick = async (record: ${typeName}, column: any) => {
  if (column.key === 'id') {
    try {
      const data = await get${typeName}ById(record.id) as unknown as ${typeName}
    selectedRecord.value = data
    drawerVisible.value = true
  } catch (error) {
    console.error('获取详情失败:', error)
    message.error('获取详情失败')
  }
}
}

const handleEdit = (record: ${typeName}) => {
  modalTitle.value = '编辑${entityLabel}'
  formData.id = record.id
    <#list formFields?default(fields) as f>
  formData.${f.name} = record.${f.name}<#if f.type?lower_case == 'string'> || ''</#if>
    </#list>
    showCreateModal.value = true
  }

  const handleDelete = async (id: number) => {
    try {
      await delete${typeName}(id)
    message.success('删除成功')
    fetchItems()
  } catch (error) {
    console.error('删除失败:', error)
    message.error('删除失败')
  }
}

const handleModalOk = async () => {
  try {
    await formRef.value?.validate()
    createLoading.value = true
    if (formData.id) {
      await update${typeName}(formData.id, {
    <#list formFields?default(fields) as f>
        ${f.name}: formData.${f.name}<#if f_has_next>,</#if>
    </#list>
    })
    message.success('修改成功')
  } else {
    await create${typeName>({
    <#list formFields?default(fields) as f>
    ${f.name}: formData.${f.name}<#if f_has_next>,</#if>
</#list>
    })
    message.success('新增成功')
  }
  showCreateModal.value = false
  resetForm()
  fetchItems()
} catch (error) {
  console.error('保存失败:', error)
  message.error(formData.id ? '修改失败' : '新增失败')
} finally {
  createLoading.value = false
}
}

const handleModalCancel = () => { showCreateModal.value = false; resetForm() }

const resetForm = () => {
formData.id = undefined
    <#list formFields?default(fields) as f>
  <#if f.type?lower_case == 'boolean' || f.type?lower_case == 'bool'>
    formData.${f.name} = false
    <#elseif f.type?lower_case == 'number'>
    formData.${f.name} = ${(f.name?lower_case?contains('order') || f.name?lower_case?contains('sort'))?string('1','0')}
  <#else>
    formData.${f.name} = ''
    </#if>
</#list>
    formRef.value?.resetFields()
  }

  onMounted(() => { fetchItems() })
</script>

<style scoped>
    .${camelName}-page { display: flex; flex-direction: column; background: #f5f7fa }
    .${camelName}-card { background: #fff; border-radius: 14px; box-shadow: 0 4px 24px rgba(0,0,0,0.08); margin: 32px 24px 0 24px; padding-bottom: 32px }
    .card-header { padding: 28px 40px 12px 40px; display: flex; justify-content: space-between; align-items: center }
    .card-title { font-size: 22px; font-weight: 700; color: #222; margin: 0 }
    .table-toolbar { display: flex; justify-content: flex-end; align-items: center; gap: 8px; min-height: 32px; margin-bottom: 8px; padding: 0 40px }
    .action-button { border-radius: 20px; font-size: 15px; padding: 0 22px; height: 38px }
    .table-container { padding: 24px 40px 0 40px; display: flex; flex-direction: column }
    :deep(.ant-table-thead > tr > th) { background: #f7faff; font-weight: 600; font-size: 15px }
</style>