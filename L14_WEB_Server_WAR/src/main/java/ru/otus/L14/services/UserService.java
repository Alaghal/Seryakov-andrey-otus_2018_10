package ru.otus.L14.services;


import ru.otus.l11.hibernate.entity.MyUser;

import java.util.List;

public interface UserService {
    List<MyUser> getUsers();
    MyUser getUserOfLogin(String login);
    void saveUserToDB(MyUser user);

}
