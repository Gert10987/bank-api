package pl.easyprogramming.bank.domain.common.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.ValidationException;
import java.io.Serializable;
import java.util.Objects;

public final class Email implements Serializable {

    private static final long serialVersionUID = 3089334206779236478L;

    private String value;

    @JsonIgnore
    private String name;
    @JsonIgnore
    private String organization;

    private Email() {
    }

    public Email(String value) {
        setValue(value);
    }

    @JsonGetter
    public String value() {
        return this.value;
    }

    private void setValue(String value) {

        //TODO Add real examples of validation

        if (value == null)
            throw new ValidationException("Email is null");

        if (value.length() < 3)
            throw new ValidationException("Email address should have more chars");

        if (!value.contains("@"))
            throw new ValidationException("Email address is not valid");


        String[] parts = value.split("@");

        this.name = parts[0];
        this.organization = parts[1];

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value) &&
                Objects.equals(name, email.name) &&
                Objects.equals(organization, email.organization);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, name, organization);
    }

    @Override
    public String toString() {
        return new StringBuilder("Email{").
                append("value='").append(value).append('\'').
                append(", name='").append(name).append('\'').
                append(", organization='").append(organization).append('\'').
                append('}').toString();
    }
}
