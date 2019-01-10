package pl.easyprogramming.bank.domain.user.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.access.AuthorizationServiceException;
import pl.easyprogramming.bank.domain.TestData;
import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.user.model.LoginData;
import pl.easyprogramming.bank.domain.user.model.Password;
import pl.easyprogramming.bank.domain.user.model.RegisterDate;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;
import pl.easyprogramming.bank.domain.user.repository.UserRepository;
import pl.easyprogramming.bank.domain.user.repository.entity.User;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginControlTest {

    @InjectMocks
    private LoginControl loginService;

    @Mock
    private UserRepository userRepository;

    private TestData testData = new TestData();

    @Before
    public void setUp() {

        RegistrationData firstUserRegistrationData = testData.getFirstUserRegistrationData();

        firstUserRegistrationData.setRegisteredDate(new RegisterDate(LocalDateTime.now()));

        when(userRepository.findByEmail(firstUserRegistrationData.email().value()))
                .thenReturn(new User(firstUserRegistrationData));
    }

    @Test
    public void attemptLoginToNonActiveAccountShouldThrowAuthorizationError() {

        //given
        LoginData loginData = testData.getFirstUserLoginData();

        //when
        try {
            loginService.login(loginData.email(), loginData.password());
        } catch (AuthorizationServiceException e) {
            assertTrue(e.getMessage().contains("Account is not active, please contact with support."));
        }
    }

    @Test
    public void attemptLoginToNotExistAccountShouldThrowAuthorizationError() {

        //given
        LoginData loginData = testData.getFirstUserLoginData();
        Email notExistEmail = new Email("bad@email.com");

        //when
        try {
            loginService.login(notExistEmail, loginData.password());
        } catch (AuthorizationServiceException e) {
            assertTrue(e.getMessage().contains("User email not found."));
        }
    }

    @Test
    public void attemptLoginToWithNonEqualsPasswordShouldThrowAuthorizationError() {

        //given
        LoginData loginData = testData.getFirstUserLoginData();
        Password badPassword = new Password("987654321");

        //when
        try {
            loginService.login(loginData.email(), badPassword);
        } catch (AuthorizationServiceException e) {
            assertTrue(e.getMessage().contains("Invalid Password."));
        }
    }
}