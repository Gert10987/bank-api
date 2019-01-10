package pl.easyprogramming.bank.domain.user.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.easyprogramming.bank.domain.user.model.LoginData;
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
    public ResponseEntity register(@RequestBody RegistrationData registrationData) {

        return registrationService.register(registrationData);
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public ResponseEntity login(@RequestBody LoginData loginData) {

        return loginService.login(loginData.email(), loginData.password());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "logout")
    public ResponseEntity logout(@RequestParam String uuid) {

        return loginService.logout(UUID.fromString(uuid));
    }
}
