package ru.otus.L14.messages;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l15.messageSystem.FrontendService;
import ru.otus.l15.messageSystem.MsgToFrontend;
import ru.otus.l15.messageSystem.entity.Address;

import java.util.List;

public class MessageGetUsersAnswer extends MsgToFrontend {

    private final String LIST_MY_USERS = "listOfMyUserClass";
    private final List<MyUser>  userList ;

    public MessageGetUsersAnswer(Address from, Address to, List<MyUser> userList) {
        super( from, to );
        this.userList = userList;
    }

    @Override
    public void exec(FrontendService frontendService) {
       frontendService.addMessageValueToFrontendPool( LIST_MY_USERS, userList );
    }
}
