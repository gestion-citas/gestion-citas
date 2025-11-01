package com.cibertec.gestioncitas.repositories;

import com.cibertec.gestioncitas.entities.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {

    // Buscar especialidad por nombre
    Optional<Especialidad> findByNombre(String nombre);

    // Buscar todas las especialidades activas (CORREGIDO: true -> 1)
    @Query("SELECT e FROM Especialidad e WHERE e.activo = 1")
    List<Especialidad> findAllActivas();

    // Obtener todas las especialidades
    @Query("SELECT e FROM Especialidad e ORDER BY e.nombre ASC")
    List<Especialidad> obtenerTodas();

    // Obtener solo especialidades que tienen médicos activos
    @Query("SELECT DISTINCT e FROM Especialidad e INNER JOIN Medico m ON e.idEspecialidad = m.especialidad.idEspecialidad WHERE m.activo = 1 ORDER BY e.nombre ASC")
    List<Especialidad> findEspecialidadesConMedicosActivos();
}
