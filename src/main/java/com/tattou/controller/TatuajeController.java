package com.tattou.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tattou.model.Tatuaje;
import com.tattou.service.TatuajeService;

@RestController
@RequestMapping("/tatuajes")
@CrossOrigin(origins = "*")
public class TatuajeController  {

    private final TatuajeService tatuajeService;

    public TatuajeController(TatuajeService tatuajeService) {
        this.tatuajeService = tatuajeService;
    }

    @GetMapping
    public List<Tatuaje> obtenerTatuajes() {
        return tatuajeService.obtenerTatuajes();
    }

    @GetMapping("/{id}")
    public Optional<Tatuaje> obtenerTatuajePorId(@PathVariable Long id) {
        return tatuajeService.obtenerTatuajePorId(id);
    }

    @GetMapping("/autor/{autorId}")
    public List<Tatuaje> obtenerTatuajesPorAutorId(@PathVariable Long autorId) {
        return tatuajeService.obtenerPorAutorId(autorId);
    }

    @PostMapping
    public Tatuaje crearTatuaje(@RequestBody Tatuaje tatuaje) {
        return tatuajeService.crearTatuaje(tatuaje);
    }

    @DeleteMapping("/{id}")
    public void eliminarTatuajePorId(@PathVariable Long id) {
        tatuajeService.eliminarTatuajePorId(id);
    }

}
