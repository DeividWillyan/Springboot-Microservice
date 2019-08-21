package br.com.microservice.token.config;

import br.com.microservice.token.filter.JwtTokenAuthorizationFilter;
import br.com.microservice.token.properties.JwtConfiguration;
import br.com.microservice.token.token.converter.TokenConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityCredetialsConfig extends SecurityTokenConfig {

    private final TokenConverter tokenConverter;

    public SecurityCredetialsConfig(
            JwtConfiguration jwtConfiguration,
            TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAfter(new JwtTokenAuthorizationFilter(jwtConfiguration, tokenConverter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

}