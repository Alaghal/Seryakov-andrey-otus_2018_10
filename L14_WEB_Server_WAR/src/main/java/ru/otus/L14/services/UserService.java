package ru.otus.L14.services;

import ru.otus.l10.orm.users.MyUser;

import java.util.List;

public interface UserService {
    public List<MyUser> getUsers();
    public MyUser getUserOfLogin(String login);
    public void saveUserToDB(MyUser user);

}
