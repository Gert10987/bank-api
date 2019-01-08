package pl.easyprogramming.bank.domain.user.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity register(RegistrationData registrationData) {

        if(!userRepository.existsByEmail(registrationData.email().emailValue())){
            userRepository.save(new User(registrationData));

            sendToQueueToCreateNewAccount(registrationData);
        } else {
            throw new IllegalStateException("User with email " + registrationData.email().emailValue() + " exist");
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    private void sendToQueueToCreateNewAccount(RegistrationData registrationData){
        //send to queue to register in account repository
        jmsTemplate.convertAndSend(accountQueue, registrationData);
    }
}
