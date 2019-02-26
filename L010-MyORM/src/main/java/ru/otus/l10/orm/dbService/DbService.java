package ru.otus.l10.orm.dbService;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface DbService {
     void SaveToDB(Object inputObject, DataSource dataSource) throws SQLException;
     <T> T GetOfDBObject(Class<T> clazz, long id, DataSource dataSource)throws SQLException;
     void createTable(H2DataSource dataSource)throws SQLException;
}

