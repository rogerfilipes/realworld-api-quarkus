package io.github.rogerfilipes.infrastructure.provider;

import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class HashProvider {

    public String encrypt(String password) {
        return BcryptUtil.bcryptHash(password);
    }

    public boolean verify(String password, String hash) {
        return BcryptUtil.matches(password, hash);
    }
}
