package ru.otus.L14.services;

import org.springframework.stereotype.Service;
import ru.otus.L15.messageSystem.MessageSystem;


@Service
public class MessageSystemProviderServiceImp  implements  MessageSystemProviderService{
    private final MessageSystem messageSystem;

   public MessageSystemProviderServiceImp(){
       messageSystem = new MessageSystem();
   }

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
}
