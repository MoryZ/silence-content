// view.ftl
package ${packageName};

import org.springframework.data.web.ProjectedPayload;
import ${basePackage}.domain.enums.${className}Status;
import ${basePackage}.commons.domain.AuditableView;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
* ${className}视图接口
*/
@ProjectedPayload
public interface ${className}View extends AuditableView {
BigInteger getId();

<#list tableInfo.columns as column>
    <#if !column.primaryKey>
        ${getJavaType(column)} get${toCamelCase(column.name, true)}();
    </#if>
</#list>

<#-- 关联视图 -->
<#list tableInfo.foreignKeys as fk>
    <#assign refViewName = toCamelCase(fk.referencedTable, true) + "View">
    List<${refViewName}> get${toCamelCase(fk.referencedTable, true)}List();
</#list>
}