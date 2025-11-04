package ${packageName};


<#if hasInstantType>import java.time.Instant;
</#if>
<#if hasBigDecimalType>import java.math.BigDecimal;
</#if>
<#if hasBigIntegerType>import java.math.BigInteger;
</#if>

/**
* ${className}查询对象
*/
public class ${className}ConsoleQuery {
<#list columnInfos as column>
    <#if isQueryableField(column)>
        <#if column.type?contains("varchar") || column.type?contains("char")>
    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)};

        <#elseif column.type?contains("int") || isEnumField(column)>
    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)};
        <#elseif column.type?contains("date") || column.type?contains("time")>

    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)}Start;

    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)}End;
        </#if>
    </#if>
</#list>

<#-- 关联查询字段 -->
<#list tableInfo.foreignKeys as fk>
@RelationalQueryProperty(name = "${fk.columnName}.id", type = Part.Type.IN)
private List&lt;BigInteger> ${toCamelCase(fk.referencedTable, false)}Ids;
    </#list>

    <#-- Getter和Setter方法 -->
    <#list columnInfos as column>
        <#if isQueryableField(column)>
            <#if column.type?contains("varchar") || column.type?contains("char") || column.type?contains("int") || isEnumField(column)>
    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}() {
        return this.${toCamelCase(column.fieldName, false)};
    }

    public void set${toCamelCase(column.fieldName, true)}(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}) {
        this.${toCamelCase(column.fieldName, false)} = ${toCamelCase(column.fieldName, false)};
    }
            <#elseif column.type?contains("date") || column.type?contains("time")>
    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}Start() {
        return this.${toCamelCase(column.fieldName, false)}Start;
    }

    public void set${toCamelCase(column.fieldName, true)}Start(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}Start) {
        this.${toCamelCase(column.fieldName, false)}Start = ${toCamelCase(column.fieldName, false)}Start;
    }

    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}End() {
        return this.${toCamelCase(column.fieldName, false)}End;
    }

    public void set${toCamelCase(column.fieldName, true)}End(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}End) {
        this.${toCamelCase(column.fieldName, false)}End = ${toCamelCase(column.fieldName, false)}End;
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