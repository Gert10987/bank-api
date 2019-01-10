package pl.easyprogramming.bank.domain.common.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;

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
}
