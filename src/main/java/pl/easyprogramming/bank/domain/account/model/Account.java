package pl.easyprogramming.bank.domain.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.common.model.Name;

import java.time.LocalDateTime;
import java.util.Set;

public final class Account {

    private AccountNumber accountNumber;

    private LocalDateTime registeredDateTime;

    private Name name;

    private Money totalMoney;

    private Set<pl.easyprogramming.bank.domain.account.model.Payment> payments;

    private Set<pl.easyprogramming.bank.domain.account.model.Address> addresses;

    private Account() {
    }

    public Account(AccountNumber accountNumber, LocalDateTime registeredDateTime, Name name, Money totalMoney) {
        this.accountNumber = accountNumber;
        this.registeredDateTime = registeredDateTime;
        this.name = name;
        this.totalMoney = totalMoney;
    }

    @JsonGetter
    public Set<pl.easyprogramming.bank.domain.account.model.Payment> payments() {
        return payments;
    }


    @JsonGetter
    public Set<pl.easyprogramming.bank.domain.account.model.Address> addresses() {
        return addresses;
    }

    @JsonGetter
    public AccountNumber accountNumber() {
        return accountNumber;
    }

    @JsonGetter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime registeredDateTime() {
        return registeredDateTime;
    }

    @JsonGetter
    public Name name() {
        return name;
    }

    @JsonGetter
    public Money totalMoney() {
        return totalMoney;
    }

    public void setAddresses(Set<pl.easyprogramming.bank.domain.account.model.Address> addresses) {
        this.addresses = addresses;
    }

    public void setPayments(Set<pl.easyprogramming.bank.domain.account.model.Payment> payments) {
        this.payments = payments;
    }
}
