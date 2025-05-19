package com.tattou.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRegistroRequest {

    private Long usuarioId;

    @NotBlank(message = "La lista de intereses es obligatoria")
    private String intereses;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

}
