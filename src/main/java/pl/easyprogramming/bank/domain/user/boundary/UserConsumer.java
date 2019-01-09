package pl.easyprogramming.bank.domain.user.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.easyprogramming.bank.domain.account.model.AccountIdentity;
import pl.easyprogramming.bank.domain.user.model.registration.RegistrationService;

@Component
public class UserConsumer {

    private RegistrationService registrationService;

    @Autowired
    public UserConsumer(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @JmsListener(destination = "UserQueue")
    public void consume(AccountIdentity accountIdentity) {
        registrationService.assignAccountId(accountIdentity);
    }
}
