package pl.easyprogramming.bank.domain.user.model;

import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.common.model.Name;
import pl.easyprogramming.bank.domain.user.dto.RegistrationDataDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class RegistrationData implements Serializable {

    private Name name;
    private Email email;
    private Money money;
    private RegisterDate registeredDate;

    private transient Password password;

    public RegistrationData(RegistrationDataDTO registrationDataDTO) {

        this.name = new Name(registrationDataDTO.getFirstName(), registrationDataDTO.getLastName());
        this.email = new Email(registrationDataDTO.getEmail());
        this.password = new Password(registrationDataDTO.getPassword());
        this.money = new Money(new BigDecimal(registrationDataDTO.getAmountMoney()), registrationDataDTO.getCurrency());

        this.registeredDate = new RegisterDate(LocalDateTime.now());
    }

    public Email email() {
        return email;
    }

    public Password password() {
        return password;
    }

    public Name name() {
        return name;
    }

    public Money money() {
        return money;
    }

    public RegisterDate registeredDate() {
        return registeredDate;
    }
}
