package com.akashmailapalli.bike_spare_parts_store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.akashmailapalli.bike_spare_parts_store.security.CustomUserDetailsService;
import com.akashmailapalli.bike_spare_parts_store.security.JwtFilter;

@Configuration
public class SecurityConfig {


    
    private final CustomUserDetailsService customUserDetailsService;

    private final JwtFilter jwtFilter;


    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.requestMatchers("/users/register","users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/").hasAnyRole("CUSTOMER", "ADMIN")  // Customers can view products
                .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")  // Only admins can add products
                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")  // Only admins can update products
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")  // Only admins can delete products
        .anyRequest().authenticated())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());
        
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
