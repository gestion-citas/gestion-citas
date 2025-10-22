package com.cibertec.gestioncitas.repository;

import com.cibertec.gestioncitas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Buscar usuario por nombre_usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
