package com.jeremy.wenda.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@ConfigurationProperties(
        prefix = "spring.velocity"
)
@Configuration
public class VelocityConfig {
    private Properties properties;
    VelocityConfig(){
        properties = new Properties();
    }

    @Bean
    VelocityEngine velocityEngine(){
        return new VelocityEngine(properties);
    }

    public Properties getProperties() {
        return properties;
    }
}