package pl.easyprogramming.bank.domain.user.control;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.easyprogramming.bank.domain.common.model.Email;
import pl.easyprogramming.bank.domain.user.model.Password;
import pl.easyprogramming.bank.domain.user.model.login.LoginService;
import pl.easyprogramming.bank.domain.user.repository.ExpireTokenRepository;
import pl.easyprogramming.bank.domain.user.repository.UserRepository;
import pl.easyprogramming.bank.domain.user.repository.entity.ExpireToken;
import pl.easyprogramming.bank.domain.user.repository.entity.User;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@Primary
@Transactional
public class LoginControl implements LoginService {

    @Value("${secret-key}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpireTokenRepository expireTokenRepository;

    @Override
    public String login(Email email, Password password) {

        String emailValue = email.value();
        String passwordValue = password.value();

        User user = userRepository.findByEmail(emailValue);
        if (user == null) {
            throw new AuthorizationServiceException("User email not found.");
        }

        if (!passwordValue.equals(user.password())) {
            throw new AuthorizationServiceException("Invalid Password.");
        }

        if (!user.isActive()) {
            throw new AuthorizationServiceException("AccountEntity is not active, please contact with support.");
        }

        Date currentDate = new Date();

        String jwtToken = Jwts.builder().setSubject(emailValue)
                .setIssuedAt(currentDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(addMinutesToCurrentDate(30))
                .claim("id", UUID.randomUUID())
                .claim("account_id", user.accountId())
                .compact();

        user.lastLoggedAt(LocalDateTime.now());

        return jwtToken;
    }

    @Override
    public boolean logout(UUID uuid) {
        expireTokenRepository.save(new ExpireToken(uuid));

        return true;
    }

    private Date addMinutesToCurrentDate(int minutesToAdd) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, minutesToAdd);

        return cal.getTime();
    }
}
