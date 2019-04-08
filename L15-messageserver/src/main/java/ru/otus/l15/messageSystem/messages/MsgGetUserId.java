package ru.otus.l15.messageSystem.messages;


import ru.otus.l15.messageSystem.DBService;
import ru.otus.l15.messageSystem.MsgToDB;
import ru.otus.l15.messageSystem.entity.Address;

/**
 * Created by tully.
 */
public class MsgGetUserId extends MsgToDB {
    private final String login;

    public MsgGetUserId(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }

    @Override
    public void exec(DBService dbService) {
      //  int id = dbService.getUserId(login);
       // dbService.getMS().sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), login, id));
    }
}
