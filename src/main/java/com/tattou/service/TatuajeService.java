package com.tattou.service;

import java.util.List;
import java.util.Optional;

import com.tattou.model.Tatuaje;

public interface TatuajeService {

    List<Tatuaje> obtenerTatuajes();
    
    Optional<Tatuaje> obtenerTatuajePorId(Long id);
    
    List<Tatuaje> obtenerPorAutorId(Long id);

    Tatuaje crearTatuaje(Tatuaje tatuaje);

    void eliminarTatuajePorId(Long id);

}
