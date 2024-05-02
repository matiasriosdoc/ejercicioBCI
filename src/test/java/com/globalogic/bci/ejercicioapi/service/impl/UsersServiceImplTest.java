package com.globalogic.bci.ejercicioapi.service.impl;

import com.globalogic.bci.ejercicioapi.dto.CreateUserRequestDTO;
import com.globalogic.bci.ejercicioapi.dto.CreateUserResponseDTO;
import com.globalogic.bci.ejercicioapi.jpa.domains.User;
import com.globalogic.bci.ejercicioapi.jpa.repositories.UserRepository;
import com.globalogic.bci.ejercicioapi.mappers.UserMapper;
import com.globalogic.bci.ejercicioapi.utils.TokenJWTUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.github.javaparser.utils.Utils.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UsersServiceImplTest {
    private CreateUserRequestDTO requestDTO;
    private CreateUserResponseDTO responseDTO;
    private User user;
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TokenJWTUtils tokenJWTUtils;

    @Mock
    private JwtBuilder _jwtBuilder;
    @Mock
    private JwtParser _jwtParser;

    @InjectMocks
    private UsersServiceImpl usersService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

      //  usersService = new UsersServiceImpl(userRepository,userMapper);
      //  tokenJWTUtils = new TokenJWTUtils(_jwtBuilder,_jwtParser);

    }

    @Test
    public void testSignUp() {
        // Preparar el entorno de la prueba
        CreateUserRequestDTO requestDTO = this.makeRequestDTO();
        CreateUserResponseDTO responseDTO = this.makeResponseDTO(); // Crear un objeto CreateUserResponseDTO simulado
        //tokenJWTUtils = new TokenJWTUtils(jwtBuilder, jwtParser);
        when(userMapper.createUserRequestDTOToUser(requestDTO)).thenReturn(makeUser());
        when(userRepository.save(makeUser())).thenReturn(makeUser());

        when(tokenJWTUtils.generateToken(anyString(), anyString())).thenReturn("token");

        when(userMapper.userToCreateUserResponseDTO(makeUser())).thenReturn(responseDTO);
        CreateUserResponseDTO response = usersService.signUp(requestDTO);

        // Verificar el resultado esperado
        assertNotNull(response);
        assertEquals(responseDTO.getEmail(), response.getEmail());
        assertEquals(responseDTO.getToken(), response.getToken());

    }

    private CreateUserResponseDTO makeResponseDTO() {
        if (responseDTO == null) {
            responseDTO = new CreateUserResponseDTO();
            responseDTO.setId(makeUser().getId().toString());
            responseDTO.setEmail(makeUser().getEmail());
            responseDTO.setToken("mockedToken");

        }
        return responseDTO;
    }

    private User makeUser() {
        if (user == null) {
            user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail(makeRequestDTO().getEmail());
            user.setPassword(makeRequestDTO().getPassword());
        }
        return user;
    }

    private CreateUserRequestDTO makeRequestDTO() {
        if (requestDTO == null) {
            requestDTO = new CreateUserRequestDTO();
            requestDTO.setEmail("test@example.com");
            requestDTO.setPassword("password");

        }
        return requestDTO;
    }
}
