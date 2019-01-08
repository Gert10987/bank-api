package pl.easyprogramming.bank.domain.account.model;

public interface AccountService {

    void charge(AccountId accountId, Money money);
    void transferMoneyToAnotherAccount(AccountId accountId, AccountNumber otherAccountNumber, Money money);
}
