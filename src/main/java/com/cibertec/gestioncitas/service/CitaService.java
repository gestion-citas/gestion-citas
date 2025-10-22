package com.cibertec.gestioncitas.service;

import com.cibertec.gestioncitas.entity.Cita;
import com.cibertec.gestioncitas.repository.CitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar las citas medicas
 * Incluye logica de negocio para programar, actualizar y gestionar estados
 */
@Service
@Transactional
public class CitaService {
    
    private final CitaRepository citaRepository;
    
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }
    
    /**
     * Lista todas las citas del sistema
     * @return Lista completa de citas
     */
    @Transactional(readOnly = true)
    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }
    
    /**
     * Obtiene una cita por su ID
     * @param id Identificador de la cita
     * @return Optional con la cita si existe
     */
    @Transactional(readOnly = true)
    public Optional<Cita> obtenerPorId(Integer id) {
        return citaRepository.findById(id);
    }
    
    /**
     * Lista citas filtradas por estado
     * @param estado Estado de la cita (PROGRAMADA, ATENDIDA, CANCELADA)
     * @return Lista de citas con ese estado
     */
    @Transactional(readOnly = true)
    public List<Cita> listarPorEstado(String estado) {
        return citaRepository.findByEstado(estado);
    }
    
    /**
     * Obtiene las citas programadas para hoy
     * Util para el panel del dia del recepcionista
     * @return Lista de citas programadas para hoy ordenadas por hora
     */
    @Transactional(readOnly = true)
    public List<Cita> citasProgramadasHoy() {
        return citaRepository.findCitasProgramadasHoy();
    }
    
    /**
     * Lista citas de un medico en un rango de fechas
     * @param idMedico ID del medico
     * @param desde Fecha inicial del rango
     * @param hasta Fecha final del rango
     * @return Lista de citas ordenadas por fecha y hora
     */
    @Transactional(readOnly = true)
    public List<Cita> listarPorMedicoYFechas(Integer idMedico, LocalDate desde, LocalDate hasta) {
        return citaRepository.findByMedicoAndFechaBetween(idMedico, desde, hasta);
    }
    
    /**
     * Lista citas de un paciente en un rango de fechas
     * @param idPaciente ID del paciente
     * @param desde Fecha inicial
     * @param hasta Fecha final
     * @return Lista de citas ordenadas por fecha descendente
     */
    @Transactional(readOnly = true)
    public List<Cita> listarPorPacienteYFechas(Integer idPaciente, LocalDate desde, LocalDate hasta) {
        return citaRepository.findByPacienteAndFechaBetween(idPaciente, desde, hasta);
    }
    
    /**
     * Crea una nueva cita medica
     * Valida que tenga paciente, medico, fecha y hora
     * @param cita Objeto cita a crear
     * @return Cita creada con ID generado
     * @throws IllegalArgumentException si faltan datos obligatorios
     */
    public Cita crear(Cita cita) {
        // Validar datos obligatorios usando metodo de la entidad
        cita.validar();
        
        // Si no tiene estado, asignar PROGRAMADA por defecto
        if (cita.getEstado() == null || cita.getEstado().isEmpty()) {
            cita.setEstado(Cita.ESTADO_PROGRAMADA);
        }
        
        return citaRepository.save(cita);
    }
    
    /**
     * Actualiza una cita existente
     * @param id ID de la cita a actualizar
     * @param cita Nuevos datos de la cita
     * @return Cita actualizada
     * @throws IllegalArgumentException si no existe la cita
     */
    public Cita actualizar(Integer id, Cita cita) {
        // Buscar cita existente
        Cita existente = citaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        
        // Validar nuevos datos
        cita.validar();
        
        // Actualizar campos
        existente.setPaciente(cita.getPaciente());
        existente.setMedico(cita.getMedico());
        existente.setFecha(cita.getFecha());
        existente.setHora(cita.getHora());
        existente.setEstado(cita.getEstado());
        existente.setMotivo(cita.getMotivo());
        
        return citaRepository.save(existente);
    }
    
    /**
     * Cambia el estado de una cita
     * Metodo auxiliar para cambios de estado rapidos
     * @param id ID de la cita
     * @param nuevoEstado Nuevo estado (usar constantes de Cita)
     * @return Cita con estado actualizado
     */
    public Cita cambiarEstado(Integer id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        
        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }
    
    /**
     * Cancela una cita (atajo para cambiar estado a CANCELADA)
     * @param id ID de la cita a cancelar
     */
    public void cancelar(Integer id) {
        cambiarEstado(id, Cita.ESTADO_CANCELADA);
    }
    
    /**
     * Marca una cita como atendida
     * @param id ID de la cita
     */
    public void atender(Integer id) {
        cambiarEstado(id, Cita.ESTADO_ATENDIDA);
    }
    
    /**
     * Elimina una cita del sistema
     * @param id ID de la cita a eliminar
     * @throws IllegalArgumentException si no existe
     */
    public void eliminar(Integer id) {
        if (!citaRepository.existsById(id)) {
            throw new IllegalArgumentException("Cita no encontrada");
        }
        citaRepository.deleteById(id);
    }
}

