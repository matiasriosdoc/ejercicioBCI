package com.globalogic.bci.ejercicioapi.utils;

import com.globalogic.bci.ejercicioapi.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TokenJWTUtilsImpl implements TokenJWTUtils {

    private JwtParser _jwtParser;

    private JwtBuilder _jwtBuilder;

    @Autowired
    public TokenJWTUtilsImpl(JwtBuilder jwtBuilder, JwtParser jwtParser) {
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