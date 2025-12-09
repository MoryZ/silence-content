<#-- TypeScript 类型定义模板 -->
// ${typeName} 实体类型
export interface ${typeName} {
<#list fields as f>
    ${f.name}<#if f.optional?? && f.optional>? </#if>: ${f.type!'any'}
</#list>
}

// ${typeName} 查询参数类型
export interface ${queryParamsName} {
pageNo: number
pageSize: number
<#list queryParams?default([]) as p>
    ${p.name}?: ${p.type}
</#list>
}

// 创建${typeName}参数类型
export interface ${createParamsName} {
<#list createFields?default(fields) as f>
    ${f.name}: ${f.type!'any'}
</#list>
}

// 更新${typeName}参数类型
export type ${updateParamsName} = Partial<${createParamsName}>