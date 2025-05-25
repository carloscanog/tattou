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

    // Lista de rutas públicas
    private static final List<String> PUBLIC_PATHS = List.of(
        "/auth/login",
        "/usuarios/registro",
        "/clientes/registro",
        "/tatuadores/registro"
    );

    public JwtAuthenticationFilter(JwUtil jwUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwUtil = jwUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("🔎 Authorization: " + request.getHeader("Authorization"));
        String path = request.getRequestURI();

        // Si la ruta es pública, continúa sin autenticar
        if (PUBLIC_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwUtil.validateToken(token)) {
                String email = jwUtil.getSubject(token);
                UserDetails detallesUser = customUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        detallesUser, null, detallesUser.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                auth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("Usuario autenticado: " + detallesUser.getUsername());
                System.out.println("Usuario autenticado en el filtro: " + email);
                System.out.println("Authentication establecida: " + auth.getPrincipal());
            }
        }

        filterChain.doFilter(request, response);
    }
}
