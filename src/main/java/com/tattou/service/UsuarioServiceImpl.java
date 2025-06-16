package com.tattou.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tattou.model.Usuario;
import com.tattou.repository.ClienteRepository;
import com.tattou.repository.TatuadorRepository;
import com.tattou.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final TatuadorRepository tatuadorRepository;

    private final ClienteRepository clienteRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, TatuadorRepository tatuadorRepository,
        ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tatuadorRepository = tatuadorRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario.getFotoPerfil() == null || usuario.getFotoPerfil().isBlank()) {
            usuario.setFotoPerfil("/uploads/usuarios/default-avatar.svg");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean esRegistradoComoTatuador(Usuario usuario) {
        return tatuadorRepository.existsByUsuario(usuario);
    }

    @Override
    public boolean esRegistradoComoCliente(Usuario usuario) {
        return clienteRepository.existsByUsuario(usuario);
    }

    @Override
    public void eliminarUsuarioPorEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            boolean esTatuador = esRegistradoComoTatuador(usuario);
            boolean esCliente = esRegistradoComoCliente(usuario);
            if (!esTatuador && !esCliente) {
                // El usuario solo podra eliminar su cuenta sin autentificarse si aun no es tatuador ni cliente
                usuarioRepository.delete(usuario);
            } else {
                throw new RuntimeException("El usuario ya está registrado y no puede completar su registro sin estar autentificado.");
            }
        }
    }

}
