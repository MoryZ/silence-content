package ${packageName};

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;

import ${basePackage}.domain.model.${className};
import ${basePackage}.domain.repository.${className}Repository;
import ${basePackage}.infrastructure.persistence.dao.${className}Dao;

import java.math.BigInteger;
import java.util.Optional;

/**
* ${className}仓储实现
*/
@Repository
public class ${className}MyBatisRepository implements ${className}Repository {
        private final ${className}Dao ${className?uncap_first}Dao;

        public ${className}MyBatisRepository(${className}Dao ${className?uncap_first}Dao) {
            this.${className?uncap_first}Dao = ${className?uncap_first}Dao;
        }

        @Override
        public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
            return ${className?uncap_first}Dao.findById(id, projectionType);
        }

        @Override
        public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
            return ${className?uncap_first}Dao.findByCriteria(criteria, pageable, projectionType);
        }

        @Override
        public int create(${className} ${className?uncap_first}) {
            return ${className?uncap_first}Dao.insert(${className?uncap_first});
        }

        @Override
        public int update(${className} ${className?uncap_first}) {
            return ${className?uncap_first}Dao.update(${className?uncap_first});
        }

        @Override
        public int deleteById(BigInteger id) {
            return ${className?uncap_first}Dao.deleteById(id);
        }
}