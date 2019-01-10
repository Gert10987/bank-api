package pl.easyprogramming.bank.domain.user.model.registration;

import pl.easyprogramming.bank.domain.account.model.AccountIdentity;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

public interface RegistrationService {

    boolean register(RegistrationData registrationData);

    void assignAccountId(AccountIdentity accountIdentity);
}
