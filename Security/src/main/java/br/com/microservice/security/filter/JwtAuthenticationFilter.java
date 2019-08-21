package br.com.microservice.security.filter;

import br.com.microservice.token.model.ApplicationUser;
import br.com.microservice.token.properties.JwtConfiguration;
import br.com.microservice.token.token.creator.TokenCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final TokenCreator tokenCreator;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("Attempting authentication ... ");
        ApplicationUser
                applicationUser = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
        if (applicationUser == null) {
            throw new UsernameNotFoundException("Unable to retrieve the username or password.");
        }
        log.info("Creating the authentication object for the user {} and calling UserDatailsServiceImp loadUserByUsername", applicationUser.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword(), Collections.emptyList());
        usernamePasswordAuthenticationToken.setDetails(applicationUser);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        log.info("Authentication was sucessful for the user '{}', generating JWE Token", auth.getName());
        SignedJWT signedJWT = this.tokenCreator.createSignedJWT(auth);
        String encryptToken = this.tokenCreator.encryptToken(signedJWT);
        log.info("Token generated sucessfully, adding it to the response header.");
        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + this.jwtConfiguration.getHeader().getName());
        response.addHeader(jwtConfiguration.getHeader().getName(), this.jwtConfiguration.getHeader().getPrefix() + encryptToken);
    }

}