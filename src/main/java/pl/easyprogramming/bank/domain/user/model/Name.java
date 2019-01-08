package pl.easyprogramming.bank.domain.user.model;

import java.io.Serializable;

public class Name implements Serializable {

    private static final long serialVersionUID = -295422703255886286L;

    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
