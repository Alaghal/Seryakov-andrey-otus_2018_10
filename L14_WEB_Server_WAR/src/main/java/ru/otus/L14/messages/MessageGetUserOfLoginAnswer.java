package ru.otus.L14.messages;

import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.entity.Address;
import ru.otus.l15.messageSystem.FrontendService;
import ru.otus.l15.messageSystem.MsgToFrontend;

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
