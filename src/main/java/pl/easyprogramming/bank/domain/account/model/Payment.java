package pl.easyprogramming.bank.domain.account.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import pl.easyprogramming.bank.domain.common.model.Money;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(money, payment.money) &&
                Objects.equals(registeredDateTime, payment.registeredDateTime) &&
                type == payment.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(money, registeredDateTime, type);
    }

    @Override
    public String toString() {
        return new StringBuilder("Payment{").
                append("money=").append(money).
                append(", registeredDateTime=").append(registeredDateTime).
                append(", type=").append(type).
                append('}').toString();
    }
}
