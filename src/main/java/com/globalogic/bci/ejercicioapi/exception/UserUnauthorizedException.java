package com.globalogic.bci.ejercicioapi.exception;

public class UserUnauthorizedException extends RuntimeException{
    public UserUnauthorizedException(String mesasage) {
        super(mesasage);
    }

}
