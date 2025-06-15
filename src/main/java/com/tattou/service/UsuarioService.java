package com.tattou.service;

import java.util.List;
import java.util.Optional;

import com.tattou.model.Usuario;

public interface UsuarioService {

    List<Usuario> obtenerUsuarios();

    Optional<Usuario> obtenerUsuarioPorId(Long id);

    Optional<Usuario> obtenerUsuarioPorEmail(String email);

    Usuario guardar(Usuario usuario);

    void eliminarUsuarioPorId(Long id);

    void eliminarUsuarioPorEmail(String email);

    boolean esRegistradoComoTatuador(Usuario usuario);

    boolean esRegistradoComoCliente(Usuario usuario);

}
