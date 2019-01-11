package pl.easyprogramming.bank.domain.account.model;

import pl.easyprogramming.bank.domain.common.model.Email;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountIdentity that = (AccountIdentity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return new StringBuilder("AccountIdentity{").
                append("id=").append(id).append('\'').
                append(", email=").append(email).append('\'').
                append('}').toString();
    }
}
