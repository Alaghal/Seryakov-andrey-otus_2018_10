package ru.otus.L16.messageSystem.entity.messages;


import ru.otus.L16.messageSystem.DBService;
import ru.otus.L16.messageSystem.entity.Address;
import ru.otus.l11.entity.MyUser;

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
