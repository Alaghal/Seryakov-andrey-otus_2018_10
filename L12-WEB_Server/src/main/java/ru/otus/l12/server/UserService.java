package ru.otus.l12.server;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.Repository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final Repository repository;

    public UserService(Repository repository) {
        this.repository = repository;
    }

    public void addUsers(MyUser user){
        repository.save( user );

    }


    public  MyUser getUserForLogin (String login){
      MyUser my= (MyUser) repository.getByValue( "login",login,MyUser.class );
      return my;
    }

    public  List<MyUser> getUsers(){
        List<MyUser> listUser =( List<MyUser>)  repository.getAll(  MyUser.class );
         System.out.println( listUser );
        System.out.println( "wwweq" );
        return listUser;
    }


    public boolean authenticate(String name, String password) {


        return Optional.ofNullable((MyUser)repository.getByValue( "login",name, MyUser.class ))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}
