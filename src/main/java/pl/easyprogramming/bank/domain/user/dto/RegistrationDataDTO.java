package pl.easyprogramming.bank.domain.user.dto;

public class RegistrationDataDTO {

    private String firstName;
    private String lastName;

    private String amountMoney;
    private String currency;

    private String email;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String email(){
        return this.email;
    }

    public String password(){
        return this.password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMoney(String money) {
        this.amountMoney = money;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
