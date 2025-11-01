package com.cibertec.gestioncitas.config;

import com.cibertec.gestioncitas.entities.Usuario;
import com.cibertec.gestioncitas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔐 Buscando usuario: " + username);
        
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ Usuario no encontrado: " + username);
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });

        System.out.println("✅ Usuario encontrado: " + usuario.getUsername() + " (Rol: " + usuario.getRole() + ")");

        // Crear autoridades basadas en el role
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRole()));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())  // ✅ SIN ENCRIPTAR, TEXTO PLANO
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(usuario.getActivo() == 0)
                .credentialsExpired(false)
                .disabled(usuario.getActivo() == 0)
                .build();
    }
}
