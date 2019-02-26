package ru.otus.l10.orm;

import ru.otus.l10.orm.dbService.DbService;
import ru.otus.l10.orm.dbService.MyDBService;
import ru.otus.l10.orm.dbService.H2DataSource;
import ru.otus.l10.orm.users.SimpleUser;

import java.sql.SQLException;

public class Demo {

    public  static  void main(String... arg) throws SQLException {
        H2DataSource dataSource= new H2DataSource();
        DbService serviceDB = new MyDBService();
        SimpleUser userInsert = new SimpleUser( 1,"Forest","Gump" );
        SimpleUser userUpdate = new SimpleUser( 1,"Test","Gump" );
        serviceDB.createTable( dataSource );
        serviceDB.SaveToDB( userInsert,dataSource );
        SimpleUser userOfInsert = serviceDB.GetOfDBObject( SimpleUser.class,1,dataSource );
        serviceDB.SaveToDB( userUpdate,dataSource );
        SimpleUser userOfUpdate = serviceDB.GetOfDBObject( SimpleUser.class,1,dataSource );

        System.out.println( "Insert user " + userOfInsert.toString() );
        System.out.println( "Insert user " + userOfUpdate.toString() );


    }
}
