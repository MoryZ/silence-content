package com.old.silence.content.code.generator.spi;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplatesRepository implements TemplatesRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TemplateResource load(String name) {
        try {
            return jdbcTemplate.query(
                    "SELECT content, updated_date FROM freemarker_templates WHERE name = ?",
                    rs -> rs.next() ? new TemplateResource(rs.getString(1), rs.getString(2)) : null,
                    name
            );
        } catch (Exception e) {
            return null;
        }
    }
}
