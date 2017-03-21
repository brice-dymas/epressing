package com.urservices.epressing.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.urservices.epressing.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.Caracteristique.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.CarteBancaire.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.Categorie.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.Commande.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.LigneCommande.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.Operation.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.Produit.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.Tarif.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.TypeUtilisateur.class.getName(), jcacheConfiguration);
            cm.createCache(com.urservices.epressing.domain.Utilisateur.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
