package ${packageName};

<#if hasNotBlank>import jakarta.validation.constraints.NotBlank;</#if>
<#if hasSize>import jakarta.validation.constraints.Size;</#if>
<#if hasNotNull>import jakarta.validation.constraints.NotNull;</#if>
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
<#list columnInfos as column>
    <#if !column.nullable>
        <#if  getJavaType(column) == "String">
    @NotBlank
    @Size(max = ${column.length?c})
    <#elseif isCollectionType(column)>
        @NotEmpty
    <#elseif isNumericType(column) || isBooleanType(column) || isInstantType(column)>    @NotNull
    </#if>
    </#if>
    private ${getJavaType(column)} ${column.fieldName};
</#list>

<#-- Getter和Setter方法 -->
<#list columnInfos as column>
    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}() {
        return this.${toCamelCase(column.fieldName, false)};
    }

    public void set${toCamelCase(column.fieldName, true)}(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}) {
        this.${toCamelCase(column.fieldName, false)} = ${toCamelCase(column.fieldName, false)};
    }
</#list>
}