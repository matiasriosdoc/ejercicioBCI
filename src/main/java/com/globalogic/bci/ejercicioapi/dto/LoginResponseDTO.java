package com.globalogic.bci.ejercicioapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "created",
        "lastLogin",
        "token",
        "isActive",
        "name",
        "email",
        "password",
        "phones"
})
public class LoginResponseDTO {

    @JsonProperty("id")
    public String id;
    @JsonProperty("created")
    public String created;
    @JsonProperty("lastLogin")
    public String lastLogin;
    @JsonProperty("token")
    public String token;
    @JsonProperty("isActive")
    public Boolean isActive;
    @JsonProperty("name")
    public String name;
    @JsonProperty("email")
    public String email;
    @JsonProperty("password")
    public String password;
    @JsonProperty("phones")
    @Valid
    public List<PhoneDTO> phones;

}
