package com.cibertec.gestioncitas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuracion de beans de seguridad
 * Define el encoder para encriptar passwords
 */
@Configuration  // Indica que esta clase contiene configuracion de Spring
public class SecurityBeans {
    
    /**
     * Bean para encriptar passwords usando BCrypt
     * BCrypt es el algoritmo recomendado por Spring Security
     * @return Instancia de BCryptPasswordEncoder
     */
    @Bean  // Este metodo crea un bean gestionado por Spring
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
