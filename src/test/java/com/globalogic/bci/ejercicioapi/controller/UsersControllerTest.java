package com.globalogic.bci.ejercicioapi.controller;

import com.globalogic.bci.ejercicioapi.dto.CreateUserRequestDTO;
import com.globalogic.bci.ejercicioapi.dto.CreateUserResponseDTO;
import com.globalogic.bci.ejercicioapi.exception.UserUnauthorizedException;
import com.globalogic.bci.ejercicioapi.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignUp() {
        CreateUserRequestDTO requestDTO = mock(CreateUserRequestDTO.class);
        CreateUserResponseDTO createUserResponseDTO = mock(CreateUserResponseDTO.class);

        when(usersService.signUp(requestDTO)).thenReturn(createUserResponseDTO);

        ResponseEntity<CreateUserResponseDTO> response = usersController.signUp(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testLogin() {
        String authorizationHeader = "Bearer token";
        CreateUserResponseDTO createUserResponseDTO = mock(CreateUserResponseDTO.class);

        when(usersService.login("token")).thenReturn(createUserResponseDTO);

        ResponseEntity<CreateUserResponseDTO> response = usersController.login(authorizationHeader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLoginUnauthorizedEmptyHeader() {
        String authorizationHeader = "";

        assertThrows(UserUnauthorizedException.class,
                ()->{usersController.login(authorizationHeader);});
    }

    @Test
    public void testLoginUnauthorizedInvalidToken() {
        String authorizationHeader = "InvalidToken";
        assertThrows(UserUnauthorizedException.class,
                ()->{usersController.login(authorizationHeader);});
    }
}