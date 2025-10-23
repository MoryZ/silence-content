// command.ftl
package ${packageName};

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ${basePackage}.domain.enums.${className}Status;

import java.math.BigDecimal;
import java.time.Instant;

/**
* ${className}命令对象
*/
public class ${className}Command {
<#list tableInfo.columns as column>
    <#if !column.primaryKey && column.name != "id">
        <#if column.name == "name">
            @NotBlank
            @Size(max = 100)
        </#if>
        private ${getJavaType(column)} ${toCamelCase(column.name, false)};
    </#if>
</#list>

<#-- Getter和Setter方法 -->
<#list tableInfo.columns as column>
    <#if !column.primaryKey && column.name != "id">
        public ${getJavaType(column)} get${toCamelCase(column.name, true)}() {
        return this.${toCamelCase(column.name, false)};
        }

        public void set${toCamelCase(column.name, true)}(${getJavaType(column)} ${toCamelCase(column.name, false)}) {
        this.${toCamelCase(column.name, false)} = ${toCamelCase(column.name, false)};
        }
    </#if>
</#list>
}