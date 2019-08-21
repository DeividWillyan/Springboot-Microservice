package br.com.microservice.security.config;

import br.com.microservice.security.filter.JwtAuthenticationFilter;
import br.com.microservice.token.config.SecurityTokenConfig;
import br.com.microservice.token.filter.JwtTokenAuthorizationFilter;
import br.com.microservice.token.properties.JwtConfiguration;
import br.com.microservice.token.token.converter.TokenConverter;
import br.com.microservice.token.token.creator.TokenCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityCredetialsConfig extends SecurityTokenConfig {

    private final UserDetailsService userDetailsService;
    private final TokenCreator tokenCreator;
    private final TokenConverter tokenConverter;

    public SecurityCredetialsConfig(
            JwtConfiguration jwtConfiguration,
            @Qualifier("userDatailsServiceImp") UserDetailsService userDetailsService,
            TokenCreator tokenCreator,
            TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.userDetailsService = userDetailsService;
        this.tokenCreator = tokenCreator;
        this.tokenConverter = tokenConverter;
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfiguration, tokenCreator))
            .addFilterAfter(new JwtTokenAuthorizationFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
