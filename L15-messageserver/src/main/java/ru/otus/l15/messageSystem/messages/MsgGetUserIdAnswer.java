package ru.otus.l15.messageSystem.messages;


import ru.otus.l15.messageSystem.FrontendService;
import ru.otus.l15.messageSystem.MsgToFrontend;
import ru.otus.l15.messageSystem.entity.Address;

/**
 * Created by tully.
 */
public class MsgGetUserIdAnswer extends MsgToFrontend {
    private final String name;
    private final int id;

    public MsgGetUserIdAnswer(Address from, Address to, String name, int id) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(id, name);
    }
}
