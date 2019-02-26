package ru.otus.l10.orm.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.l10.orm.annotation.ID;
import ru.otus.l10.orm.enums.TypePrimitibeFields;
import ru.otus.l10.orm.executor.ExecutorForDB;
import ru.otus.l10.orm.reflection.MyParser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public  class MyDBService implements DbService{

    public void SaveToDB(Object inputObject,DataSource dataSource )throws SQLException{
        try(Connection connection = dataSource.getConnection()){
            MyParser parser = new MyParser();
            ObjectMapper oMapper = new ObjectMapper();

            long id = parser.getValueOfAnnotationName( ID.class, inputObject );
            Map<String, Object> mapField = oMapper.convertValue( inputObject, Map.class );
            ExecutorForDB executor = new ExecutorForDB( connection, mapField, id, null );
            executor.save( inputObject );
            connection.commit();
        }
    }

    public <T> T GetOfDBObject (Class<T> clazz,long id, DataSource dataSource )throws SQLException{
        try(Connection connection = dataSource.getConnection()){
            MyParser parser = new MyParser();
            Map<String, TypePrimitibeFields> mapFielClazz = parser.getTypeFieldOfObject( clazz );
            ExecutorForDB executor = new ExecutorForDB( connection, null, id, mapFielClazz );
            return  (T) executor.load( id,clazz);
        }
    }

    @Override
    public void createTable(H2DataSource dataSource)throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table SIMPLEUSER(id long auto_increment, name varchar(50),secondName varchar(50))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }


}
