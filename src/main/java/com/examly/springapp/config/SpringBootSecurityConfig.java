package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.examly.springapp.service.CustomUserDetialService;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SpringBootSecurityConfig {

    @Bean
    public SecurityFilterChain securityAuth(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .antMatchers(HttpMethod.POST, "/api/reviews").permitAll()
                        .antMatchers("/api/**").authenticated()
                        .antMatchers("/welcome").authenticated()
                        .anyRequest().permitAll())
                .formLogin(
                        form -> form.permitAll().defaultSuccessUrl("/welcome"))
                .csrf(csrf -> csrf.disable()); // Disable CSRF if you're building a REST API

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        // UserDetails user = User.withUsername("alice").password(passwordEncoder.encode("user")).roles("USER").build();
        // UserDetails admin = User.withUsername("john").password(passwordEncoder.encode("admin")).roles("ADMIN").build();
        // return new InMemoryUserDetailsManager(user, admin);

        return new CustomUserDetialService();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthentication(){
        DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
        daoAuth.setUserDetailsService(userDetailsService());
        daoAuth.setPasswordEncoder(passwordEncoder());

        return daoAuth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
