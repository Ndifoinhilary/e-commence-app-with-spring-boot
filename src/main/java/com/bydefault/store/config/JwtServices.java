package com.bydefault.store.config;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServices {
    @Value("${spring.jwt}")
    private  String secret;
    public String generateJwtToken(String email) {
        final long tokenExpirationInSeconds = 60 * 60 * 24;
         return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000* tokenExpirationInSeconds))
                 .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                 .compact();
    }
}
