package pl.easyprogramming.bank.domain.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import pl.easyprogramming.bank.domain.common.model.Money;

import java.time.LocalDateTime;

public class Payment {

    private Money money;

    private LocalDateTime registeredDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PaymantType type;

    private Payment() {
    }

    public Payment(Money money, LocalDateTime registeredDateTime, PaymantType type) {
        this.money = money;
        this.registeredDateTime = registeredDateTime;
        this.type = type;
    }

    @JsonGetter
    public Money money() {
        return money;
    }

    @JsonGetter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime registeredDateTime() {
        return registeredDateTime;
    }

    @JsonGetter
    public PaymantType type() {
        return type;
    }
}
