package pl.easyprogramming.bank.domain.user.control;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.easyprogramming.bank.domain.user.model.Email;
import pl.easyprogramming.bank.domain.user.model.Password;
import pl.easyprogramming.bank.domain.user.model.login.LoginService;
import pl.easyprogramming.bank.domain.user.repository.ExpireTokenRepository;
import pl.easyprogramming.bank.domain.user.repository.UserRepository;
import pl.easyprogramming.bank.domain.user.repository.entity.ExpireToken;
import pl.easyprogramming.bank.domain.user.repository.entity.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@Primary
@Transactional
public class LoginControl implements LoginService {

    @Value("${secret-key}")
    private String secret;

    private UserRepository userRepository;
    private ExpireTokenRepository expireTokenRepository;

    @Autowired
    public LoginControl(UserRepository userRepository, ExpireTokenRepository expireTokenRepository) {
        this.userRepository = userRepository;
        this.expireTokenRepository = expireTokenRepository;
    }

    @Override
    public ResponseEntity<String> login(Email email, Password password) {

        String emailValue = email.emailValue();
        String passwordValue = password.passwordValue();

        User user = userRepository.findByEmail(emailValue);
        if (user == null) {
            throw new AuthorizationServiceException("User email not found.");
        }

        if (!passwordValue.equals(user.password())) {
            throw new AuthorizationServiceException("Invalid Password.");
        }

        if(!user.isActive()){
            throw new AuthorizationServiceException("Account is not active, please contact with support");
        }

        String jwtToken = Jwts.builder().setSubject(emailValue).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret)
                .claim("id", UUID.randomUUID())
                .claim("account_id", user.accountId())
                .claim("email", user.accountId())
                .compact();

        user.lastLoggedAt(LocalDateTime.now());

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @Override
    public ResponseEntity logout(UUID uuid) {
        expireTokenRepository.save(new ExpireToken(uuid));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
