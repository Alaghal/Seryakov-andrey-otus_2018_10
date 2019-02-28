package ru.otus.l10.orm;

import ru.otus.l10.orm.dbService.DbService;
import ru.otus.l10.orm.dbService.H2DataSource;
import ru.otus.l10.orm.dbService.MyDBService;
import ru.otus.l10.orm.users.SimpleUser;

import java.sql.SQLException;

public class Demo {

    public static void main(String... arg) throws SQLException {
        H2DataSource dataSource = new H2DataSource();
        DbService serviceDB = new MyDBService( dataSource );
        SimpleUser userInsert = new SimpleUser( 1, "Forest", "Gump" );
        SimpleUser userUpdate = new SimpleUser( 1, "Test", "Gump" );
        serviceDB.createTable( dataSource );
        serviceDB.save( userInsert );
        SimpleUser userOfInsert = (SimpleUser) serviceDB.load( 1, SimpleUser.class );
        serviceDB.save( userUpdate );
        SimpleUser userOfUpdate = (SimpleUser) serviceDB.load( 1, SimpleUser.class );

        System.out.println( "Insert user " + userOfInsert.toString() );
        System.out.println( "Insert user " + userOfUpdate.toString() );


    }
}