package com.uca.parcialfinalncapas.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LogInRequest {
    @JsonProperty("correo")
    private String correo;

    @JsonProperty("password")
    private String password;
}
