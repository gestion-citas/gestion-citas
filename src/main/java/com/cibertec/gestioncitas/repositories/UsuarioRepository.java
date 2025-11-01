package com.cibertec.gestioncitas.repositories;

import com.cibertec.gestioncitas.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // ============= BUSCAR POR USERNAME =============
    /**
     * Busca un usuario por su nombre de usuario
     * @param username el nombre de usuario
     * @return Optional<Usuario> si existe, vacio si no existe
     */
    Optional<Usuario> findByUsername(String username);

    // ============= BUSCAR POR EMAIL =============
    /**
     * Busca un usuario por su email
     * @param email el email del usuario
     * @return Optional<Usuario> si existe, vacio si no existe
     */
    Optional<Usuario> findByEmail(String email);
}
