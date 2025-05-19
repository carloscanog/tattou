package com.tattou.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tattou.model.Disenyo;
import com.tattou.repository.DisenyoRepository;

@Service
public class DisenyoServiceImpl implements DisenyoService {

    private DisenyoRepository disenyoRepository;

    public DisenyoServiceImpl(DisenyoRepository disenyoRepository) {
        this.disenyoRepository = disenyoRepository;
    }

    @Override
    public List<Disenyo> obtenerDisenyos() {
        return disenyoRepository.findAll();
    }

    @Override
    public Optional<Disenyo> obtenerDisenyoPorId(Long id) {
        return disenyoRepository.findById(id);
    }

    @Override
    public List<Disenyo> obtenerDisenyosPorAutorId(Long autorId) {
        return disenyoRepository.findByAutorId(autorId);
    }

    @Override
    public Disenyo crearDisenyo(Disenyo disenyo) {
        return disenyoRepository.save(disenyo);
    }

    @Override
    public void eliminarDisenyoPorId(Long id) {
        disenyoRepository.deleteById(id);
    }

}
