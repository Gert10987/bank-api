package pl.easyprogramming.bank.domain.account.repository.entity;

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

    private Payment() { }

    public Payment(pl.easyprogramming.bank.domain.account.model.Money money) {
        this.amount = money.amount();
        this.currency = money.currency();
    }
}
