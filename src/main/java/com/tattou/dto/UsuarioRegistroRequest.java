package com.tattou.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRegistroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Email(message = "Debe ser un formato de email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;

}
