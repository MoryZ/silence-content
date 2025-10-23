// model.ftl
package ${packageName};

<#if persistencePackage == "jakarta">
    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.OneToMany;
<#else>
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.OneToMany;
</#if>
import ${basePackage}.domain.enums.${className}Status;
import ${basePackage}.commons.domain.AbstractAuditable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Entity
public class ${className} extends AbstractAuditable&lt;igInteger> {
    <#list tableInfo.columns as column>
        <#if !column.primaryKey>
            <#if column.name == "isbn">
                @Column(updatable = false)
            </#if>
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

    <#-- 关联关系的Getter和Setter -->
    <#list tableInfo.foreignKeys as fk>
        <#assign fieldName = toCamelCase(fk.referencedTable, false)>
        <#assign refClassName = toCamelCase(fk.referencedTable, true)>
        public List<${refClassName}> get${toCamelCase(fk.referencedTable, true)}List() {
        return this.${fieldName}List;
        }

        public void set${toCamelCase(fk.referencedTable, true)}List(List<${refClassName}> ${fieldName}List) {
        this.${fieldName}List = ${fieldName}List;
        }
    </#list>
}