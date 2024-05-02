package com.globalogic.bci.ejercicioapi.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.globalogic.bci.ejercicioapi.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;

@Component
public class TokenJWTUtils {

    private JwtParser _jwtParser;

    private JwtBuilder _jwtBuilder;

    public TokenJWTUtils(JwtBuilder jwtBuilder, JwtParser jwtParser) {
        _jwtParser = jwtParser;
        _jwtBuilder = jwtBuilder;
    }

    public Jws<Claims> validateAndExtractJWTClaims(String token) {
        return Optional.of(_jwtParser.parseClaimsJws(token))
                .orElseThrow(() -> new InvalidTokenException("Token inválido"));
    }

    public String generateToken(String email, String password) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("password", password);

        return _jwtBuilder.setClaims(claims).compact();
    }
}