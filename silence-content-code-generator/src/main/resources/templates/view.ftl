package ${packageName};

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

<#if hasInstantType>import java.time.Instant;
</#if>
<#if hasBigDecimalType>import java.math.BigDecimal;
</#if>
<#if hasBigIntegerType>import java.math.BigInteger;
</#if>
<#-- Imports for relation views when foreign keys exist -->
<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>
import java.util.List;
</#if>

/**
* ${className}视图接口
*/
@ProjectedPayload
public interface ${className}View extends AuditableView {
    BigInteger getId();

<#list columnInfos as column>
<#if !column.primaryKey>
    ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}();

</#if>
</#list>

<#-- 关联视图 -->
<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>
<#list tableInfo.foreignKeys as fk>
    <#assign refViewName = toCamelCase(fk.referencedTable, true) + "View">
    List<${refViewName}> get${toCamelCase(fk.referencedTable, true)}List();

</#list>
</#if>
}