package br.com.microservice.token.token.converter;

import br.com.microservice.token.properties.JwtConfiguration;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenConverter {

    private final JwtConfiguration jwtConfiguration;

    @SneakyThrows
    public String decryptToken(String encyptedToken) {
        log.info("Decrypting token");
        JWEObject jweObject = JWEObject.parse(encyptedToken);
        DirectDecrypter directDecrypter = new DirectDecrypter(this.jwtConfiguration.getPrivateKey().getBytes());
        jweObject.decrypt(directDecrypter);
        log.info("Token decrypted, returning signed token ...");
        return jweObject.getPayload().toSignedJWT().serialize();
    }

    @SneakyThrows
    public void validateTokenSignature(String signedToken) {

        log.info("Starting method to validate token signature...");
        SignedJWT signedJWT = SignedJWT.parse(signedToken);
        log.info("Token parsed! Retrieving public key from signed token.");
        RSAKey rsaKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
        log.info("Public key retrieved, validating signature...");
        if(!signedJWT.verify(new RSASSAVerifier(rsaKey)))
            throw new AccessDeniedException("Invalid token signature.");
        log.info("Token has a valid signature. ;)");
    }

}