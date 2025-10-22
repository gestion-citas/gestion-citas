package com.cibertec.gestioncitas.repository;

import com.cibertec.gestioncitas.entity.Cita;
import com.cibertec.gestioncitas.entity.Medico;
import com.cibertec.gestioncitas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByPaciente(Paciente paciente);
    List<Cita> findByMedico(Medico medico);
    List<Cita> findByEstado(String estado);
    List<Cita> findByFecha(LocalDate fecha);
    long countByEstado(String estado);
    
    @Query("SELECT c FROM Cita c WHERE c.medico.idMedico = :idMedico " +
           "AND c.fecha BETWEEN :desde AND :hasta ORDER BY c.fecha, c.hora")
    List<Cita> findByMedicoAndFechaBetween(
        @Param("idMedico") Integer idMedico,
        @Param("desde") LocalDate desde,
        @Param("hasta") LocalDate hasta
    );
    
    @Query("SELECT c FROM Cita c WHERE c.paciente.idPaciente = :idPaciente " +
           "AND c.fecha BETWEEN :desde AND :hasta ORDER BY c.fecha DESC, c.hora DESC")
    List<Cita> findByPacienteAndFechaBetween(
        @Param("idPaciente") Integer idPaciente,
        @Param("desde") LocalDate desde,
        @Param("hasta") LocalDate hasta
    );
    
    @Query("SELECT c FROM Cita c WHERE c.medico.idMedico = :idMedico " +
           "AND c.fecha = :fecha ORDER BY c.hora")
    List<Cita> findCitasDelDiaPorMedico(
        @Param("idMedico") Integer idMedico,
        @Param("fecha") LocalDate fecha
    );
    
    @Query("SELECT c FROM Cita c WHERE c.fecha = CURRENT_DATE " +
           "AND c.estado = 'PROGRAMADA' ORDER BY c.hora")
    List<Cita> findCitasProgramadasHoy();
}
