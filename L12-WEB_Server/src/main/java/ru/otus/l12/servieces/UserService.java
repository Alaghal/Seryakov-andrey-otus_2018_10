package ru.otus.l12.servieces;


import ru.otus.l11.entity.MyUser;
import ru.otus.l11.hibernate.RepositoryImp;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final RepositoryImp repository;

    public UserService(RepositoryImp repository) {
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
        return listUser;
    }


    public boolean authenticate(String name, String password) {
        return Optional.ofNullable((MyUser)repository.getByValue( "login",name, MyUser.class ))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}
