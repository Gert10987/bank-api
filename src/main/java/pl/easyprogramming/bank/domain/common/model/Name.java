package pl.easyprogramming.bank.domain.common.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;
import java.util.Objects;

public final class Name implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;

    private String firstName;
    private String lastName;

    private Name() {
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @JsonGetter
    public String firstName() {
        return firstName;
    }

    @JsonGetter
    public String lastName() {
        return lastName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(lastName, name.lastName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return new StringBuilder("Name{").
                append("firstName='").append(firstName).append('\'').
                append(", lastName='").append(lastName).append('\'').
                append('}').toString();
    }
}
