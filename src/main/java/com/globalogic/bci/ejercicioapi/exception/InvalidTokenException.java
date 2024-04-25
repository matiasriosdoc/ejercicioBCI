package com.globalogic.bci.ejercicioapi.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String email) {
        super("El usuario con email '" + email + "' ya existe.");
    }
}
