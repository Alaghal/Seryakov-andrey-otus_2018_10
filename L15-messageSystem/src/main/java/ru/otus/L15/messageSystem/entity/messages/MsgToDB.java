package ru.otus.L15.messageSystem.entity.messages;


import ru.otus.L15.messageSystem.DBService;
import ru.otus.L15.messageSystem.entity.Address;
import ru.otus.L15.messageSystem.entity.Addressee;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
