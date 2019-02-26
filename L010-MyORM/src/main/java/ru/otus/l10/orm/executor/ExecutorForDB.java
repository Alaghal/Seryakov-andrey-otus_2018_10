package ru.otus.l10.orm.executor;

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
    private final Map<String, Object> mapFieldObject;
    private final long idObject;
    private final  Map<String, TypePrimitibeFields> mapFielClazz;

    public ExecutorForDB(Connection connection, Map<String, Object> mapFieldObject, long id, Map<String, TypePrimitibeFields> mapFielClazz) {
        this.connection = connection;
        this.mapFieldObject = mapFieldObject;
        this.idObject = id;
        this.mapFielClazz = mapFielClazz;
    }

    @Override
    public void save(T objectData) throws SQLException {
        if (checkObjectPresentInDB( objectData, idObject )) {
            String sql = CreateQueryForUpdate( objectData, mapFieldObject );
            updateRecord( sql, idObject, mapFieldObject );
            System.out.println( "Update record at id=" + idObject);
        } else {
            String sql = CreateQueryForInsert( objectData, mapFieldObject );
            var returnId = insertRecord( sql, mapFieldObject );
            System.out.println( "Insert record at " + returnId );
        }
    }

    @Override
    public <T1> T1 load(long id, Class<T1> clazz) {
        MyParser parser = new MyParser();
        String sql = "select * from " + clazz.getSimpleName() + " where id  = ?";
        try (PreparedStatement pst = this.connection.prepareStatement( sql )) {
            pst.setLong( 1, id );
            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    T1 object = parser.CreateObjectOfCurrentType( clazz );
                    setValueToObjectField(rs, mapFielClazz, object);
                    return object;
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


    @Override
    public long insertRecord(String sql, Map<String, Object> map) throws SQLException {
        Savepoint savePoint = this.connection.setSavepoint( "savePointName" );
        try (PreparedStatement pst = connection.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS )) {
            setParametersStatement(map,pst);
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
        try (PreparedStatement pst = connection.prepareStatement( sql )) {
            setParametersStatement(map,pst);
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
    private void setParametersStatement(Map<String, Object> map, PreparedStatement pst ) throws SQLException {
        int counterIndexParams = 1;
        for (var item : map.entrySet()) {
            if(item.getKey()=="id"){
                continue;
            }
            pst.setString( counterIndexParams, item.getValue().toString() );
            counterIndexParams++;
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
