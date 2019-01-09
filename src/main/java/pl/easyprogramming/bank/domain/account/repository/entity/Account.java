package pl.easyprogramming.bank.domain.account.repository.entity;

import pl.easyprogramming.bank.domain.account.model.AccountNumber;
import pl.easyprogramming.bank.domain.account.model.Money;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 26, updatable = false)
    private String accountNumber;

    @Column
    private LocalDateTime registeredDateTime;

    @Column
    private String defualtCurrency;

    @Column
    private BigDecimal totalMoney;

    @OneToOne(cascade = CascadeType.ALL)
    private Identity identity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Set<Address> addresses = new HashSet<>();

    private Account() { }

    public Account(RegistrationData registrationData, AccountNumber accountNumber) {

        this.registeredDateTime = registrationData.registeredDate().registeredAt();
        this.defualtCurrency = registrationData.money().currency();

        this.accountNumber = accountNumber.accountNumber();
    }

    public BigDecimal totalMoney() {
        return totalMoney;
    }

    public void updateTotalValueOfMoney(BigDecimal totalMoney){
        this.totalMoney = totalMoney;
    }

    public void withIdentity(Identity identity) {
        this.identity = identity;
    }

    public void addAddress(Address address){
        this.addresses.add(address);
    }

    public boolean isActive() {
        return this.totalMoney != null;
    }

    public void addWithdrawals(pl.easyprogramming.bank.domain.account.model.Money money){

        Money negateMoney = new Money(money.amount().negate(), money.currency());

        Payment paymentEntity = new Payment(negateMoney);

        payments.add(paymentEntity);
    }

    public void addDepositPaymant(pl.easyprogramming.bank.domain.account.model.Money money){

        Payment paymentEntity = new Payment(money);

        payments.add(paymentEntity);
    }
}
