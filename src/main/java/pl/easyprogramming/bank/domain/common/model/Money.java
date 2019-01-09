package pl.easyprogramming.bank.domain.common.model;

import pl.easyprogramming.bank.domain.common.dto.MoneyDTO;

import java.io.Serializable;
import java.math.BigDecimal;

public final class Money implements Serializable {

    private BigDecimal amount;
    private String currency;

    public Money(BigDecimal amount, String currency) {
        setAmount(amount);
        this.currency = currency;
    }

    public Money(MoneyDTO moneyDTO) {
        this.amount = new BigDecimal(moneyDTO.getAmount());
        this.currency = moneyDTO.getCurrency();
    }

    private void setAmount(BigDecimal amount) {

        if(amount.compareTo(BigDecimal.ONE) <= 0)
            throw new IllegalArgumentException("Amount of money should be more than 0");

        this.amount = amount.setScale(2);
    }

    public BigDecimal amount() {
        return amount;
    }

    public String currency() {
        return currency;
    }
}
