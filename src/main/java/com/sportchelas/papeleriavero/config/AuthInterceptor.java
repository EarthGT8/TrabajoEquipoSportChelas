package com.sportchelas.papeleriavero.config;

import com.sportchelas.papeleriavero.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        // 1. Si no hay sesión activa, bloquea el acceso por URL directa
        if (usuario == null) {
            response.sendRedirect("/login?error=sesion");
            return false;
        }

        // 2. Control por roles: solo ADMIN accede a Inventario, Proveedores y Usuarios
        String ruta = request.getRequestURI();
        boolean esRutaAdmin = ruta.startsWith("/inventario")
                || ruta.startsWith("/proveedores")
                || ruta.startsWith("/usuarios");

        if (esRutaAdmin && !"ADMIN".equalsIgnoreCase(usuario.getRol())) {
            response.sendRedirect("/pos?error=sin_permiso");
            return false;
        }

        return true;
    }
}