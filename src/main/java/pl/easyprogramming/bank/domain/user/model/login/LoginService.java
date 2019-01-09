package pl.easyprogramming.bank.domain.user.model.login;

import org.springframework.http.ResponseEntity;
import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.user.model.Password;

import java.util.UUID;

public interface LoginService {

    ResponseEntity login(Email email, Password password);
    ResponseEntity logout(UUID uuid);
}
