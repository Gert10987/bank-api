package pl.easyprogramming.bank.domain.account.repository.entity;

import pl.easyprogramming.bank.domain.account.model.AccountNumber;
import pl.easyprogramming.bank.domain.account.model.PaymantType;
import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.common.model.Name;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Set<Address> addresses = new HashSet<>();

    private Account() {
    }

    public Account(RegistrationData registrationData, AccountNumber accountNumber) {

        this.registeredDateTime = registrationData.registeredDate().registeredAt();
        this.defualtCurrency = registrationData.money().currency();
        this.totalMoney = registrationData.money().amount();

        this.accountNumber = accountNumber.number();
    }

    public Long id() {
        return id;
    }

    public BigDecimal totalMoney() {
        return totalMoney;
    }

    public void updateTotalValueOfMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void withIdentity(Identity identity) {
        this.identity = identity;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public boolean isActive() {
        return this.totalMoney != null;
    }

    public String defaultCurrency() {
        return defualtCurrency;
    }

    public void addPayment(Money money, PaymantType type) {

        Payment paymentEntity = new Payment(money, type);

        payments.add(paymentEntity);
    }

    public Set<Payment> payments() {
        return payments;
    }

    public Identity identity() {
        return identity;
    }

    public pl.easyprogramming.bank.domain.account.model.Account createModel(){

        AccountNumber accountNumber = new AccountNumber(this.accountNumber);
        Name name = new Name(this.identity.firstName(), this.identity.lastName());
        Money money = new Money(this.totalMoney, this.defualtCurrency);

        pl.easyprogramming.bank.domain.account.model.Account res = new pl.easyprogramming.bank.domain.account.model.Account(accountNumber, this.registeredDateTime, name, money);

        res.setPayments(this.payments.stream().map(payment -> payment.createModel()).collect(Collectors.toSet()));
        res.setAddresses(this.addresses.stream().map(address -> address.createModel()).collect(Collectors.toSet()));

        return res;
    }
}
