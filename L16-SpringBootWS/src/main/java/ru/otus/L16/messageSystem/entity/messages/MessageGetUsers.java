package ru.otus.L16.messageSystem.entity.messages;


import ru.otus.L16.messageSystem.DBService;
import ru.otus.L16.messageSystem.entity.Address;
import ru.otus.l11.entity.MyUser;

import java.util.ArrayList;
import java.util.List;

public class MessageGetUsers extends MsgToDB {

    public MessageGetUsers(Address from, Address to) {
        super( from, to );
    }

    @Override
    public void exec(DBService dbService) {
        MyUser user1= new MyUser( "Petrov","12345" );
        MyUser user2= new MyUser( "Ivan", "14568" );

        List<MyUser> userList = new ArrayList<>(  );
        userList.add(user2);
        userList.add(user1);



        dbService.getMS().sendMessage( new MessageGetUsersAnswer( getTo(),getFrom(), userList ) );


    }
}
