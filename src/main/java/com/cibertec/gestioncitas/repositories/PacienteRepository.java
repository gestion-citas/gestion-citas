package com.cibertec.gestioncitas.repositories;

import com.cibertec.gestioncitas.entities.Paciente;
import com.cibertec.gestioncitas.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByUsuario(Usuario usuario);
}
