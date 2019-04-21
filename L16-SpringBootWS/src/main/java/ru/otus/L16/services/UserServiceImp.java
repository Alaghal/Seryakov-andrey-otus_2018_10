package ru.otus.L16.services;


import org.h2.command.Command;
import org.springframework.stereotype.Service;
import ru.otus.L16.messageSystem.FrontendService;
import ru.otus.L16.messageSystem.MessageSystem;
import ru.otus.L16.messageSystem.MessageSystemContext;
import ru.otus.L16.messageSystem.entity.Address;
import ru.otus.L16.messageSystem.entity.messages.Message;
import ru.otus.L16.messageSystem.entity.messages.MessageGetUsers;
import ru.otus.L16.messageSystem.entity.messages.MessageSaveUser;
import ru.otus.l11.entity.MyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImp implements UserService, FrontendService {
    private Address address;
    private AddressDBService serviceAddressDB;
    private MessageSystemContext context;
    private List<MyUser> userListCash;
    private final Object monitor = new Object();



    public UserServiceImp(AddressDBService serviceAddressDB,
                          MessageSystemContextService messageSystemContextService ) {
        context=messageSystemContextService.getMessageSystemContext();
        context.getMessageSystem().addAddressee( this );
        address = new Address(   "Front") ;
        this.serviceAddressDB = serviceAddressDB;
    }



    @Override
    public List<MyUser> getUsers() {

      return userListCash;
    }

    @Override
    public MyUser getUserOfLogin(String login) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public void saveUserToDB(MyUser myUser) {
        Message message = new MessageSaveUser( context.getFrontAddress(), context.getDbAddress(), myUser );
        context.getMessageSystem().sendMessage( message );
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @Override
    public void addMessageValueToFrontendPool(String key, Object object) {

    }

    @Override
    public void addUserList(List<MyUser> userList) {
            synchronized (monitor){
                this.userListCash=userList;
            }
    }
}
