package ru.otus.L14.services;

import org.springframework.stereotype.Service;
import ru.otus.l11.entity.Address;
import ru.otus.l15.messageSystem.MessageSystemContext;
import ru.otus.l15.messageSystem.entity.MessageSystem;

@Service
public class MessageSystemContextServiceImp implements MessgeSystemContextService{
    private  final  MessageSystemContext context;

    public MessageSystemContextServiceImp(MessageSystemProviderService service) {
        this.context = new MessageSystemContext( service.getMessageSystem());
    }

    @Override
    public MessageSystemContext getMessageSystemContext() {
        return context;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return context.getMessageSystem();
    }

    @Override
    public Address getFrontAddress() {
        return context.getFrontAddress();
    }

    @Override
    public void setFrontAddress(Address address) {
         context.setFrontAddress(address);
    }

    @Override
    public Address getDbAddress() {
        return context.getDbAddress();
    }

    @Override
    public void setDbAddress(Address address) {
         context.setDbAddress( address );

    }
}
