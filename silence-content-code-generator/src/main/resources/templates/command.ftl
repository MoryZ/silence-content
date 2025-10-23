package ${packageName};

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
<#if hasInstantType>import java.time.Instant;
</#if>
<#if hasBigDecimalType>import java.math.BigDecimal;
</#if>
<#if hasBigIntegerType>import java.math.BigInteger;
</#if>
/**
* ${className}命令对象
*/
public class ${className}Command {
<#list tableInfo.columns as column>
    <#if !column.nullable>
        <#if  getJavaType(column) == "String">
    @NotBlank
    @Size(max = ${column.length})
    <#elseif isCollectionType>
        @NotEmpty
    <#elseif isNumericType(column) || isBooleanType(column) || isInstantType(column)>    @NotNull
    </#if>
    </#if>
    private ${getJavaType(column)} ${toCamelCase(column.name, false)};
</#list>

<#-- Getter和Setter方法 -->
<#list tableInfo.columns as column>
    public ${getJavaType(column)} get${toCamelCase(column.name, true)}() {
        return this.${toCamelCase(column.name, false)};
    }

    public void set${toCamelCase(column.name, true)}(${getJavaType(column)} ${toCamelCase(column.name, false)}) {
        this.${toCamelCase(column.name, false)} = ${toCamelCase(column.name, false)};
    }
</#list>
}