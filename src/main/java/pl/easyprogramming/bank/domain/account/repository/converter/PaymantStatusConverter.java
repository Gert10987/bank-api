package pl.easyprogramming.bank.domain.account.repository.converter;

import pl.easyprogramming.bank.domain.account.model.PaymantType;

import javax.persistence.AttributeConverter;

public class PaymantStatusConverter implements AttributeConverter<PaymantType, String> {

    @Override
    public String convertToDatabaseColumn(PaymantType paymantType) {
        return paymantType.name();
    }

    @Override
    public PaymantType convertToEntityAttribute(String paymantType) {
        return PaymantType.valueOf(paymantType);
    }
}
