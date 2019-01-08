package pl.easyprogramming.bank.domain.account.model;

public final class AccountNumber {

    private String prefix;
    private String accountNumberValue;

    public AccountNumber(String generatedAccountNumber) {
        this.prefix = generatedAccountNumber.substring(0, 2);
        this.accountNumberValue = generatedAccountNumber;
    }

    public String prefix() {
        return prefix;
    }

    public String accountNumber() {
        return accountNumberValue;
    }
}
