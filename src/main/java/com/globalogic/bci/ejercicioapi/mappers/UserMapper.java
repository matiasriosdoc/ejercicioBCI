package com.globalogic.bci.ejercicioapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.globalogic.bci.ejercicioapi.dto.CreateUserRequestDTO;
import com.globalogic.bci.ejercicioapi.dto.CreateUserResponseDTO;
import com.globalogic.bci.ejercicioapi.dto.PhoneDTO;
import com.globalogic.bci.ejercicioapi.jpa.domains.Phone;
import com.globalogic.bci.ejercicioapi.jpa.domains.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    //@Mapping(source = "token", target = "token")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "phones", target = "phones")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "isActive", target = "isActive", defaultValue = "true")
    CreateUserResponseDTO userToCreateUserResponseDTO(User user);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phones", target = "phones")
    User createUserRequestDTOToUser(CreateUserRequestDTO createUserRequestDTO);

    @Mapping(source = "number", target = "number")
    @Mapping(source = "cityCode", target = "cityCode")
    @Mapping(source = "countryCode", target = "countryCode")
    Phone phoneDTOToPhone(PhoneDTO phoneDTO);

    @Mapping(source = "number", target = "number")
    @Mapping(source = "cityCode", target = "cityCode")
    @Mapping(source = "countryCode", target = "countryCode")
    PhoneDTO phoneToPhoneDTO(Phone phone);
}
