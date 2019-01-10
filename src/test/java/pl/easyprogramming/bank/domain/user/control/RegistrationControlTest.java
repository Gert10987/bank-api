package pl.easyprogramming.bank.domain.user.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import pl.easyprogramming.bank.domain.TestData;
import pl.easyprogramming.bank.domain.user.model.RegisterDate;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;
import pl.easyprogramming.bank.domain.user.repository.UserRepository;

import javax.jms.Queue;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControlTest {

    @InjectMocks
    private RegistrationControl registrationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private Queue accountQueue;

    private TestData testData = new TestData();

    @Before
    public void setup() {
        RegistrationData firstUserRegistrationData = testData.getFirstUserRegistrationData();
        firstUserRegistrationData.setRegisteredDate(new RegisterDate(LocalDateTime.now()));

        when(userRepository.existsByEmail(firstUserRegistrationData.email().value()))
                .thenReturn(false);
    }

    @Test
    public void attemptRegisterUserWhichExistShouldThrowIllegalStateError() {

        //given
        RegistrationData registrationData = testData.getFirstUserRegistrationData();

        //when
        try {
            registrationService.register(testData.getFirstUserRegistrationData());
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("User with email " + registrationData.email().value() + " exist"));
        }
    }
}