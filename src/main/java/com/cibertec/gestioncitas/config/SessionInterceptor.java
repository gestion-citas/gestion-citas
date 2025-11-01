package com.cibertec.gestioncitas.config;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ⚠️ INTERCEPTOR DESHABILITADO TEMPORALMENTE PARA EVITAR CONFLICTOS CON SPRING SECURITY
        // El manejo de sesiones ahora se hace completamente a través de Spring Security
        // y los controladores individuales verifican la autenticación
        return true;
        
        /*
        HttpSession session = request.getSession(false);

        // Rutas públicas permitidas sin sesión
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login") || requestURI.contains("/logout") || 
            requestURI.contains("/css") || requestURI.contains("/js") || requestURI.contains("/img")) {
            return true;
        }

        // Verificar sesión
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
        */
    }
}
