package ru.otus.L14.services;

import ru.otus.L15.messageSystem.FrontendService;
import ru.otus.l11.entity.MyUser;


import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService extends FrontendService {
    List<MyUser> getUsers() throws ExecutionException, InterruptedException;
    MyUser getUserOfLogin(String login) throws ExecutionException, InterruptedException;
    void saveUserToDB(MyUser user);


}
