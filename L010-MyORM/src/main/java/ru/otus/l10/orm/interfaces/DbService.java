package ru.otus.l10.orm.interfaces;

import javax.sql.DataSource;

public interface DbService {
    public void SaveToDB(Object inputObject, DataSource dataSource );
    public <T> T GetOfDBObject (Class<T> clazz,long id, DataSource dataSource );

}
