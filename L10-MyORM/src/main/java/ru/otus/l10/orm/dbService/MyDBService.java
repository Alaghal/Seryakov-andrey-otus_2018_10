package ru.otus.l10.orm.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.l10.orm.annotation.ID;
import ru.otus.l10.orm.enums.TypePrimitibeFields;
import ru.otus.l10.orm.executor.ExecutorForDB;
import ru.otus.l10.orm.reflection.MyParser;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper.setFieldValue;

public class MyDBService<T> implements DbService<T> {
    private final DataSource dataSource;

    public MyDBService(DataSource dataSource) {


        this.dataSource = dataSource;
    }

    @Override
    public void save(T inputObject) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            MyParser parser = new MyParser();
            ObjectMapper oMapper = new ObjectMapper();

            long id = parser.getValueOfAnnotationName( ID.class, inputObject );
            Map<String, Object> mapField = oMapper.convertValue( inputObject, Map.class );
            ExecutorForDB executor = new ExecutorForDB( connection );

            if (executor.checkObjectPresentInDB( inputObject, id )) {
                String sql = executor.createQueryForUpdate( inputObject, mapField );
                executor.updateRecord( sql, id, mapField );
                System.out.println( "Update record at id=" + id );
            } else {
                String sql = executor.createQueryForInsert( inputObject, mapField );
                var returnId = executor.insertRecord( sql, mapField );
                System.out.println( "Insert record at " + returnId );
            }
            connection.commit();
        }
    }


    @Override
    public <T1> T1 load(long id, Class<T1> clazz) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            MyParser parser = new MyParser();
            Map<String, TypePrimitibeFields> mapFielClazz = parser.getTypeFieldOfObject( clazz );
            ExecutorForDB executor = new ExecutorForDB( connection );
            String sql = "select * from " + clazz.getSimpleName() + " where id  = ?";

            try (PreparedStatement pst = connection.prepareStatement( sql )) {
                pst.setLong( 1, id );
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        T1 object = parser.createObjectOfCurrentType( clazz );
                        setValueToObjectField( rs, mapFielClazz, object );
                        return (T1) object;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;

        }
    }


    @Override
    public void createTable(H2DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement( "create table SIMPLEUSER(id long auto_increment, name varchar(50),secondName varchar(50))" )) {
            pst.executeUpdate();
        }
        System.out.println( "table created" );
    }

    private void setValueToObjectField(ResultSet rs, Map<String, TypePrimitibeFields> mapTypeField, Object inputeObject) throws SQLException {
        for (var item : mapTypeField.entrySet()) {
            switch (item.getValue()) {
                case VALUE_NUMBER:
                    var numberValue = rs.getLong( item.getKey() );
                    setFieldValue( inputeObject, item.getKey(), numberValue );
                    break;
                case VALUE_STRING:
                    var stringValue = rs.getString( item.getKey() );
                    setFieldValue( inputeObject, item.getKey(), stringValue );
                    break;
                case VALUE_TRUE:
                    var booleanTrue = rs.getString( item.getKey() );
                    setFieldValue( inputeObject, item.getKey(), booleanTrue );
                    break;
                case VALUE_FALSE:
                    var booleanFalse = rs.getString( item.getKey() );
                    setFieldValue( inputeObject, item.getKey(), booleanFalse );
                    break;
            }
        }
    }

}
