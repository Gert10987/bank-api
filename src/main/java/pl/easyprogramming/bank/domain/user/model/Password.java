package pl.easyprogramming.bank.domain.user.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.validation.ValidationException;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return new StringBuilder("Password{").
                append("value='").append(value).append('\'').
                append('}').toString();
    }
}
