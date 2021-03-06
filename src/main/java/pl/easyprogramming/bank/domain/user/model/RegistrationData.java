package pl.easyprogramming.bank.domain.user.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.common.model.Name;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public final class RegistrationData implements Serializable {

    private static final long serialVersionUID = -6058991309438826987L;

    private Name name;
    private Email email;
    private Money money;

    @JsonIgnore
    private RegisterDate registeredDate;

    private transient Password password;

    private RegistrationData() {
        this.registeredDate = new RegisterDate(LocalDateTime.now());
    }

    public RegistrationData(Name name, Email email, Money money, Password password) {
        this.name = name;
        this.email = email;
        this.money = money;
        this.password = password;
    }

    @JsonGetter
    public Email email() {
        return email;
    }

    @JsonGetter
    public Password password() {
        return password;
    }

    @JsonGetter
    public Name name() {
        return name;
    }

    @JsonGetter
    public Money money() {
        return money;
    }

    @JsonGetter
    public RegisterDate registeredDate() {
        return registeredDate;
    }

    private void setName(Name name) {
        this.name = name;
    }

    private void setEmail(Email email) {
        this.email = email;
    }

    private void setMoney(Money money) {
        this.money = money;
    }

    private void setPassword(Password password) {
        this.password = password;
    }

    @JsonIgnore
    public void setRegisteredDate(RegisterDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationData that = (RegistrationData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(money, that.money) &&
                Objects.equals(registeredDate, that.registeredDate) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, email, money, registeredDate, password);
    }

    @Override
    public String toString() {
        return new StringBuilder("RegistrationData{").
                append("name=").append(name).append('\'').
                append(", email=").append(email).append('\'').
                append(", money=").append(money).append('\'').
                append(", registeredDate=").append(registeredDate).append('\'').
                append(", password=").append(password).append('\'').
                append('}').toString();
    }
}
