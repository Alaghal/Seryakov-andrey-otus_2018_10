package ru.otus.L16.services;


import org.springframework.stereotype.Service;
import ru.otus.L16.messageSystem.MessageSystem;

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
