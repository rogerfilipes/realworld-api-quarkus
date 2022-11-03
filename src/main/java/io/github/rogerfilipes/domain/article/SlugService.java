package io.github.rogerfilipes.domain.article;

import com.github.slugify.Slugify;
import io.github.rogerfilipes.infrastructure.repository.ArticlesRepository;

import javax.enterprise.context.RequestScoped;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@RequestScoped
public class SlugService {

    final ArticlesRepository articlesRepository;

    public SlugService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public String slugify(final String text) {
        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(text);
        if (articlesRepository.findArticleBySlug(slug).isPresent()) {
            slug = generateRandom(slug);
        }
        return slug;
    }

    private String generateRandom(String text) {
        Slugify slugify = Slugify.builder().build();

        String slug;
        while (true) {
            slug = slugify.slugify(text + randomSuffix().toLowerCase());
            if (articlesRepository.findArticleBySlug(slug).isEmpty())
                return slug;
        }
    }

    private String randomSuffix() {
        byte[] array = new byte[7]; // length is bounded by 7
        try {
            SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
            instanceStrong.nextBytes(array);
            return new String(array, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            throw new SlugServiceException(e);
        }
    }

    class SlugServiceException extends RuntimeException{
        public SlugServiceException(Throwable cause) {
            super(cause);
        }
    }
}
