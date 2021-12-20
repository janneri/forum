package com.example.forum.util;

import com.example.forum.dto.BaseId;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimpleJdbcQuery {
    private final JdbcTemplate jdbcTemplate;
    private String queryStr;
    private Object[] params;

    public SimpleJdbcQuery(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private SimpleJdbcQuery(JdbcTemplate jdbcTemplate, String queryStr) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryStr = queryStr;
    }

    public SimpleJdbcQuery queryStr(String queryStr) {
        return new SimpleJdbcQuery(jdbcTemplate, queryStr);
    }

    public SimpleJdbcQuery params(Object... params) {
        this.params = applyUserTypes(params);
        return this;
    }

    public <T> List<T> listWith(EnhancedRowMapper<T> enhancedRowMapper) {
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> enhancedRowMapper.mapRow(rs), params);
    }

    public <T> T getExactlyOne(EnhancedRowMapper<T> enhancedRowMapper) {
        return Objects.requireNonNull(jdbcTemplate.queryForObject(queryStr, (rs, rowNum) -> enhancedRowMapper.mapRow(rs), params));
    }

    public int execute() {
        return jdbcTemplate.update(queryStr, params);
    }

    // Could/should operate with a list of converters/real user types
    private Object[] applyUserTypes(Object... params) {
        return Arrays.stream(params)
                .map(param -> {
                    if (param != null && BaseId.class.isAssignableFrom(param.getClass())) {
                        return ((BaseId) param).intValue();
                    }
                    return param;
                }).
                toArray(Object[]::new);
    }
}
