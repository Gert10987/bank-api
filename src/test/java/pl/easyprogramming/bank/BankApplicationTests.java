package pl.easyprogramming.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.easyprogramming.bank.domain.TestData;
import pl.easyprogramming.bank.domain.account.repository.AccountRepository;
import pl.easyprogramming.bank.domain.account.repository.entity.AccountEntity;
import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.user.model.LoginData;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;
import pl.easyprogramming.bank.domain.user.repository.UserRepository;
import pl.easyprogramming.bank.domain.user.repository.entity.User;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BankApplication.class)
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class BankApplicationTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Value("${secret-key}")
    private String secret;

    @Autowired
    private ObjectMapper objectMapper;

    private TestData testData = new TestData();

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void clean() {
        userRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void accountResourceShouldReturn401ForRequestWithoutAuthToken() throws Exception {

        //when
        mvc.perform(post("/account/1/charge")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void afterCreatedUserAccountShouldBeCreatedToo() throws Exception {

        //given
        RegistrationData registrationData = testData.getFirstUserRegistrationData();

        //when
        registerUser(registrationData)
                .andExpect(status().isCreated());

        await().until(newUserIsUpdated(registrationData.email()));

        //then
        User createdUser = userRepository.findByEmail(registrationData.email().value());

        assertTrue(createdUser.isActive());
    }

    @Test
    public void afterChargeAccountTotalValueOfMoneyShouldBeIncrease() throws Exception {

        //given
        RegistrationData secondUserRegistrationData = testData.getSecondUserRegistrationData();

        registerUser(secondUserRegistrationData);

        await().until(newUserIsUpdated(secondUserRegistrationData.email()));

        User createdUser = userRepository.findByEmail(secondUserRegistrationData.email().value());

        String jwtToken = loginUser(testData.getSecondUserLoginData());

        AccountEntity accountEntity = accountRepository.findById(createdUser.accountId())
                .orElse(null);

        BigDecimal totalValueOfMoneyBeforeCharge = accountEntity.totalMoney();

        //when
        mvc.perform(put("/account/" + createdUser.accountId() + "/charge")
                .header("authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getSecondUserDepositPaymentData())))
                .andExpect(status().isOk());

        //then
        accountEntity = accountRepository.findById(createdUser.accountId())
                .orElse(null);

        assertEquals(totalValueOfMoneyBeforeCharge.add(testData.getSecondUserDepositPaymentData().amount()), accountEntity.totalMoney());
    }


    @Test
    public void afterTransferTotalValueOfMoneyOtherAccountShouldBeIncrease() throws Exception {

        //given
        RegistrationData firstUserRegistrationData = testData.getFirstUserRegistrationData();
        RegistrationData secondUserRegistrationData = testData.getSecondUserRegistrationData();

        LoginData firstUserLoginData = testData.getFirstUserLoginData();

        registerUser(firstUserRegistrationData);

        await().until(newUserIsUpdated(firstUserRegistrationData.email()));

        registerUser(secondUserRegistrationData);

        await().until(newUserIsUpdated(secondUserRegistrationData.email()));

        String jwtToken = loginUser(firstUserLoginData);

        User firstUser = userRepository.findByEmail(firstUserLoginData.email().value());
        User secondUser = userRepository.findByEmail(secondUserRegistrationData.email().value());

        String secondUserAccountNumber = accountRepository.findAccountNumberById(secondUser.accountId());
        AccountEntity secondUserAccountEntity = accountRepository.findByAccountNumber(secondUserAccountNumber)
                .orElse(null);

        BigDecimal totalValueOfMoneyBeforeTransfer = secondUserAccountEntity.totalMoney();

        //when
        mvc.perform(put("/account/" + firstUser.accountId() + "/transfer/" + secondUserAccountNumber + "/charge")
                .header("authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getFirstUserTransfertPaymentData())))
                .andExpect(status().isOk());

        //then
        secondUserAccountEntity = accountRepository.findByAccountNumber(secondUserAccountNumber)
                .orElse(null);

        assertEquals(totalValueOfMoneyBeforeTransfer.add(testData.getFirstUserTransfertPaymentData().amount()), secondUserAccountEntity.totalMoney());
    }


    @Test
    public void afterChargeAccountPaymentShouldBePersist() throws Exception {

        //given
        RegistrationData secondUserRegistrationData = testData.getSecondUserRegistrationData();

        registerUser(secondUserRegistrationData);

        await().until(newUserIsUpdated(secondUserRegistrationData.email()));

        User createdUser = userRepository.findByEmail(secondUserRegistrationData.email().value());

        String jwtToken = loginUser(testData.getSecondUserLoginData());

        //when
        mvc.perform(put("/account/" + createdUser.accountId() + "/charge")
                .header("authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getSecondUserDepositPaymentData())))
                .andExpect(status().isOk());

        //then
        AccountEntity accountEntity = accountRepository.findById(createdUser.accountId())
                .orElse(null);

        assertTrue(accountEntity.payments().stream()
                .anyMatch(payment ->
                        payment.amount().compareTo(testData.getSecondUserDepositPaymentData().amount()) == 0));
    }

    private String loginUser(LoginData loginData) throws Exception {
        return mvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginData)))
                .andReturn().getResponse().getContentAsString();
    }

    private ResultActions registerUser(RegistrationData registrationData) throws Exception {
        return mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationData)));
    }

    private Callable<Boolean> newUserIsUpdated(Email email) {
        return () -> {
            return userRepository.findByEmail(email.value()).isActive(); // The condition that must be fulfilled
        };
    }
}

