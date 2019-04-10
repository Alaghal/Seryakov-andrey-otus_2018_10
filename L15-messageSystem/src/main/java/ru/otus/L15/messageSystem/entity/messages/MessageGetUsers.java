package ru.otus.L15.messageSystem.entity.messages;

import ru.otus.L15.messageSystem.DBService;
import ru.otus.L15.messageSystem.entity.Address;
import ru.otus.l10.orm.users.MyUser;

import java.util.List;

public class MessageGetUsers extends MsgToDB {

    public MessageGetUsers(Address from, Address to) {
        super( from, to );
    }

    @Override
    public void exec(DBService dbService) {
        List<MyUser> userList = dbService.getAll( MyUser.class );
        dbService.getMS().sendMessage( new MessageGetUsersAnswer( getTo(),getFrom(), userList ) );


    }
}
