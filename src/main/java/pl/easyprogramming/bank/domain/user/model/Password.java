package pl.easyprogramming.bank.domain.user.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.validation.ValidationException;

public final class Password {

    private String value;

    private Password() {
    }

    public Password(String value) {
        setPassword(value);
    }

    @JsonGetter
    public String value() {
        return value;
    }

    private void setPassword(String value) {

        if (value == null)
            throw new ValidationException("Password is null");

        if (value.length() < 8)
            throw new ValidationException("Password is not enough strong");

        this.value = value;
    }

    @Override
    public String toString() {
        return "Password{" +
                "value='" + value + '\'' +
                '}';
    }
}
