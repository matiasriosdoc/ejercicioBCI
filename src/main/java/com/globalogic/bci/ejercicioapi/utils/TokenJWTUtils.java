package com.globalogic.bci.ejercicioapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface TokenJWTUtils {
    

    Jws<Claims> validateAndExtractJWTClaims(String token);

    String generateToken(String email, String password);

}
