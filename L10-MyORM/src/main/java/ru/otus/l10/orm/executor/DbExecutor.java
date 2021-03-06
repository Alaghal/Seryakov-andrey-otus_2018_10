package ru.otus.l10.orm.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface DbExecutor<T> {

    long insertRecord(String sql, Map<String, Object> map) throws SQLException;

    void updateRecord(String sql, long id, Map<String, Object> map) throws SQLException;

    Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException;
}
