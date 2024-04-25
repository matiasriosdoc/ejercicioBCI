package com.globalogic.bci.ejercicioapi.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.globalogic.bci.ejercicioapi.dto.CreateUserRequestDTO;
import com.globalogic.bci.ejercicioapi.dto.CreateUserResponseDTO;

public interface UsersService {

    CreateUserResponseDTO signUp(@RequestBody CreateUserRequestDTO createUserRequestDTO);

    CreateUserResponseDTO login(String token);
}
