package com.tattou.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email(message = "El formato del email es incorrecto")
    @NotBlank(message = "El email no puede ser nulo")
    private String email;

    @NotBlank(message = "La contraseña no puede ser nula")
    private String password;
}
