package com.joabio.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("api/clients/**").authenticated()  // Protege os endpoints api/clients
                .anyRequest().permitAll()  // Permite outros endpoints sem autenticação
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User
            .withUsername("user1")
            .password("{noop}password1")
            .roles("USER")
            .build();
    
        UserDetails user2 = User
            .withUsername("user2")
            .password("{noop}password2")
            .roles("ADMIN")
            .build();
    
        return new InMemoryUserDetailsManager(user1, user2);
    }
    
}
