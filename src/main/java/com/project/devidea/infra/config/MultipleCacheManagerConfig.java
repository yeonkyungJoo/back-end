package com.project.devidea.infra.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.cache.CacheManager;
import java.time.Duration;

@Configuration
@EnableCaching
public class MultipleCacheManagerConfig {

//    @Bean
//    @Primary
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(CacheProperties.Caffeine.newBuilder()
//                .initialCapacity(200)
//                .maximumSize(500)
//                .weakKeys()
//                .recordStats());
//        return (CacheManager) cacheManager;
//    }
//
//    @Bean
//    public CacheManager alternateCacheManager() {
//        return (CacheManager) new ConcurrentMapCacheManager("customerOrders", "orderprice");
//    }
}