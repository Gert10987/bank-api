package pl.easyprogramming.bank.domain.account.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {

        return Objects.hash(city, street);
    }

    @Override
    public String toString() {
        return new StringBuilder("Address{").
                append("city='").append(city).append('\'').
                append(", street='").append(street).append('\'').
                append('}').toString();
    }
}
