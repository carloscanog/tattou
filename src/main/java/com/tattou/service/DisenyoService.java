package com.tattou.service;

import java.util.List;
import java.util.Optional;

import com.tattou.model.Disenyo;

public interface DisenyoService {

    List<Disenyo> obtenerDisenyos();

    Optional<Disenyo> obtenerDisenyoPorId(Long id);

    List<Disenyo> obtenerDisenyosPorAutorId(Long autorId);

    Disenyo guardarDisenyo(Disenyo disenyo);

    void eliminarDisenyoPorId(Long id);
    
}
