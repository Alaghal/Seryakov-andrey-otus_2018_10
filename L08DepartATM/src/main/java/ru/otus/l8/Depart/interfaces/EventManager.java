package ru.otus.l8.Depart.interfaces;

import ru.otus.l7.ATM.interfaces.EventListener;

public interface EventManager {

    public void subscribe(String eventType, EventListener listener);
    public void unsubscribe(String eventType, EventListener listener);
    public void notify(String eventType);
}
