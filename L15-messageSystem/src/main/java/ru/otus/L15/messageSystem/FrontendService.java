package ru.otus.L15.messageSystem;


import ru.otus.L15.messageSystem.entity.Addressee;


public interface FrontendService extends Addressee {
    public void addMessageValueToFrontendPool(String key, Object object);

}

