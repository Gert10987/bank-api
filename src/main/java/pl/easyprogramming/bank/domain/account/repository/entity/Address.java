package pl.easyprogramming.bank.domain.account.repository.entity;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    private String street;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }
}
