package com.tattou.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tattou.model.Tatuador;
import com.tattou.repository.TatuadorRepository;

@Service
public class TatuadorServiceImpl implements TatuadorService {

    @Autowired
    private final TatuadorRepository tatuadorRepository;

    public TatuadorServiceImpl(TatuadorRepository tatuadorRepository) {
        this.tatuadorRepository = tatuadorRepository;
    }

    @Override
    public List<Tatuador> obtenerTatuadores() {
        return tatuadorRepository.findAll();
    }

    @Override
    public Optional<Tatuador> obtenerTatuadorPorId(Long id) {
        return tatuadorRepository.findById(id);
    }

    @Override
    public Optional<Tatuador> obtenerTatuadorPorUsuarioId(Long usuarioId) {
        return tatuadorRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Tatuador crearTatuador(Tatuador tatuador) {
        return tatuadorRepository.save(tatuador);
    }

    @Override
    public void eliminarTatuadorPorId(Long id) {
        tatuadorRepository.deleteById(id);
    }

}
