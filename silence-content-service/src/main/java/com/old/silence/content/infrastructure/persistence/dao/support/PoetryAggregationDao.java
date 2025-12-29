package com.old.silence.content.infrastructure.persistence.dao.support;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.old.silence.content.api.vo.StatsVo;

import java.util.List;

/**
 * @author moryzang
 */
@Component
public class PoetryAggregationDao {

    private final JdbcTemplate jdbcTemplate;

    public PoetryAggregationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<StatsVo> findFavoriteTop5() {
        String sql = """
                SELECT user_id userId, count(*) indicatorAccumulation
                FROM user_interaction_log
                where interaction_type = 7
                GROUP BY user_id
                ORDER BY indicatorAccumulation DESC LIMIT 5\s""";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatsVo.class));
    }

    public List<StatsVo> findMaxAnswerCountTop5() {
        String sql = """
                SELECT\s
                    user_id,
                    COUNT(*) AS indicatorAccumulation,
                    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM poetry_answer_records), 2) AS percentage
                FROM poetry_answer_records
                GROUP BY user_id
                ORDER BY indicatorAccumulation DESC
                LIMIT 5\s""";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatsVo.class));
    }

    public List<StatsVo> findMaxAnswerAccuracyTop5() {
        String sql = """
                SELECT\s
                             user_id,
                             COUNT(*) AS total_answers,
                             SUM(CASE WHEN is_correct = 1 THEN 1 ELSE 0 END) AS correct_answers,
                             ROUND(SUM(CASE WHEN is_correct = 1 THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS accuracy
                         FROM poetry_answer_records
                         GROUP BY user_id
                         HAVING total_answers >= 5
                         ORDER BY accuracy DESC, total_answers DESC
                         LIMIT 5""";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatsVo.class));
    }
}
