package com.cibertec.gestioncitas.services;

import com.cibertec.gestioncitas.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EstadisticaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public Map<String, Long> obtenerCitasPorEspecialidad() {
        Map<String, Long> estadisticas = new LinkedHashMap<>();
        especialidadRepository.findAll().forEach(especialidad -> {
            long count = citaRepository.countByMedicoEspecialidad(especialidad);
            estadisticas.put(especialidad.getNombre(), count);
        });
        return estadisticas;
    }

    public Map<String, Long> obtenerCitasPorMes() {
        Map<String, Long> estadisticas = new LinkedHashMap<>();
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        
        for (int i = 0; i < 6; i++) {
            LocalDate mes = hoy.minusMonths(i);
            long count = citaRepository.countByMes(mes.getMonthValue(), mes.getYear());
            estadisticas.put(mes.format(formatter), count);
        }
        return estadisticas;
    }

    public Map<String, Object> obtenerEstadisticasMedico(Integer idMedico) {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalCitas = citaRepository.countByMedicoId(idMedico);
        long citasPendientes = citaRepository.countByMedicoIdAndEstado(idMedico, "PROGRAMADA");
        long citasConfirmadas = citaRepository.countByMedicoIdAndEstado(idMedico, "CONFIRMADA");
        long citasCompletadas = citaRepository.countByMedicoIdAndEstado(idMedico, "COMPLETADA");
        long citasCanceladas = citaRepository.countByMedicoIdAndEstado(idMedico, "CANCELADA");
        long citasHoy = citaRepository.countCitasHoyByMedico(idMedico);
        long pacientesUnicos = citaRepository.countPacientesUnicosByMedico(idMedico);
        
        estadisticas.put("totalCitas", totalCitas);
        estadisticas.put("citasPendientes", citasPendientes);
        estadisticas.put("citasConfirmadas", citasConfirmadas);
        estadisticas.put("citasCompletadas", citasCompletadas);
        estadisticas.put("citasCanceladas", citasCanceladas);
        estadisticas.put("citasHoy", citasHoy);
        estadisticas.put("pacientesUnicos", pacientesUnicos);
        
        return estadisticas;
    }

    public Map<String, Object> obtenerEstadisticasPaciente(Integer idPaciente) {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalCitas = citaRepository.countByPacienteId(idPaciente);
        long citasPendientes = citaRepository.countByPacienteIdAndEstado(idPaciente, "PROGRAMADA");
        long citasConfirmadas = citaRepository.countByPacienteIdAndEstado(idPaciente, "CONFIRMADA");
        long citasCompletadas = citaRepository.countByPacienteIdAndEstado(idPaciente, "COMPLETADA");
        long citasCanceladas = citaRepository.countByPacienteIdAndEstado(idPaciente, "CANCELADA");
        long citasHoy = citaRepository.countCitasHoyByPaciente(idPaciente);
        long medicosVisitados = citaRepository.countMedicosUnicosByPaciente(idPaciente);
        
        estadisticas.put("totalCitas", totalCitas);
        estadisticas.put("citasPendientes", citasPendientes);
        estadisticas.put("citasConfirmadas", citasConfirmadas);
        estadisticas.put("citasCompletadas", citasCompletadas);
        estadisticas.put("citasCanceladas", citasCanceladas);
        estadisticas.put("citasHoy", citasHoy);
        estadisticas.put("medicosVisitados", medicosVisitados);
        
        return estadisticas;
    }
}
