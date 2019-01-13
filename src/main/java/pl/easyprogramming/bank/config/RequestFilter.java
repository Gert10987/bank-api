package pl.easyprogramming.bank.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.easyprogramming.bank.domain.user.repository.ExpireTokenRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

public class RequestFilter implements Filter {

    @Value("${secret-key}")
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
        HttpServletResponse response = (HttpServletResponse) res;

        String errorMessage = authorizeRequest(request, response);
        if (errorMessage != null)
            send401ErrorResponse(response, errorMessage);
        else
            chain.doFilter(req, res);
    }

    private String authorizeRequest(HttpServletRequest request, HttpServletResponse response) {

        String errorMessage = null;

        String authHeader = request.getHeader("authorization");

        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            errorMessage = "Missing or invalid Authorization header";
        } else {
            String token = authHeader.substring(7);
            Claims claims = null;

            try {
                claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            } catch (RuntimeException e) {
                errorMessage = "Invalid token";
            }

            if (errorMessage == null) {

                UUID uuid = UUID.fromString(claims.get("id").toString());
                String accountId = claims.get("account_id").toString();
                LocalDateTime expTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(claims.get("exp").toString())), TimeZone.getDefault().toZoneId());

                //TODO Add authorization rules

                if (expireTokenRepository.existsById(uuid)) {
                    errorMessage = "Token expired";
                }
            }
        }

        return errorMessage;
    }

    private void send401ErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }
}