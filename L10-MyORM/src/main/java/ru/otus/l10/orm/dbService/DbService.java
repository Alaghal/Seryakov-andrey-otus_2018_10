package ru.otus.l10.orm.dbService;

import java.sql.SQLException;

public interface DbService<T> {
    void save(T inputObject) throws SQLException;

    <T1> T1 load(long id, Class<T1> clazz) throws SQLException;

    void createTable(H2DataSource dataSource) throws SQLException;
}

