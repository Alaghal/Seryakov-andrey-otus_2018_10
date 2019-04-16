package ru.otus.l11;

import ru.otus.l11.entity.AddressDataSet;
import ru.otus.l11.entity.PhoneDataSet;
import ru.otus.l11.entity.MyUser;
import ru.otus.l11.hibernate.FactoryRepositories;
import ru.otus.l11.hibernate.FactoryUserRepositoryOfHibernate;
import ru.otus.l11.hibernate.RepositoryImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String... arg) throws SQLException {

        FactoryRepositories  facoryRepos = new FactoryUserRepositoryOfHibernate();
        RepositoryImp hFunction = facoryRepos.createRepository();

        MyUser userInsert = new MyUser( 1, "Forest", "Gump" );
        MyUser userUpdate = new MyUser( 1, "Test", "Gump" );

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
        MyUser userOfInsert = (MyUser) hFunction.load( userInsert.getId(), MyUser.class );
        hFunction.save( userUpdate );
        MyUser userOfUpdate = (MyUser) hFunction.load( userUpdate.getId(), MyUser.class );
         userOfUpdate = (MyUser)hFunction.getByValue( "name","Forest", MyUser.class );
        System.out.println( "Insert user " + userOfInsert.getName() );
        System.out.println( "Update user " + userOfUpdate.getId() );

        List<MyUser> list = (List<MyUser>) hFunction.getAll( MyUser.class);

        for (var itrm:list) {
            System.out.println( itrm.getName() + itrm.getId() );
        }
    }
}
