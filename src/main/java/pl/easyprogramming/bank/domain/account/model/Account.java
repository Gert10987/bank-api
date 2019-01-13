package pl.easyprogramming.bank.domain.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.common.model.Name;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public final class Account {

    private AccountNumber accountNumber;

    private LocalDateTime registeredDateTime;

    private Name name;

    private Money totalMoney;

    private Set<Payment> payments;

    private Set<Address> addresses;

    private Account() {
    }

    public Account(AccountNumber accountNumber, LocalDateTime registeredDateTime, Name name, Money totalMoney) {
        this.accountNumber = accountNumber;
        this.registeredDateTime = registeredDateTime;
        this.name = name;
        this.totalMoney = totalMoney;
    }

    @JsonGetter
    public Set<Payment> payments() {
        return payments;
    }


    @JsonGetter
    public Set<Address> addresses() {
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

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(registeredDateTime, account.registeredDateTime) &&
                Objects.equals(name, account.name) &&
                Objects.equals(totalMoney, account.totalMoney) &&
                Objects.equals(payments, account.payments) &&
                Objects.equals(addresses, account.addresses);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accountNumber, registeredDateTime, name, totalMoney, payments, addresses);
    }

    @Override
    public String toString() {
        return new StringBuilder("Account{").
                append("accountNumber=").append(accountNumber).
                append(", registeredDateTime=").append(registeredDateTime).
                append(", name=").append(name).
                append(", totalMoney=").append(totalMoney).
                append(", payments=").append(payments).
                append(", addresses=").append(addresses).
                append('}').toString();
    }
}
