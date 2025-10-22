package com.cibertec.gestioncitas.repository;

import com.cibertec.gestioncitas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByDni(String dni);
    boolean existsByDni(String dni);
    List<Paciente> findByApellidosContainingIgnoreCase(String apellidos);
    
    @Query("SELECT p FROM Paciente p WHERE " +
           "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Paciente> buscarPorNombreOApellido(@Param("texto") String texto);
}
