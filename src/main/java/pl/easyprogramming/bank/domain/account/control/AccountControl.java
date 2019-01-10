package pl.easyprogramming.bank.domain.account.control;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.easyprogramming.bank.domain.account.model.AccountIdentity;
import pl.easyprogramming.bank.domain.account.model.AccountNumber;
import pl.easyprogramming.bank.domain.account.model.AccountService;
import pl.easyprogramming.bank.domain.account.model.PaymantType;
import pl.easyprogramming.bank.domain.account.repository.AccountRepository;
import pl.easyprogramming.bank.domain.account.repository.entity.Account;
import pl.easyprogramming.bank.domain.account.repository.entity.Address;
import pl.easyprogramming.bank.domain.account.repository.entity.Identity;
import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

import javax.jms.Queue;
import javax.validation.ValidationException;
import java.math.BigDecimal;

@Service
@Primary
@Transactional
public class AccountControl implements AccountService {

    private AccountRepository accountRepository;

    private Queue userQueue;

    private JmsTemplate jmsTemplate;

    @Autowired
    public AccountControl(AccountRepository accountRepository, Queue userQueue, JmsTemplate jmsTemplate) {
        this.accountRepository = accountRepository;
        this.userQueue = userQueue;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void charge(AccountIdentity accountIdentity, Money money) {

        Account account = findAccountById(accountIdentity.id());

        account.addPayment(money, PaymantType.DEPOSIT);
        account.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(account.totalMoney(), money, account.defaultCurrency()));
    }

    @Override
    public void transfer(AccountIdentity accountIdentity, AccountNumber otherAccountNumber, Money money) {

        Account account = findAccountById(accountIdentity.id());
        Account destinationAccount = findAccountByAccountNumber(otherAccountNumber.accountNumber());

        if (!account.isActive())
            throw new ValidationException("Lack of account funds");

        if (account.totalMoney().compareTo(money.amount()) <= 0)
            throw new ValidationException("Insufficient amount of funds on the account");

        account.addPayment(money, PaymantType.WITHDRAWALS);
        account.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(account.totalMoney(), money, account.defaultCurrency()));

        destinationAccount.addPayment(money, PaymantType.DEPOSIT);
        destinationAccount.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(destinationAccount.totalMoney(), money, destinationAccount.defaultCurrency()));
    }

    @Override
    public void create(RegistrationData registrationData) {

        AccountNumber accountNumber = new AccountNumber(RandomStringUtils.random(26, false, true));
        Account account = new Account(registrationData, accountNumber);

        Identity identity = new Identity(registrationData.name().firstName(), registrationData.name().lastName(), registrationData.email().value());

        //TODO Add support for addresses
        Address address = new Address("London", "One");

        account.withIdentity(identity);
        account.addAddress(address);

        accountRepository.save(account);

        //send to user queue to assign account id
        jmsTemplate.convertAndSend(userQueue, new AccountIdentity(account.id(), registrationData.email()));
    }

    private BigDecimal calculateNewTotalValueOfMoney(BigDecimal currentTotalValueOfMoney, Money transferAmountOfMoney, String defaultCurrency) {

        Money newTotalMoneyValue = null;

        if (!transferAmountOfMoney.currency().equals(defaultCurrency)) {

            BigDecimal amountOfMoneyAfterConversion = currencyConversion(transferAmountOfMoney, defaultCurrency);

            newTotalMoneyValue = new Money(currentTotalValueOfMoney.add(amountOfMoneyAfterConversion), defaultCurrency);

        } else {
            if (currentTotalValueOfMoney != null)
                newTotalMoneyValue = new Money(currentTotalValueOfMoney.add(transferAmountOfMoney.amount()), defaultCurrency);
            else
                newTotalMoneyValue = new Money(transferAmountOfMoney.amount(), defaultCurrency);
        }

        return newTotalMoneyValue.amount();
    }

    private BigDecimal currencyConversion(Money tranferMoney, String defaultCurrency) {

        //TODO Add conversion betweeen currencies, request to other service
        return tranferMoney.amount();
    }

    private Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Account Not Found"));
    }

    private Account findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalStateException("Account Not Found"));
    }
}
