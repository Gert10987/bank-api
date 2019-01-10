package pl.easyprogramming.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.easyprogramming.bank.domain.TestData;
import pl.easyprogramming.bank.domain.account.repository.AccountRepository;
import pl.easyprogramming.bank.domain.account.repository.entity.Account;
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
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void accountResourceShouldReturn401ForRequestWithoutAuthToken() throws Exception {

        mvc.perform(post("/account/1/charge")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void afterCreatedUserAccountShouldBeCreatedToo() throws Exception {

        //given
        String email = testData.getFirstUserRegistrationData().getEmail();

        //when
        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getFirstUserRegistrationData())))
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

        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getSecondUserRegistrationData())))
                .andExpect(status().isCreated());

        await().until(newUserIsUpdated(email));

        User createdUser = userRepository.findByEmail(email);

        String jwtToken = mvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testData.getSecondUserLoginData())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

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

    private Callable<Boolean> newUserIsUpdated(String email) {
        return () -> {
            return userRepository.findByEmail(email).isActive(); // The condition that must be fulfilled
        };
    }
}

