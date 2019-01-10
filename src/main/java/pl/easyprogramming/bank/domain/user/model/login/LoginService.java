package pl.easyprogramming.bank.domain.user.model.login;

import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.user.model.Password;

import java.util.UUID;

public interface LoginService {

    String login(Email email, Password password);

    boolean logout(UUID uuid);
}
