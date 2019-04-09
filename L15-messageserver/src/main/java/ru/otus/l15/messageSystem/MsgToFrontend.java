package ru.otus.l15.messageSystem;


import ru.otus.l11.entity.Address;
import ru.otus.l11.entity.Addressee;
import ru.otus.l15.messageSystem.entity.Message;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}