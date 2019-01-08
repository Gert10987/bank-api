package pl.easyprogramming.bank.domain.user.model;

import javax.validation.ValidationException;
import java.io.Serializable;

public final class Email implements Serializable {

    private String emailValue;
    private String name;
    private String organization;

    public Email(String emailValue) {
        setEmailValue(emailValue);

        String[] parts = this.emailValue.split("@");

        this.name = parts[0];
        this.organization = parts[1];
    }

    public String emailValue(){
        return this.emailValue;
    }

    private void setEmailValue(String emailValue){

        if (emailValue == null)
            throw new ValidationException("Email is null");

        if (emailValue.length() < 3)
            throw new ValidationException("Email address should have more chars");

        if (!emailValue.contains("@"))
            throw new ValidationException("Email address is not valid");

        this.emailValue = emailValue;
    }

    @Override
    public String toString() {
        return "Email{" +
                "fullEmail='" + emailValue + '\'' +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }
}
