package com.cibertec.gestioncitas.repository;

import com.cibertec.gestioncitas.entity.Medico;
import com.cibertec.gestioncitas.entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    Optional<Medico> findByDni(String dni);
    boolean existsByDni(String dni);
    List<Medico> findByEspecialidad(Especialidad especialidad);
    List<Medico> findByEspecialidadIdEspecialidad(Integer idEspecialidad);
    List<Medico> findByApellidosContainingIgnoreCase(String apellidos);
    
    @Query("SELECT m FROM Medico m JOIN FETCH m.especialidad ORDER BY m.apellidos, m.nombres")
    List<Medico> findAllWithEspecialidad();
}
