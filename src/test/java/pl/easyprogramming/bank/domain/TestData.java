package pl.easyprogramming.bank.domain;

import pl.easyprogramming.bank.domain.common.dto.MoneyDTO;
import pl.easyprogramming.bank.domain.user.dto.LoginDataDTO;
import pl.easyprogramming.bank.domain.user.dto.RegistrationDataDTO;

public class TestData {

    private RegistrationDataDTO firstUserRegistrationData;
    private LoginDataDTO firstUserLogin;
    private MoneyDTO firstUserDepositPayment;

    private RegistrationDataDTO secondUserRegistrationData;
    private LoginDataDTO secondUserLogin;
    private MoneyDTO secondUserDepositPayment;

    public TestData() {
        initFirstuserRegisterData();
        initFirstuserLoginData();
        initFirstuserDepositPaymant();

        initSecondUserRegisterData();
        initSecondUserLoginData();
        initSecondUserDepositPaymant();
    }

    public RegistrationDataDTO getFirstUserRegistrationData() {
        return firstUserRegistrationData;
    }

    public LoginDataDTO getFirstUserLoginData() {
        return firstUserLogin;
    }

    public MoneyDTO getFirstUserDepositPaymentData() {
        return firstUserDepositPayment;
    }

    public RegistrationDataDTO getSecondUserRegistrationData() {
        return secondUserRegistrationData;
    }

    public LoginDataDTO getSecondUserLoginData() {
        return secondUserLogin;
    }

    public MoneyDTO getSecondUserDepositPaymentData() {
        return secondUserDepositPayment;
    }

    public void initFirstuserRegisterData() {

        firstUserRegistrationData = new RegistrationDataDTO();

        firstUserRegistrationData.setFirstName("Greg");
        firstUserRegistrationData.setLastName("KER");
        firstUserRegistrationData.setEmail("Gregk@test.com");
        firstUserRegistrationData.setPassword("12345678");
        firstUserRegistrationData.setAmountMoney("10.00");
        firstUserRegistrationData.setCurrency("PLN");
    }

    public void initFirstuserLoginData() {

        firstUserLogin = new LoginDataDTO();

        firstUserLogin.setEmail("Gregk@test.com");
        firstUserLogin.setPassword("12345678");
    }

    public void initFirstuserDepositPaymant() {

        firstUserDepositPayment = new MoneyDTO();

        firstUserDepositPayment.setAmount("11.99");
        firstUserDepositPayment.setCurrency("PLN");
    }

    public void initSecondUserRegisterData() {

        secondUserRegistrationData = new RegistrationDataDTO();

        secondUserRegistrationData.setFirstName("Michael");
        secondUserRegistrationData.setLastName("Gas");
        secondUserRegistrationData.setEmail("Michael@test.com");
        secondUserRegistrationData.setPassword("87654321");
        secondUserRegistrationData.setAmountMoney("50.10");
        secondUserRegistrationData.setCurrency("PLN");
    }

    public void initSecondUserLoginData() {

        secondUserLogin = new LoginDataDTO();

        secondUserLogin.setEmail("Michael@test.com");
        secondUserLogin.setPassword("87654321");
    }

    public void initSecondUserDepositPaymant() {

        secondUserDepositPayment = new MoneyDTO();

        secondUserDepositPayment.setAmount("200.00");
        secondUserDepositPayment.setCurrency("PLN");
    }
}
