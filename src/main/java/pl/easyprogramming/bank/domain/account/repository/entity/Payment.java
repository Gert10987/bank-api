package pl.easyprogramming.bank.domain.account.repository.entity;

import pl.easyprogramming.bank.domain.account.model.PaymantType;
import pl.easyprogramming.bank.domain.account.repository.converter.PaymantStatusConverter;
import pl.easyprogramming.bank.domain.common.model.Money;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal amount;

    @Column
    private String currency;

    @Column
    @Convert(converter = PaymantStatusConverter.class)
    private PaymantType paymantType;

    private Payment() { }

    public Payment(Money money, PaymantType paymantType) {

        this.paymantType = paymantType;

        this.currency = money.currency();

        if(paymantType.equals(PaymantType.WITHDRAWALS))
            this.amount = money.amount().negate();
        else
            this.amount = money.amount();
    }
}
