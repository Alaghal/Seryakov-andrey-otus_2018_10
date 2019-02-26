package ru.otus.l10.orm.dbService;

import ru.otus.l10.orm.executor.ExecutorForDB;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBService {

    public void SaveToDB(Object inputObject,DataSource dataSource )throws SQLException{
        try(Connection connection = dataSource.getConnection()){
            ExecutorForDB executor = new ExecutorForDB( connection );
            executor.save( inputObject );
            connection.commit();
        }
    }

    public <T> T GetOfDBObject (Class<T> clazz,long id, DataSource dataSource )throws SQLException{
        try(Connection connection = dataSource.getConnection()){
            ExecutorForDB executor = new ExecutorForDB( connection );
            return  (T) executor.load( id,clazz);
        }
    }

    public void createTable(DataSource dataSource)throws SQLException  {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table SIMPLEUSER(id long auto_increment, name varchar(50),secondName varchar(50))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }

    public static interface DbService {
        public void SaveToDB(Object inputObject, DataSource dataSource);
        public <T> T GetOfDBObject(Class<T> clazz, long id, DataSource dataSource);

    }
}
