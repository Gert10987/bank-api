package pl.easyprogramming.bank.domain.account.repository.entity;

import pl.easyprogramming.bank.domain.account.model.Account;
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
public class AccountEntity {

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
    private IdentityEntity identityEntity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Set<PaymentEntity> paymentEntities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Set<AddressEntity> addressEntities = new HashSet<>();

    private AccountEntity() {
    }

    public AccountEntity(RegistrationData registrationData, AccountNumber accountNumber) {

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

    public void withIdentity(IdentityEntity identityEntity) {
        this.identityEntity = identityEntity;
    }

    public void addAddress(AddressEntity addressEntity) {
        this.addressEntities.add(addressEntity);
    }

    public boolean isActive() {
        return this.totalMoney != null;
    }

    public String defaultCurrency() {
        return defualtCurrency;
    }

    public void addPayment(Money money, PaymantType type) {

        PaymentEntity paymentEntityEntity = new PaymentEntity(money, type);

        paymentEntities.add(paymentEntityEntity);
    }

    public Set<PaymentEntity> payments() {
        return paymentEntities;
    }

    public IdentityEntity identity() {
        return identityEntity;
    }

    public Account createModel(){

        AccountNumber accountNumber = new AccountNumber(this.accountNumber);
        Name name = new Name(this.identityEntity.firstName(), this.identityEntity.lastName());
        Money money = new Money(this.totalMoney, this.defualtCurrency);

        Account res = new Account(accountNumber, this.registeredDateTime, name, money);

        res.setPayments(this.paymentEntities.stream().map(PaymentEntity::createModel).collect(Collectors.toSet()));
        res.setAddresses(this.addressEntities.stream().map(AddressEntity::createModel).collect(Collectors.toSet()));

        return res;
    }
}
