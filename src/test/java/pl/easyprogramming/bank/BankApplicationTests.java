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
import pl.easyprogramming.bank.domain.account.repository.entity.Account;
import pl.easyprogramming.bank.domain.user.dto.LoginDataDTO;
import pl.easyprogramming.bank.domain.user.dto.RegistrationDataDTO;
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
        String email = testData.getFirstUserRegistrationData().getEmail();

        //when
        registerUser(testData.getFirstUserRegistrationData())
                .andExpect(status().isCreated());

        await().until(newUserIsUpdated(email));

        //then
        User createdUser = userRepository.findByEmail(email);

        assertTrue(createdUser.isActive());
    }

    @Test
    public void afterChargeAccountTotalValueOfMoneyShouldBeIncrease() throws Exception {

        //given
        String email = testData.getSecondUserRegistrationData().getEmail();

        registerUser(testData.getSecondUserRegistrationData());

        await().until(newUserIsUpdated(email));

        User createdUser = userRepository.findByEmail(email);

        String jwtToken = loginUser(testData.getSecondUserLoginData());

        Account account = accountRepository.findById(createdUser.accountId())
                .orElse(null);

        BigDecimal totalValueOfMoneyBeforeCharge = account.totalMoney();

        //when
        mvc.perform(put("/account/" + createdUser.accountId() + "/charge")
                .header("authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getSecondUserDepositPaymentData())))
                .andExpect(status().isOk());

        //then
        account = accountRepository.findById(createdUser.accountId())
                .orElse(null);

        assertEquals(totalValueOfMoneyBeforeCharge.add(new BigDecimal(testData.getSecondUserDepositPaymentData().getAmount())), account.totalMoney());
    }


    @Test
    public void afterTransferTotalValueOfMoneyOtherAccountShouldBeIncrease() throws Exception {

        //given
        registerUser(testData.getFirstUserRegistrationData());

        await().until(newUserIsUpdated(testData.getFirstUserRegistrationData().getEmail()));

        registerUser(testData.getSecondUserRegistrationData());

        await().until(newUserIsUpdated(testData.getSecondUserRegistrationData().getEmail()));

        String jwtToken = loginUser(testData.getFirstUserLoginData());

        User firstUser = userRepository.findByEmail(testData.getFirstUserLoginData().getEmail());
        User secondUser = userRepository.findByEmail(testData.getSecondUserLoginData().getEmail());

        String secondUserAccountNumber = accountRepository.findAccountNumberById(secondUser.accountId());
        Account secondUserAccount = accountRepository.findByAccountNumber(secondUserAccountNumber)
                .orElse(null);

        BigDecimal totalValueOfMoneyBeforeTransfer = secondUserAccount.totalMoney();

        //when
        mvc.perform(put("/account/" + firstUser.accountId() + "/transfer/" + secondUserAccountNumber + "/charge")
                .header("authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getFirstUserTransfertPaymentData())))
                .andExpect(status().isOk());

        //then
        secondUserAccount = accountRepository.findByAccountNumber(secondUserAccountNumber)
                .orElse(null);

        assertEquals(totalValueOfMoneyBeforeTransfer.add(new BigDecimal(testData.getFirstUserTransfertPaymentData().getAmount())), secondUserAccount.totalMoney());
    }


    @Test
    public void afterChargeAccountPaymentShouldBePersist() throws Exception {

        //given
        registerUser(testData.getSecondUserRegistrationData());

        await().until(newUserIsUpdated(testData.getSecondUserRegistrationData().getEmail()));

        User createdUser = userRepository.findByEmail(testData.getSecondUserRegistrationData().getEmail());

        String jwtToken = loginUser(testData.getSecondUserLoginData());

        //when
        mvc.perform(put("/account/" + createdUser.accountId() + "/charge")
                .header("authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getSecondUserDepositPaymentData())))
                .andExpect(status().isOk());

        //then
        Account account = accountRepository.findById(createdUser.accountId())
                .orElse(null);

        assertTrue(account.payments().stream()
                .anyMatch(payment ->
                        payment.amount().compareTo(new BigDecimal(testData.getSecondUserDepositPaymentData().getAmount())) == 0));
    }

    private String loginUser(LoginDataDTO firstUserLoginData) throws Exception {
        return mvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstUserLoginData)))
                .andReturn().getResponse().getContentAsString();
    }

    private ResultActions registerUser(RegistrationDataDTO firstUserRegistrationData) throws Exception {
        return mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstUserRegistrationData)));
    }

    private Callable<Boolean> newUserIsUpdated(String email) {
        return () -> {
            return userRepository.findByEmail(email).isActive(); // The condition that must be fulfilled
        };
    }
}

