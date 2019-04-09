package ru.otus.L14.messages;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.entity.Address;
import ru.otus.l15.messageSystem.DBService;
import ru.otus.l15.messageSystem.MsgToDB;

public class MessageGetUserOfLogin extends MsgToDB {
    private final String login;

    public MessageGetUserOfLogin(Address from, Address to, String login) {
        super( from, to );
        this.login = login;
    }

    @Override
    public void exec(DBService dbService) {
        MyUser user = (MyUser) dbService.getByValue( "login",login, MyUser.class );
        dbService.getMS().sendMessage( new MessageGetUserOfLoginAnswer( getTo(),getFrom(),user) );

    }
}
