package com.tingisweb.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class for defining a BCryptPasswordEncoder bean.
 */
@Configuration
public class EncoderConfig {

    /**
     * Defines a BCryptPasswordEncoder bean.
     *
     * @return BCryptPasswordEncoder bean for encoding and verifying passwords.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
