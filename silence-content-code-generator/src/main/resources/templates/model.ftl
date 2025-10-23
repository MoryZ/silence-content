package ${packageName};

import jakarta.persistence.Entity;
import ${basePackage}.data.commons.domain.AbstractAuditable;

<#if hasInstantType>import java.time.Instant;
</#if>
<#if hasBigDecimalType>import java.math.BigDecimal;
</#if>
<#if hasBigIntegerType>import java.math.BigInteger;
</#if>
@Entity
public class ${className} extends AbstractAuditable<${primaryType}> {
    <#list tableInfo.columns as column>
        <#if !column.primaryKey>
    private ${getJavaType(column)} ${toCamelCase(column.name, false)};
        </#if>
    </#list>

    <#-- 关联关系 -->
    <#list tableInfo.foreignKeys as fk>
        <#assign fieldName = toCamelCase(fk.referencedTable, false)>
        <#assign refClassName = toCamelCase(fk.referencedTable, true)>
    @OneToMany(mappedBy = "${toCamelCase(tableInfo.tableName, false)}")
    private List<${refClassName}> ${fieldName}List;
    </#list>

    <#-- Getter和Setter方法 -->
    <#list tableInfo.columns as column>
        <#if !column.primaryKey>
    public ${getJavaType(column)} get${toCamelCase(column.name, true)}() {
        return this.${toCamelCase(column.name, false)};
    }

    public void set${toCamelCase(column.name, true)}(${getJavaType(column)} ${toCamelCase(column.name, false)}) {
        this.${toCamelCase(column.name, false)} = ${toCamelCase(column.name, false)};
    }
        </#if>
    </#list>
}