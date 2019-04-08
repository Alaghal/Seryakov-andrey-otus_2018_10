package ru.otus.L14.messages;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l15.messageSystem.DBService;
import ru.otus.l15.messageSystem.MsgToDB;
import ru.otus.l15.messageSystem.entity.Address;

public class MessageSaveUser extends MsgToDB {
    private final MyUser user;

    public MessageSaveUser(Address from, Address to, MyUser user) {
        super( from, to );
        this.user = user;
    }

    @Override
    public void exec(DBService dbService) {
        dbService.save( user );

    }
}
