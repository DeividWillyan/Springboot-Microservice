package br.com.microservice.usuario.security.config;

import br.com.microservice.token.config.SecurityCredetialsConfig;
import br.com.microservice.token.properties.JwtConfiguration;
import br.com.microservice.token.token.converter.TokenConverter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity public class SecurityConfig extends SecurityCredetialsConfig {

    public SecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration, tokenConverter);
    }
}
