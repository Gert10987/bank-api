package pl.easyprogramming.bank.domain.account.control;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.easyprogramming.bank.domain.account.model.*;
import pl.easyprogramming.bank.domain.account.repository.AccountRepository;
import pl.easyprogramming.bank.domain.account.repository.entity.AccountEntity;
import pl.easyprogramming.bank.domain.account.repository.entity.AddressEntity;
import pl.easyprogramming.bank.domain.account.repository.entity.IdentityEntity;
import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

import javax.jms.Queue;
import javax.persistence.EntityNotFoundException;
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

        AccountEntity accountEntity = findAccountById(accountIdentity.id());

        accountEntity.addPayment(money, PaymantType.DEPOSIT);
        accountEntity.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(accountEntity.totalMoney(), money, accountEntity.defaultCurrency()));
    }

    @Override
    public void transfer(AccountIdentity accountIdentity, AccountNumber otherAccountNumber, Money money) {

        AccountEntity accountEntity = findAccountById(accountIdentity.id());
        AccountEntity destinationAccountEntity = findAccountByAccountNumber(otherAccountNumber.number());

        if (!accountEntity.isActive())
            throw new IllegalStateException("Lack of account funds");

        if (accountEntity.totalMoney().compareTo(money.amount()) <= 0)
             throw new IllegalStateException("Insufficient amount of funds on the accountEntity");

        accountEntity.addPayment(money, PaymantType.WITHDRAWALS);
        accountEntity.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(accountEntity.totalMoney(), money, accountEntity.defaultCurrency()));

        destinationAccountEntity.addPayment(money, PaymantType.DEPOSIT);
        destinationAccountEntity.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(destinationAccountEntity.totalMoney(), money, destinationAccountEntity.defaultCurrency()));
    }

    @Override
    public void create(RegistrationData registrationData) {

        AccountNumber accountNumber = new AccountNumber(RandomStringUtils.random(26, false, true));
        AccountEntity accountEntity = new AccountEntity(registrationData, accountNumber);

        IdentityEntity identityEntity = new IdentityEntity(registrationData.name().firstName(), registrationData.name().lastName(), registrationData.email().value());

        //TODO Add support for addresses
        AddressEntity addressEntity = new AddressEntity("London", "One");

        accountEntity.withIdentity(identityEntity);
        accountEntity.addAddress(addressEntity);
        accountEntity.addPayment(registrationData.money(), PaymantType.DEPOSIT);

        accountRepository.save(accountEntity);

        //send to user queue to assign accountEntity id
        jmsTemplate.convertAndSend(userQueue, new AccountIdentity(accountEntity.id(), registrationData.email()));
    }

    @Override
    public Account details(Long accountId) {

        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Not found"));

        return accountEntity.createModel();
    }

    private BigDecimal calculateNewTotalValueOfMoney(BigDecimal currentTotalValueOfMoney, Money transferAmountOfMoney, String defaultCurrency) {

        Money newTotalMoneyValue;

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

    private AccountEntity findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("AccountEntity Not Found"));
    }

    private AccountEntity findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalStateException("AccountEntity Not Found"));
    }
}
