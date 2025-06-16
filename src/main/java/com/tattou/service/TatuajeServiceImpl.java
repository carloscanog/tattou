package com.tattou.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tattou.model.Tatuaje;
import com.tattou.repository.TatuajeRepository;

@Service
public class TatuajeServiceImpl implements TatuajeService {

    private TatuajeRepository tatuajeRepository;

    public TatuajeServiceImpl(TatuajeRepository tatuajeRepository) {
        this.tatuajeRepository = tatuajeRepository;
    }

    @Override
    public List<Tatuaje> obtenerTatuajes() {
        return tatuajeRepository.findAll();
    }

    @Override
    public Optional<Tatuaje> obtenerTatuajePorId(Long id) {
        return tatuajeRepository.findById(id);
    }

    @Override
    public List<Tatuaje> obtenerPorAutorId(Long id) {
        return tatuajeRepository.findByAutorId(id);
    }

    @Override
    public Tatuaje guardarTatuaje(Tatuaje tatuaje) {
        return tatuajeRepository.save(tatuaje);
    }

    @Override
    public void eliminarTatuajePorId(Long id) {
        tatuajeRepository.deleteById(id);
    }

}
