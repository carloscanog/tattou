package com.tattou.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tattou.model.Usuario;
import com.tattou.repository.ClienteRepository;
import com.tattou.repository.TatuadorRepository;
import com.tattou.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final TatuadorRepository tatuadorRepository;

    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository, TatuadorRepository tatuadorRepository,
        ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tatuadorRepository = tatuadorRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("Usuario no encontrado en la base de datos."));

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (tatuadorRepository.findByUsuarioId(usuario.getId()).isPresent()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TATUADOR"));
        }

        if (clienteRepository.findByUsuarioId(usuario.getId()).isPresent()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        }

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), 
            usuario.getPassword(), authorities);

    }

}
