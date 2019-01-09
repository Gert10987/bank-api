package pl.easyprogramming.bank.domain.account.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.easyprogramming.bank.domain.account.model.AccountId;
import pl.easyprogramming.bank.domain.account.model.AccountNumber;
import pl.easyprogramming.bank.domain.account.model.AccountService;
import pl.easyprogramming.bank.domain.account.model.Money;
import pl.easyprogramming.bank.domain.account.repository.AccountRepository;
import pl.easyprogramming.bank.domain.account.repository.entity.Account;

import javax.validation.ValidationException;
import java.math.BigDecimal;

@Service
@Primary
@Transactional
public class AccountControl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountControl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void charge(AccountId accountId, Money money) {

        Account account = findAccountById(accountId.id());

        account.addDepositPaymant(money);
        account.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(account.totalMoney(), money.amount(), money.currency()));
    }

    @Override
    public void transfer(AccountId accountId, AccountNumber otherAccountNumber, Money money) {

        Account account = findAccountById(accountId.id());
        Account destinationAccount = findAccountByAccountNumber(otherAccountNumber.accountNumber());

        if(!account.isActive())
            throw new ValidationException("Lack of account funds");

        if(account.totalMoney().compareTo(money.amount()) <= 0)
            throw new ValidationException("Insufficient amount of funds on the account");

        account.addWithdrawals(money);
        account.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(account.totalMoney(), money.amount().negate(), money.currency()));

        destinationAccount.addDepositPaymant(money);
        destinationAccount.updateTotalValueOfMoney(calculateNewTotalValueOfMoney(destinationAccount.totalMoney(), money.amount(), money.currency()));
    }

    private BigDecimal calculateNewTotalValueOfMoney(BigDecimal currentTotalValue, BigDecimal transferMoney, String currency){

        Money newTotalMoneyValue;

        if(currentTotalValue != null){
            newTotalMoneyValue = new Money(transferMoney.add(currentTotalValue), currency);
        } else {
            newTotalMoneyValue = new Money(transferMoney, currency);
        }
        return newTotalMoneyValue.amount();
    }

    private Account findAccountById(Long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Account Not Found"));
    }

    private Account findAccountByAccountNumber(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalStateException("Account Not Found"));
    }
}
