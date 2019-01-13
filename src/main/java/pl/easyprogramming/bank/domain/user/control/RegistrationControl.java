package pl.easyprogramming.bank.domain.user.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.easyprogramming.bank.domain.account.model.AccountIdentity;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;
import pl.easyprogramming.bank.domain.user.model.registration.RegistrationService;
import pl.easyprogramming.bank.domain.user.repository.UserRepository;
import pl.easyprogramming.bank.domain.user.repository.entity.User;

import javax.jms.Queue;

@Service
@Primary
@Transactional
public class RegistrationControl implements RegistrationService {

    private UserRepository userRepository;

    private JmsTemplate jmsTemplate;
    private Queue accountQueue;

    @Autowired
    public RegistrationControl(UserRepository userRepository, JmsTemplate jmsTemplate, Queue accountQueue) {

        this.userRepository = userRepository;
        this.jmsTemplate = jmsTemplate;
        this.accountQueue = accountQueue;
    }

    @Override
    public boolean register(RegistrationData registrationData) {

        // TODO Encode password
        if (!userRepository.existsByEmail(registrationData.email().value())) {
            userRepository.save(new User(registrationData));

            sendToQueueToCreateNewAccount(registrationData);
        } else {
            throw new IllegalStateException("User with email " + registrationData.email().value() + " exist");
        }

        return true;
    }

    @Override
    public void assignAccountId(AccountIdentity accountIdentity) {

        User user = userRepository.findByEmail(accountIdentity.email().value());
        user.assignAccountId(accountIdentity.id());
    }

    private void sendToQueueToCreateNewAccount(RegistrationData registrationData) {
        //send to queue to register in account repository
        jmsTemplate.convertAndSend(accountQueue, registrationData);
    }
}
