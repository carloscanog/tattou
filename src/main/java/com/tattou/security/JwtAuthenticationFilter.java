package com.tattou.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwUtil jwUtil;

    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwUtil jwUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwUtil = jwUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        // Se busca la cabecera que contiene la info de la autorizacion
        String header = request.getHeader("Authorization");

        // Debe comenzar por Bearer, seguido del token
        if (header != null && header.startsWith("Bearer ")) {
            // Se obtiene el token, que comienza despues del Bearer
            String token = header.substring(7);
            if (jwUtil.validateToken(token)) {
                String email = jwUtil.getSubject(token);
                var detallesUser = customUserDetailsService.loadUserByUsername(email);

                // Aqui se crea la autenticacion con el usuario validado y sus roles, no hay contraseña 
                // ya que se valida con el token
                var auth = new UsernamePasswordAuthenticationToken(detallesUser, 
                    null, detallesUser.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Se especifica a la app que considere al usuario como autorizado
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        
        chain.doFilter(req, res);
    }

    

}
