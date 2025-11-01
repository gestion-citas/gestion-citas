package com.cibertec.gestioncitas.repositories;

import com.cibertec.gestioncitas.entities.*;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.sql.Date;

import java.util.List;

import java.util.Optional;

@Repository

public interface CitaRepository extends JpaRepository<Cita, Integer> {

	// ================== MÉTODOS BÁSICOS ==================

	@Override
	Optional<Cita> findById(Integer id);

	@Override
	List<Cita> findAll();

	// ================== BÚSQUEDAS POR CRITERIOS ==================

	@Query("SELECT c FROM Cita c WHERE c.paciente.idPaciente = :idPaciente ORDER BY c.fecha DESC")
	List<Cita> findByPacienteId(@Param("idPaciente") Integer idPaciente);

	@Query("SELECT c FROM Cita c WHERE c.medico.idMedico = :idMedico ORDER BY c.fecha DESC")
	List<Cita> findByMedicoIdWithDetails(@Param("idMedico") Integer idMedico);

	@Query("SELECT c FROM Cita c WHERE c.estado = :estado ORDER BY c.fecha DESC")
	List<Cita> findByEstado(@Param("estado") String estado);

	@Query("SELECT c FROM Cita c WHERE c.fecha >= :fechaInicio AND c.fecha <= :fechaFin ORDER BY c.fecha ASC")
	List<Cita> findByFechaBetween(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

	// ================== CONTADORES GENERALES ==================

	@Query("SELECT COUNT(c) FROM Cita c")
	long countTotalCitas();

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.estado IN ('PROGRAMADA', 'CONFIRMADA')")
	long countCitasPendientes();

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.estado = :estado")
	long countByEstado(@Param("estado") String estado);

	@Query("SELECT COUNT(c) FROM Cita c WHERE DATE(c.fecha) = CURDATE()")
	long countCitasHoy();

	// ================== MÉTODOS ESPECÍFICOS PARA MÉDICO ==================

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.medico = :medico")
	long countByMedico(@Param("medico") Medico medico);

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.medico = :medico AND c.estado = :estado")
	long countByMedicoAndEstado(@Param("medico") Medico medico, @Param("estado") String estado);

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.medico.idMedico = :idMedico AND DATE(c.fecha) = CURDATE()")
	long countCitasHoyByMedico(@Param("idMedico") Integer idMedico);

	@Query("SELECT COUNT(DISTINCT c.paciente.idPaciente) FROM Cita c WHERE c.medico.idMedico = :idMedico")
	long countPacientesUnicosByMedico(@Param("idMedico") Integer idMedico);

	@Query("SELECT c FROM Cita c WHERE c.medico.idMedico = :idMedico AND c.fecha >= CURDATE() AND c.estado IN ('PROGRAMADA', 'CONFIRMADA') ORDER BY c.fecha ASC, c.hora ASC")
	List<Cita> findProximasCitasByMedico(@Param("idMedico") Integer idMedico);

	@Query("SELECT c FROM Cita c LEFT JOIN FETCH c.paciente WHERE c.medico.idMedico = :idMedico ORDER BY c.fecha DESC, c.hora DESC")
	List<Cita> findAllCitasByMedicoWithPaciente(@Param("idMedico") Integer idMedico);

	@Query("SELECT DISTINCT c.paciente FROM Cita c WHERE c.medico.idMedico = :idMedico ORDER BY c.paciente.nombres")
	List<Paciente> findPacientesByMedico(@Param("idMedico") Integer idMedico);

	// ================== MÉTODOS ESPECÍFICOS PARA PACIENTE ==================

	@Query("SELECT c FROM Cita c WHERE c.paciente.idPaciente = :idPaciente AND c.fecha >= CURDATE() ORDER BY c.fecha ASC")
	List<Cita> findProximasCitasPaciente(@Param("idPaciente") Integer idPaciente);

	long countByPaciente(Paciente paciente);

	long countByPacienteAndEstado(Paciente paciente, String estado);

	// ================== CONSULTAS PARA HOY ==================

	@Query("SELECT c FROM Cita c WHERE DATE(c.fecha) = CURDATE() AND c.estado IN ('PROGRAMADA', 'CONFIRMADA') ORDER BY c.hora ASC")
	List<Cita> findCitasPendientesHoy();

	// ================== MÉTODOS ADICIONALES PARA ESTADÍSTICAS ==================

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.medico.especialidad = :especialidad")
	long countByMedicoEspecialidad(@Param("especialidad") Especialidad especialidad);

	@Query("SELECT COUNT(c) FROM Cita c WHERE MONTH(c.fecha) = :mes AND YEAR(c.fecha) = :año")
	long countByMes(@Param("mes") int mes, @Param("año") int año);

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.medico.idMedico = :idMedico")
	long countByMedicoId(@Param("idMedico") Integer idMedico);

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.medico.idMedico = :idMedico AND c.estado = :estado")
	long countByMedicoIdAndEstado(@Param("idMedico") Integer idMedico, @Param("estado") String estado);

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.paciente.idPaciente = :idPaciente")
	long countByPacienteId(@Param("idPaciente") Integer idPaciente);

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.paciente.idPaciente = :idPaciente AND c.estado = :estado")
	long countByPacienteIdAndEstado(@Param("idPaciente") Integer idPaciente, @Param("estado") String estado);

	@Query("SELECT COUNT(c) FROM Cita c WHERE c.paciente.idPaciente = :idPaciente AND DATE(c.fecha) = CURDATE()")
	long countCitasHoyByPaciente(@Param("idPaciente") Integer idPaciente);

	@Query("SELECT COUNT(DISTINCT c.medico.idMedico) FROM Cita c WHERE c.paciente.idPaciente = :idPaciente")
	long countMedicosUnicosByPaciente(@Param("idPaciente") Integer idPaciente);

	// ✅ NUEVO: Contar citas por paciente y médico (MÉTODO FALTANTE)
	@Query("SELECT COUNT(c) FROM Cita c WHERE c.paciente.idPaciente = :pacienteId AND c.medico.idMedico = :medicoId")
	long countCitasByPacienteAndMedico(@Param("pacienteId") Integer pacienteId, @Param("medicoId") Integer medicoId);

}
