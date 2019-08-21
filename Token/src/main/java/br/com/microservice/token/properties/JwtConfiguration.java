package br.com.microservice.token.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtConfiguration {

    private String loginUrl = "/login/**";

    @NestedConfigurationProperty
    private Header header = new Header();
    private int expiration = 3600;
    private String privateKey = "Dz2tTncUWSVkOWMG7Bretn0I1Kiy4k5Z";
    private String type = "encrypted";

    @Getter @Setter
    public static class Header {
        private String name = "Authorization";
        private String prefix = "Bearer ";
    }

}