package com.globalogic.bci.ejercicioapi.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JWTConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-time-in-millis}")
    private Long expirationTimeInMillis;

    @Value("${jwt.signature-algorithm-name}")
    private String signatureAlgorithmName;

    @Value("${jwt.loginToken}")
    private String loginToken;

    @Bean
    public JwtBuilder jwtBuilder() {

        return Jwts.builder()
                .setSubject(loginToken)
                .setExpiration(new Date(expirationTimeInMillis))
                .signWith(SignatureAlgorithm.forName(signatureAlgorithmName), secret);
    }


    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder().setSigningKey(secret).build();
    }
}