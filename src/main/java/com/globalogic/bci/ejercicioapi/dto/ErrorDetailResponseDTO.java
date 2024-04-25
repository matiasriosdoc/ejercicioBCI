package com.globalogic.bci.ejercicioapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timestamp",
        "code",
        "detail"
})
public class ErrorDetailResponseDTO {

    @JsonProperty("timestamp")
    private Timestamp timestamp;
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("detail")
    private String detail;

}
