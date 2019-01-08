package pl.easyprogramming.bank.domain.account.boundary;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

@Component
public class AccountConsumer {

    @JmsListener(destination = "AccountQueue")
    public void consume(RegistrationData registrationData) {
        System.out.println(registrationData.email().emailValue() + "qwewqeqwewqewqewqewqewq");
    }
}
