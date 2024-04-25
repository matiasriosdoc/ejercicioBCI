package com.globalogic.bci.ejercicioapi.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Usuario no encontrado");
    }
}
