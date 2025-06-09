package com.tattou.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TatuadorRegistroRequest {

    private Long usuarioId;

    private String biografia;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @NotEmpty(message = "La lista de estilos es obligatoria")
    private List<String> estilos;

    private String instagram;

    private String tiktok;
    
}
