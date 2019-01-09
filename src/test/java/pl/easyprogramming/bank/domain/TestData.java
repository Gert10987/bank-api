package pl.easyprogramming.bank.domain;

public class TestData {

    public static String firstuserRegisterData() {

        return "{\n" +
                "    \"firstName\": \"Greg\",\n" +
                "    \"lastName\": \"KER\",\n" +
                "    \"email\": \"Gregk@test.com\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"amountMoney\": \"10.00\",\n" +
                "    \"currency\": \"PLN\"\n" +
                "}";
    }

    public static String firstuserLoginData() {

        return "{\n" +
                "    \"email\": \"Gregk@test.com\",\n" +
                "    \"password\": \"12345678\"\n" +
                "}\n";
    }

    public static String firstuserDepositPaymant() {

        return "{\n" +
                "    \"amount\": \"11.99\",\n" +
                "    \"currency\": \"PLN\"\n" +
                "}";
    }

    public static String secondUserRegisterData() {

        return "{\n" +
                "    \"firstName\": \"Michael\",\n" +
                "    \"lastName\": \"Gas\",\n" +
                "    \"email\": \"Michael@test.com\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"amountMoney\": \"50.10\",\n" +
                "    \"currency\": \"PLN\"\n" +
                "}";
    }

    public static String secondUserLoginData() {

        return "{\n" +
                "    \"email\": \"Michael@test.com\",\n" +
                "    \"password\": \"12345678\"\n" +
                "}\n";
    }

    public static String secondUserDepositPaymant() {

        return "{\n" +
                "    \"amount\": \"200.00\",\n" +
                "    \"currency\": \"PLN\"\n" +
                "}";
    }
}
