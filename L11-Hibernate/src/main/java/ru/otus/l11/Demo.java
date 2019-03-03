package ru.otus.l11;

import ru.otus.l10.orm.users.AddressDataSet;
import ru.otus.l10.orm.users.PhoneDataSet;
import ru.otus.l10.orm.users.SimpleUser;
import ru.otus.l10.orm.users.User;
import ru.otus.l11.Hibernate.HibernateFunction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String... arg) throws SQLException {

        HibernateFunction hFunction = new HibernateFunction();

        User userInsert = new User( 1, "Forest", "Gump" );
        User userUpdate = new User( 1, "Test", "Gump" );

        List<PhoneDataSet> phonesIsert = new ArrayList<>(  );

        for (int i=0;i<30;i++){
            phonesIsert.add(new PhoneDataSet( "+"+i,userInsert ));
        }

        userInsert.setPhones( phonesIsert );
        List<PhoneDataSet> phonesUpdate = new ArrayList<>(  );

        for (int i=30;i<70;i++){
            phonesUpdate.add(new PhoneDataSet( "+"+i,userUpdate ));
        }

        userUpdate.setPhones( phonesUpdate );

        userInsert.setAddresses(new AddressDataSet(  "RedStreet",userInsert) );
        userUpdate.setAddresses(new AddressDataSet( "WhiteStreet",userUpdate ) );


        hFunction.save( userInsert );
        User userOfInsert =  hFunction.load( userInsert.getId(), User.class );
        hFunction.save( userUpdate );
        User userOfUpdate = hFunction.load( userUpdate.getId(), User.class );

        System.out.println( "Insert user " + userOfInsert.toString() );
        System.out.println( "Update user " + userOfUpdate.toString() );
    }
}