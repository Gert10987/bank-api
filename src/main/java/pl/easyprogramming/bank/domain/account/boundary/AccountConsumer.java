package pl.easyprogramming.bank.domain.account.boundary;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.easyprogramming.bank.domain.account.model.AccountNumber;
import pl.easyprogramming.bank.domain.account.repository.AccountRepository;
import pl.easyprogramming.bank.domain.account.repository.entity.Account;
import pl.easyprogramming.bank.domain.account.repository.entity.Address;
import pl.easyprogramming.bank.domain.account.repository.entity.Identity;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

@Component
public class AccountConsumer {

    private AccountRepository accountRepository;

    @Autowired
    public AccountConsumer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @JmsListener(destination = "AccountQueue")
    @Transactional
    public void consume(RegistrationData registrationData) {

        persistNewAccount(registrationData);
    }

    private void persistNewAccount(RegistrationData registrationData) {

        AccountNumber accountNumber = new AccountNumber(RandomStringUtils.random(26, false, true));
        Account account = new Account(registrationData, accountNumber);

        Identity identity = new Identity(registrationData.name().firstName(), registrationData.name().lastName(), registrationData.email().emailValue());
        Address address = new Address("London", "One");

        account.withIdentity(identity);
        account.addAddress(address);

        accountRepository.save(account);
    }
}
