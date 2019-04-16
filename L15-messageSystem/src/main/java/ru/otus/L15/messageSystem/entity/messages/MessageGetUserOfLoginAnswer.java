package ru.otus.L15.messageSystem.entity.messages;

import ru.otus.L15.messageSystem.FrontendService;
import ru.otus.L15.messageSystem.entity.Address;
import ru.otus.l11.entity.MyUser;


public class MessageGetUserOfLoginAnswer extends MsgToFrontend {
    private final MyUser user;

    public MessageGetUserOfLoginAnswer(Address from, Address to, MyUser user) {
        super( from, to );
        this.user=user;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addMessageValueToFrontendPool( user.getLogin(),user );
    }
}
