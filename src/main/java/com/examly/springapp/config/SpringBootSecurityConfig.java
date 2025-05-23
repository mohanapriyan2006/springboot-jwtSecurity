package com.examly.springapp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.examly.springapp.security.JwtFilter;
import com.examly.springapp.service.CustomUserDetialService;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SpringBootSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityAuth(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .antMatchers(HttpMethod.POST, "/api/reviews").permitAll()
                        .antMatchers("/api/**").authenticated()
                        .antMatchers("/welcome").authenticated()
                        .anyRequest().permitAll())
                // .formLogin(
                // form -> form.permitAll().defaultSuccessUrl("/welcome"))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        // UserDetails user =
        // User.withUsername("alice").password(passwordEncoder.encode("user")).roles("USER").build();
        // UserDetails admin =
        // User.withUsername("john").password(passwordEncoder.encode("admin")).roles("ADMIN").build();
        // return new InMemoryUserDetailsManager(user, admin);

        return new CustomUserDetialService();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthentication() {
        DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
        daoAuth.setUserDetailsService(userDetailsService());
        daoAuth.setPasswordEncoder(passwordEncoder());

        return daoAuth;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(daoAuthentication()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
