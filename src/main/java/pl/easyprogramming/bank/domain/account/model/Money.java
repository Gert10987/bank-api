package pl.easyprogramming.bank.domain.account.model;

import java.io.Serializable;

public final class Money implements Serializable {

    private String amount;
    private String currency;

    public Money(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public String amount() {
        return amount;
    }

    public String currency() {
        return currency;
    }
}
