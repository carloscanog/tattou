package com.tattou.security;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwUtil jwUtil;
    private final CustomUserDetailsService customUserDetailsService;

    // Rutas públicas
    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/login",
            "/usuarios/registro",
            "/clientes/registro",
            "/tatuadores/registro",
            "/usuarios/email/**"
    );

    public JwtAuthenticationFilter(JwUtil jwUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwUtil = jwUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 1️⃣ No exigir autenticación en rutas públicas
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2️⃣ Extraer y validar el token
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwUtil.validateToken(token)) {
                String email = jwUtil.getSubject(token);

                // 3️⃣ Cargar usuario y crear Authentication con authorities
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                System.out.println("Authorities: " + userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,                       // principal
                                null,                              // credentials
                                userDetails.getAuthorities());     // roles / authorities

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // 4️⃣ Guardar autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
