package pl.easyprogramming.bank.domain.account.model;

import java.util.Objects;

public final class AccountNumber {

    private String prefix;
    private String accountNumberValue;

    public AccountNumber(String generatedAccountNumber) {
        this.prefix = generatedAccountNumber.substring(0, 2);
        this.accountNumberValue = generatedAccountNumber;
    }

    public String prefix() {
        return prefix;
    }

    public String accountNumber() {
        return accountNumberValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountNumber that = (AccountNumber) o;
        return Objects.equals(prefix, that.prefix) &&
                Objects.equals(accountNumberValue, that.accountNumberValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(prefix, accountNumberValue);
    }

    @Override
    public String toString() {
        return new StringBuilder("AccountNumber{").
                append("prefix='").append(prefix).append('\'').
                append(", accountNumberValue='").append(accountNumberValue).append('\'').
                append('}').toString();
    }
}
