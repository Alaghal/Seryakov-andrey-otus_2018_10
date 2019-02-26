package ru.otus.l10.orm.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.l10.orm.annotation.ID;
import ru.otus.l10.orm.enums.TypePrimitibeFields;
import ru.otus.l10.orm.reflection.MyParser;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static ru.otus.l4.framework.mytestframework.reflaction.ReflectionHelper.setFieldValue;

public class ExecutorForDB<T> implements DbExecutor<T> {
    private final Connection connection;

    public ExecutorForDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long insertRecord(String sql, Map<String, Object> map) throws SQLException {
        Savepoint savePoint = this.connection.setSavepoint( "savePointName" );
        int counterIndexParams = 1;
        try (PreparedStatement pst = connection.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS )) {
            for (var item : map.entrySet()) {
                if(item.getKey() == "id"){
                    continue;
                }
                pst.setString( counterIndexParams, item.getValue().toString() );
                counterIndexParams++;
            }
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt( 1 );
            }
        } catch (SQLException ex) {
            this.connection.rollback( savePoint );
            System.out.println( ex.getMessage() );
            throw ex;
        }
    }

    @Override
    public void updateRecord(String sql, long id, Map<String, Object> map) throws SQLException {
        Savepoint savePoint = this.connection.setSavepoint( "savePointName" );
        int counterIndexParams = 1;
        try (PreparedStatement pst = connection.prepareStatement( sql )) {

            for (var item : map.entrySet()) {
                if(item.getKey()=="id"){
                   continue;
                }
                pst.setString( counterIndexParams, item.getValue().toString() );
                counterIndexParams++;
            }
            pst.setString( map.values().size(), String.valueOf( id ) );
            pst.executeUpdate();
        } catch (SQLException ex) {
            this.connection.rollback( savePoint );
            System.out.println( ex.getMessage() );
            throw ex;
        }
    }

    @Override
    public Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = this.connection.prepareStatement( sql )) {
            pst.setLong( 1, id );
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable( rsHandler.apply( rs ) );
            }
        }
    }

    private boolean checkObjectPresentInDB(T objectData, long idObject) {
        String sql = "select * from " + (Object) objectData.getClass().getSimpleName() + " where id  = ?";
        try (PreparedStatement pst = this.connection.prepareStatement( sql )) {
            pst.setLong( 1, idObject );
            try (ResultSet rs = pst.executeQuery()) {
                Long id = null;
                if (rs.next()) {
                    id = rs.getLong( 1 );
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    private String CreateQueryForUpdate(T objectData, Map<String, Object> mapField) {
        StringBuilder sql = new StringBuilder( "update " + objectData.getClass().getSimpleName() + " set" );
        for (var item : mapField.keySet()) {
            if(item=="id"){
                continue;
            }
            sql.append( " " + item + "= (?)," );
        }

        sql.deleteCharAt( sql.lastIndexOf( "," ) );
        sql.append( " where id = (?)" );

        return sql.toString();
    }

    private String CreateQueryForInsert(T objectData, Map<String, Object> mapField) {
        StringBuilder sql = new StringBuilder( "insert into " + objectData.getClass().getSimpleName() + " " );
        sql.append( "(" );
        for (var item : mapField.keySet()) {
            if(item == "id"){
                continue;
            }
            sql.append( " " + item + "," );
        }

        sql.deleteCharAt( sql.lastIndexOf( "," ) );
        sql.append( ") values ( " );

        for (var item : mapField.keySet()) {
            if(item == "id"){
                continue;
            }
            sql.append( " (?)," );
        }

        sql.deleteCharAt( sql.lastIndexOf( "," ) );
        sql.append( ")" );
        return sql.toString();
    }

    @Override
    public void save(T objectData) throws SQLException {
        MyParser parser = new MyParser();
        ObjectMapper oMapper = new ObjectMapper();
        long id = parser.getValueOfAnnotationName( ID.class, objectData );

        Map<String, Object> mapField = oMapper.convertValue( objectData, Map.class );
        if (checkObjectPresentInDB( objectData, id )) {
            String sql = CreateQueryForUpdate( objectData, mapField );
             updateRecord( sql, id, mapField );
            System.out.println( "Update record at id=" + id);
        } else {
            String sql = CreateQueryForInsert( objectData, mapField );
            var returnId = insertRecord( sql, mapField );
            System.out.println( "Insert record at " + returnId );
        }
    }

    @Override
    public <T1> T1 load(long id, Class<T1> clazz) {
        MyParser parser = new MyParser();
        Map<String, TypePrimitibeFields> mapFielClazz = parser.getTypeFieldOfObject( clazz );
        String sql = "select * from " + clazz.getSimpleName() + " where id  = ?";
        try (PreparedStatement pst = this.connection.prepareStatement( sql )) {
            pst.setLong( 1, id );
            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    T1 object = parser.CreateObjectOfCurrentType( clazz );
                    return (T1) setValueToObjectOfJson( rs, mapFielClazz, object );
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

    private Object setValueToObjectOfJson(ResultSet rs, Map<String, TypePrimitibeFields> mapTypeField, Object inputeObject) throws SQLException {

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

        return inputeObject;
    }
}
