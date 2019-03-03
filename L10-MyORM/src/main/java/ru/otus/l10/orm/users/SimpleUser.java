package ru.otus.l10.orm.users;

import ru.otus.l10.orm.annotation.ID;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleUser {
    @ID
    @Id
    @GeneratedValue
    private  long id;
    private  String name;
    private  String secondName;



    public  SimpleUser(){}


    public SimpleUser(long id, String name, String secondName) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }



    @Override
    public String toString() {
        return "SimpleUser{" +
                "id=" + id +
                ", Name='" + name + '\'' + ", SecondName='" + secondName + "\'" +
                '}';
    }
}
