package pl.easyprogramming.bank.domain.user.model;

import pl.easyprogramming.bank.domain.account.model.Money;
import pl.easyprogramming.bank.domain.user.dto.RegistrationDataDTO;

import java.io.Serializable;

public final class RegistrationData implements Serializable {

    private Name name;
    private Email email;
    private Money money;

    private transient Password password;

    public RegistrationData(RegistrationDataDTO registrationDataDTO) {

        this.name = new Name(registrationDataDTO.getFirstName(), registrationDataDTO.getLastName());
        this.email = new Email(registrationDataDTO.email());
        this.password = new Password(registrationDataDTO.password());
        this.money = new Money(registrationDataDTO.getAmountMoney(), registrationDataDTO.getCurrency());
    }

    public Email email() {
        return email;
    }

    public Password password() {
        return password;
    }

    public Name getName() {
        return name;
    }

    public Money getMoney() {
        return money;
    }
}
