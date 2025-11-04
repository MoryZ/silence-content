package ${packageName};

import ${basePackage}.domain.model.${className};
import com.old.silence.data.jdbc.repository.JdbcRepository;

<#if hasBigIntegerType>import java.math.BigInteger;
</#if>

/**
* ${className}数据访问接口
*/
public interface ${className}Dao extends JdbcRepository<${className}, ${primaryType}> {

}