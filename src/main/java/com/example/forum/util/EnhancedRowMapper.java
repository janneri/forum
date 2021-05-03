package com.example.forum.util;

import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface EnhancedRowMapper<T> {
    @Nullable
    T mapRow(ResultSet var1) throws SQLException;
}
