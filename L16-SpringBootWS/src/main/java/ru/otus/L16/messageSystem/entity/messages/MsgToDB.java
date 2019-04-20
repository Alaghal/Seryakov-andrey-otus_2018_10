package ru.otus.L16.messageSystem.entity.messages;


import ru.otus.L16.messageSystem.DBService;
import ru.otus.L16.messageSystem.entity.Address;
import ru.otus.L16.messageSystem.entity.Addressee;

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
