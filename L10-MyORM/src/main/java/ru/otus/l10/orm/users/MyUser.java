package ru.otus.l10.orm.users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class MyUser {
    @Id
    @GeneratedValue
    private  long id;

    private  String name;
    private  String secondName;
    private  String password;
    private  String login;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AddressDataSet address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneDataSet> phones= new ArrayList<>(  );

    public MyUser(){}

    public MyUser(String login, String password){
        this.login = login;
        this.password=password;
    }

    public MyUser(long id, String name, String secondName){
        this.id = id;
        this.name = name;
        this.secondName = secondName;

    }
    public MyUser(long id, String name, String secondName, String login, String password){
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.password=password;
        this.login = login;
    }
    public MyUser(long id, String name, String secondName, AddressDataSet address, List<PhoneDataSet> phones) {
        this.id=id;
        this.name = name;
        this.secondName = secondName;
        this.address = address;
        this.phones=phones;
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

    public AddressDataSet getAddress() {
        return address;
    }
    public void setAddresses(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }
    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("SimpleUser{"  );
        stringBuilder.append( "id=" + getId() );
        stringBuilder.append(  ", Name='" + getName()+ '\'');
        stringBuilder.append( ", SecondName='" + getSecondName() + "\'" );
        stringBuilder.append( " Phones {" );
        for (var phone:phones) {
          stringBuilder.append( " +"+phone.getNumber()   );
        }
        stringBuilder.append( "} " );
        stringBuilder.append( "Address = "+getAddress()+";" );
        stringBuilder.append( "}" );

        return stringBuilder.toString();
    }


}
