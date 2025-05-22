package com.examly.springapp.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.examly.springapp.model.Review;
import com.examly.springapp.repository.ReviewRepository;

public class CustomUserDetialService implements UserDetailsService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Review user = reviewRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found !"));

        return new User(user.getName(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
    }

}
