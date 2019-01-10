package pl.easyprogramming.bank.domain;

import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.common.model.Name;
import pl.easyprogramming.bank.domain.user.model.LoginData;
import pl.easyprogramming.bank.domain.user.model.Password;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;
import pl.easyprogramming.bank.domain.user.repository.entity.User;

import java.math.BigDecimal;

public class TestData {

    private RegistrationData firstUserRegistrationData;
    private LoginData firstUserLogin;
    private Money firstUserTransferPayment;
    private User firstUser;

    private RegistrationData secondUserRegistrationData;
    private LoginData secondUserLogin;
    private Money secondUserDepositPayment;
    private User secondUser;

    public TestData() {
        initFirstUserRegisterData();
        initFirstUserLoginData();
        initFirstUserDepositPaymant();

        initSecondUserRegisterData();
        initSecondUserLoginData();
        initSecondUserDepositPayment();
    }

    public RegistrationData getFirstUserRegistrationData() {
        return firstUserRegistrationData;
    }

    public LoginData getFirstUserLoginData() {
        return firstUserLogin;
    }

    public Money getFirstUserTransfertPaymentData() {
        return firstUserTransferPayment;
    }

    public RegistrationData getSecondUserRegistrationData() {
        return secondUserRegistrationData;
    }

    public LoginData getSecondUserLoginData() {
        return secondUserLogin;
    }

    public Money getSecondUserDepositPaymentData() {
        return secondUserDepositPayment;
    }

    public void initFirstUserRegisterData() {

        Name name = new Name("Greg", "KER");
        Email email = new Email("Gregk@test.com");
        Password password = new Password("12345678");
        Money money = new Money(new BigDecimal("10.00"), "PLN");

        firstUserRegistrationData = new RegistrationData(name, email, money, password);
    }

    public void initFirstUserLoginData() {

        firstUserLogin = new LoginData(firstUserRegistrationData.email(), firstUserRegistrationData.password());
    }

    public void initFirstUserDepositPaymant() {

        firstUserTransferPayment = new Money(new BigDecimal("5.00"), "PLN");
    }

    public void initSecondUserRegisterData() {

        Name name = new Name("Michael", "Gas");
        Email email = new Email("Michael@test.com");
        Password password = new Password("87654321");
        Money money = new Money(new BigDecimal("50.10"), "PLN");

        secondUserRegistrationData = new RegistrationData(name, email, money, password);
    }

    public void initSecondUserLoginData() {

        secondUserLogin = new LoginData(secondUserRegistrationData.email(), secondUserRegistrationData.password());
    }

    public void initSecondUserDepositPayment() {

        secondUserDepositPayment = new Money(new BigDecimal("200.00"), "PLN");
    }
}
