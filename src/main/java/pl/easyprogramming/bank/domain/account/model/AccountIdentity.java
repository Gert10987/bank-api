package pl.easyprogramming.bank.domain.account.model;

import pl.easyprogramming.bank.domain.common.model.Email;

import java.io.Serializable;

public final class AccountIdentity implements Serializable {

    private static final long serialVersionUID = 5345935182372743855L;

    private Long id;
    private Email email;

    public AccountIdentity(Long id) {
        this.id = id;
    }

    public AccountIdentity(Long id, Email email) {
        this.id = id;
        this.email = email;
    }

    public Long id() {
        return id;
    }

    public Email email() {
        return email;
    }
}
