package com.globalogic.bci.ejercicioapi.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.globalogic.bci.ejercicioapi.exception.InvalidTokenException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TokenJWTUtils {

    @Autowired
    private JwtBuilder jwtBuilder;

    @Autowired
    private JwtParser jwtParser;

    private static JwtParser _jwtParser;

    private static JwtBuilder _jwtBuilder;

    public TokenJWTUtils(JwtBuilder jwtBuilder, JwtParser jwtParser) {
        _jwtParser = jwtParser;
        _jwtBuilder = jwtBuilder;
    }

    public static String generateToken(String email, String password) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("password", password);

        return _jwtBuilder.setClaims(claims).compact();
    }

    public static Jws<Claims> validateAndExtractJWTClaims(String token) {
        return Optional.of(_jwtParser.parseClaimsJws(token))
                .orElseThrow(() -> new InvalidTokenException("Token inválido"));
    }
}