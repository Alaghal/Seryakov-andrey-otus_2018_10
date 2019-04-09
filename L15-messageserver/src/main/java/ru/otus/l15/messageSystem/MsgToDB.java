package ru.otus.l15.messageSystem;


import ru.otus.l11.entity.Address;
import ru.otus.l11.entity.Addressee;
import ru.otus.l15.messageSystem.entity.Message;

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
