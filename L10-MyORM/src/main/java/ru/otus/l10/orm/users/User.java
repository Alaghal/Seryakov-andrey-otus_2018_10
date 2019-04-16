package ru.otus.l10.orm.users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private  long id;

    private  String name;
    private  String secondName;
    private  String password;
    private  String login;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AddressDataSet address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneDataSet> phones= new ArrayList<>(  );

    public User(){}

    public User(String login, String password){
        this.login = login;
        this.password=password;
    }

    public User(long id, String name, String secondName){
        this.id = id;
        this.name = name;
        this.secondName = secondName;

    }

    public User(String name, String secondName, String login, String password){
        this.name = name;
        this.secondName = secondName;
        this.password=password;
        this.login = login;
    }

    public User(long id, String name, String secondName, String login, String password){
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.password=password;
        this.login = login;
    }
    public User(long id, String name, String secondName, AddressDataSet address, List<PhoneDataSet> phones) {
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
        StringBuilder stringBuilder = new StringBuilder("MyUser{"  );
        stringBuilder.append( "id=" + getId() );
        stringBuilder.append(  ", Name='" + getName()+ '\'');
        stringBuilder.append(  ", Login='" + getLogin()+ '\'');
        stringBuilder.append(  ", Password='" + getPassword()+ '\'');
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
