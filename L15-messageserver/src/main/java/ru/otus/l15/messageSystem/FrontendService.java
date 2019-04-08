package ru.otus.l15.messageSystem;


import ru.otus.l15.messageSystem.entity.Addressee;


public interface FrontendService extends Addressee {
    public void addMessageValueToFrontendPool(String key, Object object);

}

