package ru.otus.L15.messageSystem.entity.messages;


import ru.otus.L15.messageSystem.FrontendService;
import ru.otus.L15.messageSystem.entity.Address;
import ru.otus.L15.messageSystem.entity.Addressee;

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