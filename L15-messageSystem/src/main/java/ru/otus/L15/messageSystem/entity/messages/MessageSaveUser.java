package ru.otus.L15.messageSystem.entity.messages;


import ru.otus.L15.messageSystem.DBService;
import ru.otus.L15.messageSystem.entity.Address;
import ru.otus.l10.orm.users.MyUser;



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
