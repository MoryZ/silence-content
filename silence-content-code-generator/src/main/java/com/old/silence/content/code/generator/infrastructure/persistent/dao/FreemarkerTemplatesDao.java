package com.old.silence.content.code.generator.infrastructure.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;
import com.old.silence.content.code.generator.infrastructure.persistent.dao.support.FreemarkerTemplatesResult;

/**
 * @author moryzang
 */
@Component
public class FreemarkerTemplatesDao {


    private final JdbcTemplate contentJdbcTemplate;

    public FreemarkerTemplatesDao(JdbcTemplate contentJdbcTemplate) {
        this.contentJdbcTemplate = contentJdbcTemplate;
    }

    public FreemarkerTemplatesResult loadFromDatabase(String templateName) {
        String sql = "SELECT content,updated_date as updatedDate FROM freemarker_templates WHERE template_name = ? ";
        return contentJdbcTemplate.queryForObject(sql, new SingleColumnRowMapper<>(FreemarkerTemplatesResult.class), templateName);
    }
}
