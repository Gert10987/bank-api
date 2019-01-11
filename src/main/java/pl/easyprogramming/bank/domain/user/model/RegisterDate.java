package pl.easyprogramming.bank.domain.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public final class RegisterDate implements Serializable {

    private static final long serialVersionUID = -3640892776077697474L;

    private LocalDateTime registerDateValue;

    public RegisterDate(LocalDateTime registerDateValue) {
        this.registerDateValue = registerDateValue;
    }

    public LocalDateTime registeredAt() {
        return registerDateValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterDate that = (RegisterDate) o;
        return Objects.equals(registerDateValue, that.registerDateValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(registerDateValue);
    }

    @Override
    public String toString() {
        return new StringBuilder("RegisterDate{").
                append("registerDateValue=").append(registerDateValue).append('\'').
                append('}').toString();
    }
}
