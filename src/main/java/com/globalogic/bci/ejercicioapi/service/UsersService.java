package com.globalogic.bci.ejercicioapi.service;

import com.globalogic.bci.ejercicioapi.dto.CreateUserRequestDTO;
import com.globalogic.bci.ejercicioapi.dto.CreateUserResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface UsersService {

    CreateUserResponseDTO signUp(@RequestBody CreateUserRequestDTO createUserRequestDTO);

    CreateUserResponseDTO login(String token);
}
