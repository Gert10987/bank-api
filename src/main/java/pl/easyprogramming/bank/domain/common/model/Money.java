package pl.easyprogramming.bank.domain.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import javax.validation.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public final class Money implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
    private String currency;

    private Money() {
    }

    public Money(BigDecimal amount, String currency) {
        setAmount(amount);
        this.currency = currency;
    }

    private void setAmount(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ONE) <= 0)
            throw new ValidationException("Amount of money should be more than 0");

        this.amount = amount.setScale(2);
    }

    private void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonGetter
    public BigDecimal amount() {
        return amount;
    }

    @JsonGetter
    public String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount) &&
                Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {

        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return new StringBuilder("Money{").
                append("amount=").append(amount).append('\'').
                append(", currency='").append(currency).append('\'').
                append('}').toString();
    }
}
