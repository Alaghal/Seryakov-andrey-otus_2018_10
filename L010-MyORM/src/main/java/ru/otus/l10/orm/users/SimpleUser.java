package ru.otus.l10.orm.users;

import ru.otus.l10.orm.annotation.ID;

public class SimpleUser {
    @ID
    private final long id;
    private final String name;
    private final String secondName;

    public SimpleUser(long id, String name, String secondName) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSecondName() {
        return secondName;
    }

    @Override
    public String toString() {
        return "SimpleUser{" +
                "id=" + id +
                ", Name='" + name + '\'' +", SecondName='" + secondName+"\'"+
                '}';
    }
}
