package ru.otus.L14.services;

import org.springframework.stereotype.Service;
import ru.otus.L14.services.commands.Command;
import ru.otus.L14.services.commands.GetMyUsersCommand;
import ru.otus.L14.services.commands.GetUserOfLoginCommand;
import ru.otus.L15.messageSystem.FrontendService;
import ru.otus.L15.messageSystem.MessageSystem;
import ru.otus.L15.messageSystem.MessageSystemContext;
import ru.otus.L15.messageSystem.entity.Address;
import ru.otus.L15.messageSystem.entity.messages.Message;
import ru.otus.L15.messageSystem.entity.messages.MessageGetUserOfLogin;
import ru.otus.L15.messageSystem.entity.messages.MessageGetUsers;
import ru.otus.L15.messageSystem.entity.messages.MessageSaveUser;
import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.RepositoryImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImp implements UserService, FrontendService {
    private RepositoryImp repository;
    private Address address;
    private MessageSystemContext context;
    private Map<String, Object> messagesOperations = new HashMap<>();
    private Object monitor = new Object();


    public UserServiceImp(RepositoryImp repository,
                          AddressFrontendService frontendService,
                          MessgeSystemContextService messgeSystemContextService,
                          AddressDBService dbService) {
        this.repository = repository;
        this.address = frontendService.getFrontendAddress();
        this.context = messgeSystemContextService.getMessageSystemContext();
        context.setDbAddress( dbService.getAddressDbForUser() );
        context.setFrontAddress( address );

        context.getMessageSystem().start();


    }


    @Override
    public List<MyUser> getUsers() throws ExecutionException, InterruptedException {
        // List<MyUser> users = repository.getAll(MyUser.class);
        Message message = new MessageGetUsers( context.getFrontAddress(), context.getDbAddress() );
        context.getMessageSystem().sendMessage( message );

        Command<List<MyUser>> command = new GetMyUsersCommand<List<MyUser>>( messagesOperations );
        return command.exec();
    }

    @Override
    public void addMessageValueToFrontendPool(String key, Object object) {
        synchronized (monitor) {
            if (!messagesOperations.containsKey( key ) || messagesOperations.get( key ) == null) {
                if (messagesOperations.get( key ) == null) {
                    messagesOperations.remove( key );
                    messagesOperations.put( key, object );
                } else
                    messagesOperations.put( key, object );
            }

        }

    }

    @Override
    public MyUser getUserOfLogin(String login) throws ExecutionException, InterruptedException {

        Message message = new MessageGetUserOfLogin( context.getFrontAddress(), context.getDbAddress(), login );
        context.getMessageSystem().sendMessage( message );
        MyUser user = null;

        Command<MyUser> command = new GetUserOfLoginCommand( messagesOperations, login );
        user = command.exec();

        return user;

    }

    @Override
    public void saveUserToDB(MyUser user) {
        Message message = new MessageSaveUser( context.getFrontAddress(), context.getDbAddress(), user );
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
}
