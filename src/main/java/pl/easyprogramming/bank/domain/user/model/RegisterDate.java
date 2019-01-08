package pl.easyprogramming.bank.domain.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class RegisterDate implements Serializable {

    private static final long serialVersionUID = -3640892776077697474L;

    private LocalDateTime registerDateValue;

    public RegisterDate(LocalDateTime registerDateValue) {
        this.registerDateValue = registerDateValue;
    }

    public LocalDateTime registeredAt() {
        return registerDateValue;
    }
}
