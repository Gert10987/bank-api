package pl.easyprogramming.bank.domain.user.resources;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class UserResources {

    @Bean
    public Queue accountQueue() {
        return new ActiveMQQueue("AccountQueue");
    }
}
