package pl.easyprogramming.bank.domain.account.model;

import pl.easyprogramming.bank.domain.account.dto.ChargeDTO;

import java.io.Serializable;
import java.math.BigDecimal;

public final class Money implements Serializable {

    private BigDecimal amount;
    private String currency;

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money(ChargeDTO chargeDTO) {
        this.amount = new BigDecimal(chargeDTO.getAmount());
        this.currency = chargeDTO.getCurrency();
    }

    public BigDecimal amount() {
        return amount;
    }

    public String currency() {
        return currency;
    }
}
