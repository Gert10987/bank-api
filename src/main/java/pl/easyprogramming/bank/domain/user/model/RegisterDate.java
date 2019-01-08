package pl.easyprogramming.bank.domain.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegisterDate implements Serializable {

    private LocalDateTime registerDateValue;

    public RegisterDate(LocalDateTime registerDateValue) {
        this.registerDateValue = registerDateValue;
    }
}
