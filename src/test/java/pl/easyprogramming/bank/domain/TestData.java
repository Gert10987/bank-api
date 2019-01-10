package pl.easyprogramming.bank.domain;

import pl.easyprogramming.bank.domain.common.dto.MoneyDTO;
import pl.easyprogramming.bank.domain.user.dto.LoginDataDTO;
import pl.easyprogramming.bank.domain.user.dto.RegistrationDataDTO;

public class TestData {

    private RegistrationDataDTO firstUserRegistrationData;
    private LoginDataDTO firstUserLogin;
    private MoneyDTO firstUserTransferPayment;

    private RegistrationDataDTO secondUserRegistrationData;
    private LoginDataDTO secondUserLogin;
    private MoneyDTO secondUserDepositPayment;

    public TestData() {
        initFirstUserRegisterData();
        initFirstUserLoginData();
        initFirstUserDepositPaymant();

        initSecondUserRegisterData();
        initSecondUserLoginData();
        initSecondUserDepositPayment();
    }

    public RegistrationDataDTO getFirstUserRegistrationData() {
        return firstUserRegistrationData;
    }

    public LoginDataDTO getFirstUserLoginData() {
        return firstUserLogin;
    }

    public MoneyDTO getFirstUserTransfertPaymentData() {
        return firstUserTransferPayment;
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

    public void initFirstUserRegisterData() {

        firstUserRegistrationData = new RegistrationDataDTO();

        firstUserRegistrationData.setFirstName("Greg");
        firstUserRegistrationData.setLastName("KER");
        firstUserRegistrationData.setEmail("Gregk@test.com");
        firstUserRegistrationData.setPassword("12345678");

        MoneyDTO moneyDTO = new MoneyDTO();

        moneyDTO.setAmount("10.00");
        moneyDTO.setCurrency("PLN");

        firstUserRegistrationData.setMoney(moneyDTO);
    }

    public void initFirstUserLoginData() {

        firstUserLogin = new LoginDataDTO();

        firstUserLogin.setEmail("Gregk@test.com");
        firstUserLogin.setPassword("12345678");
    }

    public void initFirstUserDepositPaymant() {

        firstUserTransferPayment = new MoneyDTO();

        firstUserTransferPayment.setAmount("5.00");
        firstUserTransferPayment.setCurrency("PLN");
    }

    public void initSecondUserRegisterData() {

        secondUserRegistrationData = new RegistrationDataDTO();

        secondUserRegistrationData.setFirstName("Michael");
        secondUserRegistrationData.setLastName("Gas");
        secondUserRegistrationData.setEmail("Michael@test.com");
        secondUserRegistrationData.setPassword("87654321");

        MoneyDTO moneyDTO = new MoneyDTO();

        moneyDTO.setAmount("50.10");
        moneyDTO.setCurrency("PLN");

        secondUserRegistrationData.setMoney(moneyDTO);
    }

    public void initSecondUserLoginData() {

        secondUserLogin = new LoginDataDTO();

        secondUserLogin.setEmail("Michael@test.com");
        secondUserLogin.setPassword("87654321");
    }

    public void initSecondUserDepositPayment() {

        secondUserDepositPayment = new MoneyDTO();

        secondUserDepositPayment.setAmount("200.00");
        secondUserDepositPayment.setCurrency("PLN");
    }
}
