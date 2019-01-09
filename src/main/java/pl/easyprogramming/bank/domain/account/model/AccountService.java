package pl.easyprogramming.bank.domain.account.model;

import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

public interface AccountService {

    void charge(AccountIdentity accountIdentity, Money money);
    void transfer(AccountIdentity accountIdentity, AccountNumber otherAccountNumber, Money money);

    void create(RegistrationData registrationData);
}
