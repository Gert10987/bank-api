package pl.easyprogramming.bank.domain.account.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Address {

    private String city;
    private String street;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    @JsonGetter
    public String city() {
        return city;
    }

    @JsonGetter
    public String street() {
        return street;
    }
}
