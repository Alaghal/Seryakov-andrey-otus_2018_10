package ru.otus.l10.orm.users;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "addressDataSet")
public class AddressDataSet {
    @Id
    @GeneratedValue
    private long id;
    
    @Column(name = "street_number", nullable = false)
    private String street;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    private MyUser user;

    public AddressDataSet(){}
    public AddressDataSet(String street, MyUser user) {
        this.street = street;
        this.user=user;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDataSet address = (AddressDataSet) o;
        return  Objects.equals( id, address.id );
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
