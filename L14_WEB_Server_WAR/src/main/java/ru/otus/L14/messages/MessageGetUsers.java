package ru.otus.L14.messages;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.entity.Address;
import ru.otus.l15.messageSystem.DBService;
import ru.otus.l15.messageSystem.MsgToDB;

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
