package pl.easyprogramming.bank.domain.account.repository.entity;

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

    @Column
    private LocalDateTime registeredDateTime;

    @Column
    private String defualtCurrency;

    @Column
    private BigDecimal totalMoney;

    @OneToOne(cascade = CascadeType.ALL)
    private Identity identity;

    @OneToMany
    @JoinColumn(name = "account_id")
    private Set<Money> amountOfEachCurrencies = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Set<Address> addresses = new HashSet<>();

    public Account(RegistrationData registrationData) {

        this.registeredDateTime = registrationData.registeredDate().registeredAt();
        this.defualtCurrency = registrationData.money().currency();
    }

    public void withIdentity(Identity identity) {
        this.identity = identity;
    }

    public void addAddress(Address address){
        this.addresses.add(address);
    }
}
