// query.ftl
package ${packageName};

import org.springframework.data.repository.query.parser.Part;
import ${basePackage}.domain.enums.${className}Status;
import ${basePackage}.commons.annotation.RelationalQueryProperty;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

/**
* ${className}查询对象
*/
public class ${className}Query {
<#list tableInfo.columns as column>
    <#if isQueryableField(column)>
        <#if column.type?contains("varchar") || column.type?contains("char")>
            @RelationalQueryProperty(type = Part.Type.STARTING_WITH)
            private ${getJavaType(column)} ${toCamelCase(column.name, false)};
        <#elseif column.type?contains("int") || isEnumField(column)>
            @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)
            private ${getJavaType(column)} ${toCamelCase(column.name, false)};
        <#elseif column.type?contains("date") || column.type?contains("time")>
            @RelationalQueryProperty(name = "${column.name}", type = Part.Type.GREATER_THAN_EQUAL)
            private ${getJavaType(column)} ${toCamelCase(column.name, false)}Start;

            @RelationalQueryProperty(name = "${column.name}", type = Part.Type.LESS_THAN_EQUAL)
            private ${getJavaType(column)} ${toCamelCase(column.name, false)}End;
        </#if>
    </#if>
</#list>

<#-- 关联查询字段 -->
<#list tableInfo.foreignKeys as fk>
@RelationalQueryProperty(name = "${fk.columnName}.id", type = Part.Type.IN)
private List&lt;BigInteger> ${toCamelCase(fk.referencedTable, false)}Ids;
    </#list>

    <#-- Getter和Setter方法 -->
    <#list tableInfo.columns as column>
        <#if isQueryableField(column)>
            <#if column.type?contains("varchar") || column.type?contains("char") || column.type?contains("int") || isEnumField(column)>
                public ${getJavaType(column)} get${toCamelCase(column.name, true)}() {
                return this.${toCamelCase(column.name, false)};
                }

                public void set${toCamelCase(column.name, true)}(${getJavaType(column)} ${toCamelCase(column.name, false)}) {
                this.${toCamelCase(column.name, false)} = ${toCamelCase(column.name, false)};
                }
            <#elseif column.type?contains("date") || column.type?contains("time")>
                public ${getJavaType(column)} get${toCamelCase(column.name, true)}Start() {
                return this.${toCamelCase(column.name, false)}Start;
                }

                public void set${toCamelCase(column.name, true)}Start(${getJavaType(column)} ${toCamelCase(column.name, false)}Start) {
                this.${toCamelCase(column.name, false)}Start = ${toCamelCase(column.name, false)}Start;
                }

                public ${getJavaType(column)} get${toCamelCase(column.name, true)}End() {
                return this.${toCamelCase(column.name, false)}End;
                }

                public void set${toCamelCase(column.name, true)}End(${getJavaType(column)} ${toCamelCase(column.name, false)}End) {
                this.${toCamelCase(column.name, false)}End = ${toCamelCase(column.name, false)}End;
                }
            </#if>
        </#if>
    </#list>

    <#-- 关联查询字段的Getter和Setter -->
    <#list tableInfo.foreignKeys as fk>
    public List&lt;BigInteger> get${toCamelCase(fk.referencedTable, true)}Ids() {
        return this.${toCamelCase(fk.referencedTable, false)}Ids;
        }

        public void set${toCamelCase(fk.referencedTable, true)}Ids(List&lt;BigInteger> ${toCamelCase(fk.referencedTable, false)}Ids) {
            this.${toCamelCase(fk.referencedTable, false)}Ids = ${toCamelCase(fk.referencedTable, false)}Ids;
            }
            </#list>
}