package com.tingisweb.assignment.security;

import com.tingisweb.assignment.errorhandling.customhandler.CustomAccessDeniedHandler;
import com.tingisweb.assignment.errorhandling.customhandler.CustomAuthenticationHandler;
import com.tingisweb.assignment.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

/**
 * Configuration class that defines security settings for the application.
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    private final UserServiceImpl userServiceImpl;
    private final JWTFilter jwtFilter;
    @Value("${cors.allowedOrigins}")
    private String allowedOrigin;

    /**
     * Constructs a new SecurityConfig instance with the specified dependencies.
     *
     * @param userServiceImpl The UserServiceImpl instance for user-related operations.
     * @param jwtFilter The JWTFilter instance for JWT token handling.
     */
    public SecurityConfig(UserServiceImpl userServiceImpl, JWTFilter jwtFilter) {
        this.userServiceImpl = userServiceImpl;
        this.jwtFilter = jwtFilter;
    }


    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
     *
     * @return CorsConfigurationSource configured with allowed origins, methods, and headers.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigin));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configures security filters and authentication settings for the application.
     *
     * @param http The HttpSecurity instance to configure.
     * @return SecurityFilterChain configured with security settings.
     * @throws Exception Exception If an exception occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(c-> {
                    c.requestMatchers("/auth").permitAll();
                    c.anyRequest().authenticated();
                })
                .userDetailsService(userServiceImpl)
                .exceptionHandling(exception->
                        exception.accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(new CustomAuthenticationHandler()))
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Creates an AuthenticationManager instance.
     *
     * @param authConfiguration The AuthenticationConfiguration for authentication management.
     * @return The AuthenticationManager.
     * @throws Exception If an exception occurs during AuthenticationManager creation.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}
