package com.tattou.service;

import java.util.List;
import java.util.Optional;

import com.tattou.model.Tatuador;

public interface TatuadorService {

    List<Tatuador> obtenerTatuadores();

    Optional<Tatuador> obtenerTatuadorPorId(Long id);

    Optional<Tatuador> obtenerTatuadorPorUsuarioId(Long usuarioId);

    Tatuador guardar(Tatuador tatuador);

    void eliminarTatuadorPorId(Long id);

}
