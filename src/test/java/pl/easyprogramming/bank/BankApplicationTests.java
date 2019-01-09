package pl.easyprogramming.bank;

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
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


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
        String email = "Gregk@test.com";

        //when
        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.firstuserRegisterData()))
                .andExpect(status().isCreated());

        await().until(newUserIsUpdated(email));

        //then
        User createdUser = userRepository.findByEmail(email);

        assertTrue(createdUser.isActive());
    }

    @Test
    public void afterChargeAccountTotalValueOfMoneyShouldBeIncrease() throws Exception {

        //given
        String email = "Michael@test.com";

        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.secondUserRegisterData()))
                .andExpect(status().isCreated());

        await().until(newUserIsUpdated(email));

        String jwtToken = mvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.secondUserLoginData()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //when
        mvc.perform(put("/account/1/charge")
                .header("authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestData.secondUserDepositPaymant()))
                .andExpect(status().isOk());

        Account account = accountRepository.findById(1L)
                .orElse(null);

        assertEquals(new BigDecimal("250.10"), account.totalMoney());
    }

    private Callable<Boolean> newUserIsUpdated(String email) {
        return () -> {
            return userRepository.findByEmail(email).isActive(); // The condition that must be fulfilled
        };
    }
}

