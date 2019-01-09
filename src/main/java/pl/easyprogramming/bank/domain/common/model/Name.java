package pl.easyprogramming.bank.domain.common.model;

import java.io.Serializable;

public final class Name implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;

    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }
}
