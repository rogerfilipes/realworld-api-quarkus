package io.github.rogerfilipes.security;

import io.github.rogerfilipes.domain.article.model.ArticleData;
import org.casbin.jcasbin.main.Enforcer;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbacTest {


    @Test
    public void testModelArticle() {
        UUID userId = UUID.fromString("80ee0d62-1424-47b0-ba03-51a2f3e3be83");

        URL resource = getClass().getClassLoader().getResource("casbin/model.conf");
        Enforcer enforcer = new Enforcer(resource.getPath());


        ArticleData article = new ArticleData();
        article.setAuthor(userId);

        assertEquals(enforcer.enforce(userId, article, "any"), true);


    }

    @Test
    public void testModelArticleDenied() {
        UUID userId = UUID.fromString("80ee0d62-1424-47b0-ba03-51a2f3e3be83");
        UUID otherUserId = UUID.fromString("e7c0887d-d848-4ea9-a3b2-74641c7cf2d9");

        URL resource = getClass().getClassLoader().getResource("casbin/model.conf");
        Enforcer enforcer = new Enforcer(resource.getPath());

        ArticleData article = new ArticleData();
        article.setAuthor(userId);

        assertEquals(enforcer.enforce(otherUserId, article, "any"), false);

    }

}
