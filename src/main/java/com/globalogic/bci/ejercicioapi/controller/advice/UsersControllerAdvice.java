package com.globalogic.bci.ejercicioapi.controller.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.globalogic.bci.ejercicioapi.dto.ErrorDetailResponseDTO;
import com.globalogic.bci.ejercicioapi.dto.ErrorResponseDTO;
import com.globalogic.bci.ejercicioapi.exception.UserAlreadyExistsException;
import com.globalogic.bci.ejercicioapi.exception.UserNotFoundException;
import com.globalogic.bci.ejercicioapi.exception.UserUnauthorizedException;

import lombok.val;

@ControllerAdvice
public class UsersControllerAdvice {


    @ExceptionHandler( MethodArgumentNotValidException.class )
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {
        val field = ex.getBindingResult().getFieldError().getField();
        val message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(
                new ErrorResponseDTO(
                        Arrays.asList(new ErrorDetailResponseDTO(Timestamp.valueOf(LocalDateTime.now()),
                                1, "error in field: " + field + "; message: "+ message))), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        String errorMessage = ex.getMessage();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setErrors(Arrays.asList(new ErrorDetailResponseDTO(
                Timestamp.valueOf(LocalDateTime.now()), 2, errorMessage)));

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserUnauthorizedException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(RuntimeException ex) {
        String errorMessage = ex.getMessage();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setErrors(Arrays.asList(new ErrorDetailResponseDTO(
                Timestamp.valueOf(LocalDateTime.now()), 2, errorMessage)));

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleIntegrityConstraintViolationException(RuntimeException ex) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setErrors(Arrays.asList(new ErrorDetailResponseDTO(
                Timestamp.valueOf(LocalDateTime.now()), 3, "El usuario ya existe.")));

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
