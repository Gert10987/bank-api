package pl.easyprogramming.bank.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import pl.easyprogramming.bank.domain.user.repository.ExpireTokenRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class RequestFilter implements Filter {

    private String secret;

    private ExpireTokenRepository expireTokenRepository;

    @Autowired
    public RequestFilter(ExpireTokenRepository expireTokenRepository, String secret) {
        this.expireTokenRepository = expireTokenRepository;
        this.secret = secret;
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String authHeader = request.getHeader("authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new AuthorizationServiceException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        UUID uuid;

        try {
            uuid = UUID.fromString(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("id").toString());
        } catch (RuntimeException e) {
            throw new AuthorizationServiceException("Invalid token");
        }

        if(expireTokenRepository.existsById(uuid)){
            throw new AuthorizationServiceException("Token expired");
        }

        chain.doFilter(req, res);
    }
}