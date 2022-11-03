package io.github.rogerfilipes.domain.authorization;

import io.github.rogerfilipes.domain.exception.ForbiddenException;
import org.casbin.jcasbin.main.Enforcer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.net.URL;
import java.util.UUID;

@ApplicationScoped
public class AuthorizationService {

    private Enforcer enforcer;

    @PostConstruct
    void setup() {
        URL resource = getClass().getClassLoader().getResource("casbin/model.conf");
        assert resource != null;
        enforcer = new Enforcer(resource.getPath());
    }

    public void check(UUID userId, Ownership resource) {
        if(!enforcer.enforce(userId, resource, "any")){
            throw new ForbiddenException(userId.toString());
        }
    }
}
