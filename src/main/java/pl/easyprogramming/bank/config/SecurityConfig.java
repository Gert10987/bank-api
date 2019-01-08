package pl.easyprogramming.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.easyprogramming.bank.domain.user.repository.ExpireTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${secret-key}")
    private String secret;

    @Autowired
    public ExpireTokenRepository expireTokenRepository;

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/users/**");
        webSecurity.ignoring().antMatchers("/h2-console/**");
        webSecurity.ignoring().antMatchers("/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(tokenAuthorizationFilter(), BasicAuthenticationFilter.class)
                .csrf().disable();
    }

    private RequestFilter tokenAuthorizationFilter() {
        return new RequestFilter(expireTokenRepository, secret);
    }
}