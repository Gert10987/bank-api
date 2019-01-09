package pl.easyprogramming.bank.domain.account.resource;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class AccountResources {

    @Bean
    public Queue userQueue() {
        return new ActiveMQQueue("UserQueue");
    }
}
