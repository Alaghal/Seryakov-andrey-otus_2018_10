package ru.otus.l12.server;

import ru.otus.l10.orm.users.User;
import ru.otus.l11.hibernate.FactoryRepositories;
import ru.otus.l11.hibernate.FactoryUserRepositoryOfHibernate;
import ru.otus.l11.hibernate.Repository;

import java.util.Optional;

public class UserService {
    private final Repository repository;

    public UserService(Repository repository) {
        this.repository = repository;
    }


    public boolean authenticate(String name, String password) {


        return Optional.ofNullable((User)repository.getByValue( "name",name, User.class ))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}
