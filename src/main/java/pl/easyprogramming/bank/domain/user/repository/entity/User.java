package pl.easyprogramming.bank.domain.user.repository.entity;

import pl.easyprogramming.bank.domain.user.model.RegistrationData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime registrationDateTime;
    private LocalDateTime lastLoginDateTime;

    private User() {
    }

    public User(RegistrationData registrationData) {

        this.email = registrationData.email().emailValue();
        this.password = registrationData.password().passwordValue();
        this.registrationDateTime = LocalDateTime.now();
    }

    public String password() {
        return password;
    }

    public void lastLoggedAt(LocalDateTime lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }
}
