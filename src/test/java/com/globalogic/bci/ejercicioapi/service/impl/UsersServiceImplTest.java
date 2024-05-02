package com.globalogic.bci.ejercicioapi.service.impl;

import com.globalogic.bci.ejercicioapi.dto.CreateUserRequestDTO;
import com.globalogic.bci.ejercicioapi.dto.CreateUserResponseDTO;
import com.globalogic.bci.ejercicioapi.exception.UserNotFoundException;
import com.globalogic.bci.ejercicioapi.exception.UserUnauthorizedException;
import com.globalogic.bci.ejercicioapi.jpa.domains.User;
import com.globalogic.bci.ejercicioapi.jpa.repositories.UserRepository;
import com.globalogic.bci.ejercicioapi.mappers.UserMapper;
import com.globalogic.bci.ejercicioapi.utils.TokenJWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static com.github.javaparser.utils.Utils.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UsersServiceImplTest {
    private final static String MAIL= "test@example.com";
    private final static String PASS= "password";
    private final static String TOKEN= "tokenTest";
    private final static UUID ID= UUID.randomUUID();

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TokenJWTUtils tokenJWTUtils;

    @InjectMocks
    private UsersServiceImpl usersService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignUp() {
        CreateUserRequestDTO requestDTO = this.makeRequestDTO();
        CreateUserResponseDTO responseDTO = this.makeResponseDTO();

        when(userMapper.createUserRequestDTOToUser(any(CreateUserRequestDTO.class))).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userMapper.userToCreateUserResponseDTO(any(User.class))).thenReturn(responseDTO);
        when(tokenJWTUtils.generateToken(anyString(), anyString())).thenReturn(TOKEN);

        CreateUserResponseDTO response = usersService.signUp(requestDTO);

        // Verificar el resultado esperado
        assertNotNull(response);
        assertEquals(MAIL, response.getEmail());
        assertEquals(TOKEN, response.getToken());

    }

    @Test
    public void testLogin() {
        // Preparar el entorno de la prueba
        Jws<Claims> claimsJws = mock(Jws.class);
        User user = new User();
        user.setPassword(PASS);
        CreateUserResponseDTO responseDTO = makeResponseDTO();
        responseDTO.setToken(TOKEN);

        when(tokenJWTUtils.validateAndExtractJWTClaims(anyString())).thenReturn(claimsJws);
        when(claimsJws.getBody()).thenReturn(mock(Claims.class));
        when(claimsJws.getBody().get("email", String.class))
                .thenReturn(MAIL);
        when(claimsJws.getBody().get("password", String.class))
                .thenReturn(PASS);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userMapper.userToCreateUserResponseDTO(any(User.class))).thenReturn(responseDTO);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenJWTUtils.generateToken(anyString(), anyString())).thenReturn(TOKEN);

        CreateUserResponseDTO responseTest = usersService.login(TOKEN);

        assertNotNull(responseTest);
        assertEquals(TOKEN, responseTest.getToken());
    }

    @Test
    public void testLoginThrowsUserNotFoundException() {
        when(tokenJWTUtils.validateAndExtractJWTClaims(TOKEN)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> usersService.login(TOKEN));
    }

    @Test
    public void testLoginThrowsUserUnauthorizedException() {
        Jws<Claims> claimsJws = mock(Jws.class);
        User user = new User(); // Crear un objeto User simulado
        user.setPassword(PASS);

        when(tokenJWTUtils.validateAndExtractJWTClaims(TOKEN)).thenReturn(claimsJws);
        when(claimsJws.getBody()).thenReturn(mock(Claims.class));
        when(claimsJws.getBody().get("email", String.class)).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userMapper.userToCreateUserResponseDTO(user)).thenReturn(new CreateUserResponseDTO());
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertThrows(UserUnauthorizedException.class, () -> usersService.login(TOKEN));
    }


    private CreateUserResponseDTO makeResponseDTO() {
        CreateUserResponseDTO responseDTO = new CreateUserResponseDTO();
        responseDTO.setId(ID.toString());
        responseDTO.setEmail(MAIL);
        responseDTO.setPassword(PASS);
        return responseDTO;
    }

    private CreateUserRequestDTO makeRequestDTO() {
        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO();
        requestDTO.setEmail(MAIL);
        requestDTO.setPassword(PASS);
        return requestDTO;
    }
}
