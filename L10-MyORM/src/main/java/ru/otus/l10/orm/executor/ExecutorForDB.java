package ru.otus.l10.orm.executor;

import java.sql.*;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ExecutorForDB<T> implements DbExecutor<T> {
    private final Connection connection;

    public ExecutorForDB(Connection connection) {
        this.connection = connection;
    }


    @Override
    public long insertRecord(String sql, Map<String, Object> map) throws SQLException {
        Savepoint savePoint = this.connection.setSavepoint( "savePointName" );
        try (PreparedStatement pst = connection.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS )) {
            setParametersStatement( map, pst );
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
            setParametersStatement( map, pst );
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

    private void setParametersStatement(Map<String, Object> map, PreparedStatement pst) throws SQLException {
        int counterIndexParams = 1;
        for (var item : map.entrySet()) {
            if (item.getKey() == "id") {
                continue;
            }
            pst.setString( counterIndexParams, item.getValue().toString() );
            counterIndexParams++;
        }
    }

    public boolean checkObjectPresentInDB(T objectData, long idObject) {
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

    public String createQueryForUpdate(T objectData, Map<String, Object> mapField) {
        StringBuilder sql = new StringBuilder( "update " + objectData.getClass().getSimpleName() + " set" );
        for (var item : mapField.keySet()) {
            if (item == "id") {
                continue;
            }
            sql.append( " " + item + "= (?)," );
        }

        sql.deleteCharAt( sql.lastIndexOf( "," ) );
        sql.append( " where id = (?)" );

        return sql.toString();
    }

    public String createQueryForInsert(T objectData, Map<String, Object> mapField) {
        StringBuilder sql = new StringBuilder( "insert into " + objectData.getClass().getSimpleName() + " " );
        sql.append( "(" );
        for (var item : mapField.keySet()) {
            if (item == "id") {
                continue;
            }
            sql.append( " " + item + "," );
        }

        sql.deleteCharAt( sql.lastIndexOf( "," ) );
        sql.append( ") values ( " );

        for (var item : mapField.keySet()) {
            if (item == "id") {
                continue;
            }
            sql.append( " (?)," );
        }

        sql.deleteCharAt( sql.lastIndexOf( "," ) );
        sql.append( ")" );
        return sql.toString();
    }


}
