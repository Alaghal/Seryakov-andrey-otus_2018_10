package ru.otus.l12.server;

import ru.otus.l10.orm.users.User;
import ru.otus.l11.hibernate.FactoryRepositories;
import ru.otus.l11.hibernate.FactoryUserRepositoryOfHibernate;
import ru.otus.l11.hibernate.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final Repository repository;

    public UserService(Repository repository) {
        this.repository = repository;
    }

    public void addUsers(User user){
        repository.save( user );

    }

    public  List<User> getUsers(String[] logins){
        List<User> listUser = new ArrayList<>();
        for (var name : logins) {
            User user = (User) repository.getByValue( "login", name, User.class );
            listUser.add( user );
        }
        return listUser;
    }


    public boolean authenticate(String name, String password) {


        return Optional.ofNullable((User)repository.getByValue( "name",name, User.class ))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}
