package ru.otus.L14.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.L14.services.AddressDBService;
import ru.otus.L14.services.EntityPathProviderService;
import ru.otus.L14.services.MessgeSystemContextService;

import ru.otus.L15.messageSystem.DBService;
import ru.otus.L15.messageSystem.MessageSystem;
import ru.otus.L15.messageSystem.MessageSystemContext;
import ru.otus.L15.messageSystem.entity.Address;
import ru.otus.l11.hibernate.HibernateRepository;



@Repository
public class UserRepositoryHibernate extends HibernateRepository implements DBService {

    private final Address address;
    private final MessageSystemContext context;


    public UserRepositoryHibernate (EntityPathProviderService packageProviderOfEntity, AddressDBService addressService, MessgeSystemContextService service) {
        super( packageProviderOfEntity.getEntityPackagePath() );
        this.address=addressService.getAddressDbForUser();
        this.context=service.getMessageSystemContext();
        context.getMessageSystem().addAddressee( this);

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

