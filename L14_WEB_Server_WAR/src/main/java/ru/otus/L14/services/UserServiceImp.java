package ru.otus.L14.services;

import org.springframework.stereotype.Service;
import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.RepositoryImp;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final RepositoryImp repository;

    public UserServiceImp(RepositoryImp repository) {
        this.repository = repository;
    }


    @Override
    public List<MyUser> getUsers() {
        List<MyUser> users = repository.getAll(MyUser.class);
        return  users;
    }

    @Override
    public MyUser getUserOfLogin(String login) {
        MyUser user=(MyUser) repository.getByValue( "login",login,MyUser.class );
        return user;
    }

    @Override
    public void saveUserToDB(MyUser user) {
        repository.save( user );

    }
}
