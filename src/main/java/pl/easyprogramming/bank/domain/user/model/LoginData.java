package pl.easyprogramming.bank.domain.user.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import pl.easyprogramming.bank.domain.common.model.Email;

import java.io.Serializable;

public final class LoginData implements Serializable {

    private static final long serialVersionUID = 6024244549533653937L;

    private Email email;
    private Password password;

    private LoginData() {
    }

    public LoginData(Email email, Password password) {
        this.email = email;
        this.password = password;
    }

    @JsonGetter
    public Email email() {
        return email;
    }

    private void setEmail(Email email) {
        this.email = email;
    }

    @JsonGetter
    public Password password() {
        return password;
    }

    private void setPassword(Password password) {
        this.password = password;
    }
}
