package pl.easyprogramming.bank.domain.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;
import java.math.BigDecimal;

public final class Money implements Serializable {

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal amount;
    private String currency;

    private Money() {
    }

    public Money(BigDecimal amount, String currency) {
        setAmount(amount);
        this.currency = currency;
    }

    private void setAmount(BigDecimal amount) {

        if(amount.compareTo(BigDecimal.ONE) <= 0)
            throw new IllegalArgumentException("Amount of money should be more than 0");

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
}
