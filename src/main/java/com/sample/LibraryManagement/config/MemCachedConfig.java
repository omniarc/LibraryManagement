package com.sample.LibraryManagement.config;

import lombok.Value;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableCaching
public class MemcachedConfig {

    @Value("${memcached.servers}")
    private String memcachedServers;

    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses(memcachedServers)
        );
        builder.setConnectionPoolSize(5);
        return builder.build();
    }

    @Bean
    public CacheManager cacheManager(MemcachedClient memcachedClient) {
        return new MemcachedCacheManager(memcachedClient);
    }
}

