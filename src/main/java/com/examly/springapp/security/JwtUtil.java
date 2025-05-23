package com.examly.springapp.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECERT_STRING_KEY = "gdUT6pmP9AokmMgrpvSU7iuujHb47tW0";

    private final SecretKey SECERT_KEY = Keys.hmacShaKeyFor(SECERT_STRING_KEY.getBytes());

    public String tokenGenerater(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() * 1000 * 60 * 2))
                .signWith(SECERT_KEY,Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token,UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername());
    }

    public String extractUsername(String token) {
        return Jwts.parser()
        .verifyWith(SECERT_KEY)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
    }

}
