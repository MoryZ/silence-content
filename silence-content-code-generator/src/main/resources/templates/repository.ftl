// repository.ftl
package ${packageName};

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;

import ${basePackage}.domain.model.${className};

import java.math.BigInteger;
import java.util.Optional;

/**
* ${className}仓储接口
*/
public interface ${className}Repository {

        &lt;T&gt; Optional&lt;T&gt; findById(BigInteger id, Class&lt;T&gt; projectionType);

        &lt;T&gt; Page&lt;T&gt; findByCriteria(Criteria criteria, Pageable pageable, Class&lt;T&gt; projectionType);

        int create(${className} ${className?uncap_first});

        int update(${className} ${className?uncap_first});

        int deleteById(BigInteger id);
}