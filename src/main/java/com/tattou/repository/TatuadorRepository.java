package com.tattou.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tattou.model.Tatuador;
import com.tattou.model.Usuario;

@Repository
public interface TatuadorRepository extends JpaRepository<Tatuador, Long> {

    Optional<Tatuador> findByUsuarioId(Long usuarioId);

    boolean existsByUsuario(Usuario usuario);

}
