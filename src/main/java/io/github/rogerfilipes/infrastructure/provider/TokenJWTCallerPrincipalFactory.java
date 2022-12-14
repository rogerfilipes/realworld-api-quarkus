package io.github.rogerfilipes.infrastructure.provider;

import io.smallrye.jwt.auth.principal.*;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
@Alternative
@Priority(1)
public class TokenJWTCallerPrincipalFactory extends JWTCallerPrincipalFactory {

    @Override
    public JWTCallerPrincipal parse(String token, JWTAuthContextInfo authContextInfo) throws ParseException {
        try {
            String json = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8);
            return new DefaultJWTCallerPrincipal(JwtClaims.parse(json));
        } catch (InvalidJwtException ex) {
            throw new ParseException(ex.getMessage());
        }
    }
}