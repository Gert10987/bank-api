package pl.easyprogramming.bank.domain.account.repository.entity;

import pl.easyprogramming.bank.domain.account.model.Address;

import javax.persistence.*;

@Entity
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    private String street;

    private AddressEntity() {
    }

    public AddressEntity(String city, String street) {
        this.city = city;
        this.street = street;
    }

    public Address createModel() {

        Address res = new Address(this.city, this.street);

        return res;
    }
}
