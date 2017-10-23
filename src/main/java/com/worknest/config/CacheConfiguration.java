package com.worknest.config;

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
            cm.createCache(com.worknest.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlCarro.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlCarroDet.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlCarroHist.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlCarroDetHist.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlIntentoPago.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlParamBanco.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlParamEnvio.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlParamEnvio.class.getName() + ".parametrobancos", jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlParamRespuesta.class.getName(), jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlParamRespuesta.class.getName() + ".parametrobancos", jcacheConfiguration);
            cm.createCache(com.worknest.domain.PlRespuestaBanco.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
