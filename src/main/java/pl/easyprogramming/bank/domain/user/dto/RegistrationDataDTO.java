package pl.easyprogramming.bank.domain.user.dto;

import pl.easyprogramming.bank.domain.common.dto.MoneyDTO;

public class RegistrationDataDTO {

    private String firstName;
    private String lastName;

    private MoneyDTO money;

    private String email;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MoneyDTO getMoney() {
        return money;
    }

    public void setMoney(MoneyDTO money) {
        this.money = money;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
