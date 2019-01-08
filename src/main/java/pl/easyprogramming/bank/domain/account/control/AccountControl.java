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

        Account account = accountRepository.findById(accountId.id())
                .orElseThrow(() -> new IllegalStateException("Account Not Found"));

        Money newTotalMoney;

        if(account.totalMoney() != null){
            newTotalMoney = new Money(money.amount().add(account.totalMoney()), money.currency());
        } else {
            newTotalMoney = new Money(money.amount(), money.currency());
        }

        account.addPayment(money);
        account.chargeTotalMoney(newTotalMoney.amount());
    }

    @Override
    public void transferMoneyToAnotherAccount(AccountId accountId, AccountNumber otherAccountNumber, Money money) {

    }
}
