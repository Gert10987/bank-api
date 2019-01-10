package pl.easyprogramming.bank.domain.account.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.jms.core.JmsTemplate;
import pl.easyprogramming.bank.domain.TestData;
import pl.easyprogramming.bank.domain.account.model.AccountIdentity;
import pl.easyprogramming.bank.domain.account.model.AccountNumber;
import pl.easyprogramming.bank.domain.account.repository.AccountRepository;
import pl.easyprogramming.bank.domain.account.repository.entity.Account;
import pl.easyprogramming.bank.domain.common.model.Money;
import pl.easyprogramming.bank.domain.user.model.RegisterDate;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControlTest {

    @InjectMocks
    private AccountControl accountControl;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private Queue accountQueue;

    @Mock
    private Account mockAccount;

    private TestData testData = new TestData();

    @Test
    public void createMethodShouldPersistNewAccountBasedOnRegistrationData() {

        //given
        RegistrationData registrationData = testData.getFirstUserRegistrationData();
        registrationData.setRegisteredDate(new RegisterDate(LocalDateTime.now()));

        List<Account> accounts = new ArrayList<>();

        doAnswer((Answer) invocation -> {
            Account createdAccount = (Account) invocation.getArgument(0);
            accounts.add(createdAccount);

            return null;
        }).when(accountRepository).save(any());

        //when
        accountControl.create(registrationData);

        //then
        Account persistedAccount = accounts.get(0);

        assertEquals(persistedAccount.totalMoney(), registrationData.money().amount());
        assertEquals(persistedAccount.defaultCurrency(), registrationData.money().currency());

        assertEquals(persistedAccount.identity().firstName(), registrationData.name().firstName());
        assertEquals(persistedAccount.identity().lastName(), registrationData.name().lastName());
    }

    @Test
    public void createMethodShouldSendAccountIdentityToUserQueue() {

        //given
        RegistrationData registrationData = testData.getFirstUserRegistrationData();
        registrationData.setRegisteredDate(new RegisterDate(LocalDateTime.now()));

        List<AccountIdentity> accountIdentities = new ArrayList<>();

        doAnswer((Answer) invocation -> {
            AccountIdentity createdAccount = (AccountIdentity) invocation.getArgument(1);
            accountIdentities.add(createdAccount);

            return null;
        }).when(jmsTemplate).convertAndSend((Destination) any(), (Object) any());

        //when
        accountControl.create(registrationData);

        //then
        AccountIdentity accountIdentity = accountIdentities.get(0);

        assertEquals(accountIdentity.email(), registrationData.email());
    }

    @Test
    public void attemptTransferMoneyFromAccountWithLackOfFundsShouldThrowValidationError() {

        //given
        RegistrationData registrationData = testData.getSecondUserRegistrationData();
        registrationData.setRegisteredDate(new RegisterDate(LocalDateTime.now()));

        AccountIdentity accountIdentity = new AccountIdentity(1L, testData.getFirstUserLoginData().email());
        Money money = new Money(testData.getFirstUserTransfertPaymentData().amount(), testData.getFirstUserTransfertPaymentData().currency());
        AccountNumber otherAccountNumber = new AccountNumber("12345");

        when(mockAccount.isActive()).thenReturn(false);

        when(accountRepository.findById(any()))
                .thenReturn(java.util.Optional.ofNullable(mockAccount));

        when(accountRepository.findByAccountNumber(any()))
                .thenReturn(java.util.Optional.of(new Account(registrationData, new AccountNumber("898989"))));

        //when
        try {
            accountControl.transfer(accountIdentity, otherAccountNumber, money);
        } catch (ValidationException e) {
            assertTrue(e.getMessage().contains("Lack of account funds"));
        }
    }

    @Test
    public void attemptTransferMoneyFromAccountWithInsufficientAmountOfFundsShouldThrowValidationError() {

        //given
        RegistrationData registrationData = testData.getSecondUserRegistrationData();
        registrationData.setRegisteredDate(new RegisterDate(LocalDateTime.now()));

        AccountIdentity accountIdentity = new AccountIdentity(1L, testData.getFirstUserLoginData().email());
        Money money = new Money(testData.getFirstUserTransfertPaymentData().amount(), testData.getFirstUserTransfertPaymentData().currency());
        AccountNumber otherAccountNumber = new AccountNumber("12345");

        when(mockAccount.isActive()).thenReturn(true);

        when(mockAccount.totalMoney()).thenReturn(money.amount().subtract(new BigDecimal("1.00")));

        when(accountRepository.findById(any()))
                .thenReturn(java.util.Optional.ofNullable(mockAccount));

        when(accountRepository.findByAccountNumber(any()))
                .thenReturn(java.util.Optional.of(new Account(registrationData, new AccountNumber("898989"))));

        //when
        try {
            accountControl.transfer(accountIdentity, otherAccountNumber, money);
        } catch (ValidationException e) {
            assertTrue(e.getMessage().contains("Insufficient amount of funds on the account"));
        }
    }
}