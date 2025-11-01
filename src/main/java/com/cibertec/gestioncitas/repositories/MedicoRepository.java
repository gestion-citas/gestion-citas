package com.cibertec.gestioncitas.repositories;

import com.cibertec.gestioncitas.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    // Buscar médico por usuario
    @Query("SELECT m FROM Medico m WHERE m.usuario.idUsuario = :usuarioId")
    Optional<Medico> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    // Buscar médicos por especialidad
    @Query("SELECT m FROM Medico m WHERE m.especialidad.idEspecialidad = :especialidadId")
    List<Medico> findByEspecialidadId(@Param("especialidadId") Integer especialidadId);

    // Buscar todos los médicos activos (CORREGIDO: true -> 1)
    @Query("SELECT m FROM Medico m WHERE m.activo = 1")
    List<Medico> findAllActivos();

    // Buscar médico por entidad Usuario (incluye inactivos para diagnóstico)
    Optional<Medico> findByUsuario(com.cibertec.gestioncitas.entities.Usuario usuario);
    
    // Buscar médico por usuario específicamente (incluye inactivos)
    @Query("SELECT m FROM Medico m WHERE m.usuario = :usuario")
    Optional<Medico> findByUsuarioIncludeInactive(@Param("usuario") com.cibertec.gestioncitas.entities.Usuario usuario);

    // Métodos de búsqueda por DNI y Email
    Optional<Medico> findByDni(String dni);
    
    Optional<Medico> findByEmail(String email);
}
