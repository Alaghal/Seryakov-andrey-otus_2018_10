package ru.otus.L14.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.l10.orm.users.MyUser;
import ru.otus.l11.hibernate.RepositoryImp;
import ru.otus.l15.messageSystem.MessageSystemContext;
import ru.otus.l15.messageSystem.entity.Address;
import ru.otus.l15.messageSystem.entity.Addressee;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private  RepositoryImp repository;
    private  Address address;
    private  MessageSystemContext context;



    public UserServiceImp(RepositoryImp repository,
                          AddressFrontendService frontendService,
                          MessgeSystemContextService messgeSystemContextService,
                          AddressDBService dbService) {
        this.repository = repository;
        this.address=frontendService.getFrontendAddress();
        this.context=messgeSystemContextService.getMessageSystemContext();
        context.setDbAddress(dbService.getAddressDbForUser());
        context.setFrontAddress( address);

        context.getMessageSystem().start();


    }


    @Override
    public List<MyUser> getUsers() {
        List<MyUser> users = repository.getAll(MyUser.class);
        return  users;
    }

    @Override
    public MyUser getUserOfLogin(String login) {
        MyUser user=(MyUser) repository.getByValue( "login",login,MyUser.class );
        return user;
    }

    @Override
    public void saveUserToDB(MyUser user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save( user );

    }
}
