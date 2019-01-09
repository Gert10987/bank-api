package pl.easyprogramming.bank.domain.account.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.easyprogramming.bank.domain.account.model.AccountService;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

@Component
public class AccountConsumer {

    private AccountService accountService;

    @Autowired
    public AccountConsumer(AccountService accountService) {
        this.accountService= accountService;
    }

    @JmsListener(destination = "AccountQueue")
    public void consume(RegistrationData registrationData) {

        accountService.create(registrationData);
    }
}
