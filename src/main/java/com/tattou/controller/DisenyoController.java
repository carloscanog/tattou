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

import com.tattou.model.Disenyo;
import com.tattou.service.DisenyoService;

@RestController
@RequestMapping("/disenyos")
@CrossOrigin(origins = "*")
public class DisenyoController {

    private DisenyoService disenyoService;

    public DisenyoController(DisenyoService disenyoService) {
        this.disenyoService = disenyoService;
    }

    @GetMapping
    public List<Disenyo> obtenerDisenyos() {
        return disenyoService.obtenerDisenyos();
    }

    @GetMapping("/{id}")
    public Optional<Disenyo> obtenerDisenyoPorId(@PathVariable Long id) {
        return disenyoService.obtenerDisenyoPorId(id);
    }

    @PostMapping
    public Disenyo crearDisenyo(@RequestBody Disenyo disenyo) {
        return disenyoService.crearDisenyo(disenyo);
    }

    @DeleteMapping("/{id}")
    public void eliminarDisenyoPorId(@PathVariable Long id) {
        disenyoService.eliminarDisenyoPorId(id);
    }

}
