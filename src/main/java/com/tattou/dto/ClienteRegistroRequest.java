package com.tattou.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRegistroRequest {

    private Long usuarioId;

    @NotEmpty(message = "La lista de intereses es obligatoria")
    private List<String> intereses;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

}
