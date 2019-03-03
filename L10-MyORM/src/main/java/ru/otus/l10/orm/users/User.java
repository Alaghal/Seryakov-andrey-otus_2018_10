package ru.otus.l10.orm.users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private  long id;

    private  String name;
    private  String secondName;


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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AddressDataSet address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneDataSet> phones= new ArrayList<>(  );

    public User(){}

    public User(long id, String name, String secondName){
        this.id = id;
        this.name = name;
        this.secondName = secondName;
    }
    public User(long id, String name, String secondName, AddressDataSet address,List<PhoneDataSet> phones) {
        this.id=id;
        this.name = name;
        this.secondName = secondName;
        this.address = address;
        this.phones=phones;
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
