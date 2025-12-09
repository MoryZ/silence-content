<#-- API 模板 -->
import request from '@/utils/request'
import type {
${typeName},
${queryParamsName},
${createParamsName},
${updateParamsName}
} from '@/types/${camelName}'
import type { PaginationResponse } from '@/types/common'

<#-- 列表接口 -->
<#if listRoute??>
    export function get${typeName}List(params: ${queryParamsName}) {
    return request.get<PaginationResponse<${typeName}>>('${apiBasePath}', { params })
    }
</#if>

<#-- 详情接口 -->
<#if detailRoute??>
    export function get${typeName}ById(id: number) {
    return request.get<${typeName}>(`${apiBasePath}/${'$'}{id}`)
    }
</#if>

<#-- 创建接口 -->
<#if createRoute??>
    export function create${typeName}(data: ${createParamsName}) {
    return request.post<${typeName}>('${apiBasePath}', data)
    }
</#if>

<#-- 更新接口 -->
<#if updateRoute??>
    export function update${typeName}(id: number, data: ${updateParamsName}) {
    return request.put<${typeName}>(`${apiBasePath}/${'$'}{id}`, data)
    }
</#if>

<#-- 删除接口 -->
<#if deleteRoute??>
export function delete${typeName}(id: number) {
return request.delete<void>(`${apiBasePath}/${'$'}{id}`)
    }
    </#if>
