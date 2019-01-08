package pl.easyprogramming.bank.domain.user.model.registration;

import org.springframework.http.ResponseEntity;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;

public interface RegistrationService {

    ResponseEntity register(RegistrationData registrationData);
}
