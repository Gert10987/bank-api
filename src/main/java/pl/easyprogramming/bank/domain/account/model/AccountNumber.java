package pl.easyprogramming.bank.domain.account.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

public final class AccountNumber {

    private String prefix;
    private String value;

    private AccountNumber() {
    }

    public AccountNumber(String generatedAccountNumber) {
        this.prefix = generatedAccountNumber.substring(0, 2);
        this.value = generatedAccountNumber;
    }

    @JsonGetter
    public String prefix() {
        return prefix;
    }

    @JsonGetter
    public String number() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountNumber that = (AccountNumber) o;
        return Objects.equals(prefix, that.prefix) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(prefix, value);
    }

    @Override
    public String toString() {
        return new StringBuilder("AccountNumber{").
                append("prefix='").append(prefix).append('\'').
                append(", value='").append(value).append('\'').
                append('}').toString();
    }
}
