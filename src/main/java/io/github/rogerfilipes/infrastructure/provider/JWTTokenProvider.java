package io.github.rogerfilipes.infrastructure.provider;

import io.github.rogerfilipes.domain.user.model.UserData;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.RequestScoped;
import java.util.HashSet;
import java.util.List;

@RequestScoped
public class JWTTokenProvider {

    public String generateToken(UserData data) {
        return Jwt.issuer("http://localhost.com/issuer")
                .subject(data.getId().toString())
                .upn(data.getEmail())
                .groups(new HashSet<>(List.of("user")))
                .claim(Claims.email, data.getEmail())
                .claim(Claims.preferred_username, data.getUsername())
                .sign();
    }
}
