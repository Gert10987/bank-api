package pl.easyprogramming.bank.domain.account.repository.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Money {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal amount;

    @Column
    private String currency;
}
