package ru.otus.L14.services;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l15.messageSystem.FrontendService;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService extends  FrontendService {
    public List<MyUser> getUsers() throws ExecutionException, InterruptedException;
    public MyUser getUserOfLogin(String login) throws ExecutionException, InterruptedException;
    public void saveUserToDB(MyUser user);


}
