// dao.ftl
package ${packageName};

import ${basePackage}.domain.model.${className};
import ${basePackage}.jdbc.repository.JdbcRepository;

import java.math.BigInteger;

/**
* ${className}数据访问接口
*/
public interface ${className}Dao extends JdbcRepository<${className}, BigInteger> {

}