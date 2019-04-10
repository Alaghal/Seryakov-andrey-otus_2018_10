package ru.otus.L15.messageSystem.entity.messages;


import ru.otus.L15.messageSystem.entity.Address;
import ru.otus.L15.messageSystem.entity.Addressee;

/**
 * @author tully
 */
public abstract class Message {
    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}
