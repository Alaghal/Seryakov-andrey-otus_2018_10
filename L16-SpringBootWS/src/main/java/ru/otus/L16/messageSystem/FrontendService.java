package ru.otus.L16.messageSystem;


import ru.otus.L16.messageSystem.entity.Addressee;
import ru.otus.l11.entity.MyUser;

import java.util.List;

public interface FrontendService extends Addressee {
    public void addMessageValueToFrontendPool(String key, Object object);
    public void addUserList (List<MyUser> userList);
}

