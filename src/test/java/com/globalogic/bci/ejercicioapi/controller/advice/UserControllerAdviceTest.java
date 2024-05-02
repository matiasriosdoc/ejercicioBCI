package com.globalogic.bci.ejercicioapi.controller.advice;

import com.globalogic.bci.ejercicioapi.dto.ErrorResponseDTO;
import com.globalogic.bci.ejercicioapi.exception.UserAlreadyExistsException;
import com.globalogic.bci.ejercicioapi.exception.UserUnauthorizedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class UserControllerAdviceTest {

    @Test
    public void testHandleMethodArgumentNotValid() {
        // Preparar el entorno de la prueba
        UsersControllerAdvice advice = new UsersControllerAdvice();

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        WebRequest  webRequest = mock(WebRequest.class);

        // Ejecutar el método de manejo de excepciones
        ResponseEntity<ErrorResponseDTO> response = advice.handleMethodArgumentNotValid(exception, webRequest);

        // Verificar el resultado esperado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        // Verifica cualquier otra propiedad específica que esperes en la respuesta
    }

    @Test
    public void testHandleUserAlreadyExistsException() {
        // Preparar el entorno de la prueba
        UsersControllerAdvice advice = new UsersControllerAdvice();
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");

        // Ejecutar el método de manejo de excepciones
        ResponseEntity<ErrorResponseDTO> response = advice.handleUserAlreadyExistsException(exception);

        // Verificar el resultado esperado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        // Verifica cualquier otra propiedad específica que esperes en la respuesta
    }
    @Test
    public void testHandleUserUnauthorizedException() {
        // Preparar el entorno de la prueba
        UsersControllerAdvice advice = new UsersControllerAdvice();
        UserUnauthorizedException exception = new UserUnauthorizedException("User Unauthorized");

        // Ejecutar el método de manejo de excepciones
        ResponseEntity<ErrorResponseDTO> response = advice.handleUserUnauthorizedException(exception);

        // Verificar el resultado esperado
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        // Verifica cualquier otra propiedad específica que esperes en la respuesta
    }
    @Test
    public void testHandleIntegrityConstraintViolationException() {
        // Preparar el entorno de la prueba
        UsersControllerAdvice advice = new UsersControllerAdvice();
        SQLIntegrityConstraintViolationException exception = new SQLIntegrityConstraintViolationException("User already exists");

        // Ejecutar el método de manejo de excepciones
        ResponseEntity<ErrorResponseDTO> response = advice.handleIntegrityConstraintViolationException(exception);

        // Verificar el resultado esperado
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        // Verifica cualquier otra propiedad específica que esperes en la respuesta
    }
    // Tests similares para handleUserUnauthorizedException, handleUserNotFoundException y handleIntegrityConstraintViolationException
}
