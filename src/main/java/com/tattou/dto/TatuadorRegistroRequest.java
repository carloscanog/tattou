package com.tattou.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TatuadorRegistroRequest {

    private Long usuarioId;

    private String biografia;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @NotBlank(message = "La lista de estilos es obligatoria")
    private String estilos;

    private String instagram;

    private String tiktok;
    
}
