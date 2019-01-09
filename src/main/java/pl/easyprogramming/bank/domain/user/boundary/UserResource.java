package pl.easyprogramming.bank.domain.user.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.easyprogramming.bank.domain.user.dto.LoginDataDTO;
import pl.easyprogramming.bank.domain.user.dto.RegistrationDataDTO;
import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.user.model.Password;
import pl.easyprogramming.bank.domain.user.model.RegistrationData;
import pl.easyprogramming.bank.domain.user.model.login.LoginService;
import pl.easyprogramming.bank.domain.user.model.registration.RegistrationService;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserResource {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST, value = "register")
    public ResponseEntity register(@RequestBody RegistrationDataDTO registrationDataDTO) {

        return registrationService.register(new RegistrationData(registrationDataDTO));
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public ResponseEntity login(@RequestBody LoginDataDTO loginDataDTO) {

        return loginService.login(new Email(loginDataDTO.getEmail()), new Password(loginDataDTO.getPassword()));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "logout")
    public ResponseEntity logout(@RequestParam String uuid) {

        return loginService.logout(UUID.fromString(uuid));
    }
}
