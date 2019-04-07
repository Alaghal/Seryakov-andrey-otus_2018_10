package ru.otus.L14.repository;

import org.springframework.stereotype.Repository;
import ru.otus.L14.services.AddressDBService;
import ru.otus.L14.services.EntityPathProviderService;
import ru.otus.L14.services.MessgeSystemContextService;
import ru.otus.l11.hibernate.HibernateRepository;
import ru.otus.l15.messageSystem.MessageSystemContext;
import ru.otus.l15.messageSystem.entity.Address;
import ru.otus.l15.messageSystem.entity.Addressee;
import ru.otus.l15.messageSystem.entity.MessageSystem;


@Repository
public class UserRepositoryHibernate extends HibernateRepository implements Addressee{

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

