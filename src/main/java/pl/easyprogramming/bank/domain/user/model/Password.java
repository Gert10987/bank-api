package pl.easyprogramming.bank.domain.user.model;

import javax.validation.ValidationException;

public final class Password {

    private String passwordValue;

    public Password(String passwordValue) {
        setPassword(passwordValue);
    }

    public String passwordValue() {
        return passwordValue;
    }

    private void setPassword(String passwordValue) {

        if (passwordValue == null)
            throw new ValidationException("Password is null");

        if (passwordValue.length() < 8)
            throw new ValidationException("Password is not enough strong");

        this.passwordValue = passwordValue;
    }

    @Override
    public String toString() {
        return "Password{" +
                "passwordValue='" + passwordValue + '\'' +
                '}';
    }
}
