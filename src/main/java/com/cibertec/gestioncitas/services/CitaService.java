package com.cibertec.gestioncitas.services;

import com.cibertec.gestioncitas.entities.Cita;
import com.cibertec.gestioncitas.repositories.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> obtenerPorId(Integer id) {
        return citaRepository.findById(id);
    }

    public List<Cita> obtenerPorMedico(Integer medicoId) {
        return citaRepository.findAll().stream()
                .filter(c -> c.getMedico() != null && c.getMedico().getIdMedico().equals(medicoId))
                .collect(Collectors.toList());
    }

    public List<Cita> obtenerPorPaciente(Integer pacienteId) {
        return citaRepository.findAll().stream()
                .filter(c -> c.getPaciente() != null && c.getPaciente().getIdPaciente().equals(pacienteId))
                .collect(Collectors.toList());
    }

    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    public Cita actualizar(Cita cita) {
        return citaRepository.save(cita);
    }

    public void eliminar(Integer id) {
        citaRepository.deleteById(id);
    }

    public long contarTotal() {
        return citaRepository.count();
    }

    // ✅ NUEVO: Obtener citas por estado
    public List<Cita> obtenerPorEstado(String estado) {
        return citaRepository.findAll().stream()
                .filter(c -> estado.equals(c.getEstado()))
                .collect(Collectors.toList());
    }

    // ✅ NUEVO: Actualizar estado de cita
    @Transactional
    public Cita actualizarEstadoCita(Integer citaId, String nuevoEstado) {
        Optional<Cita> citaOpt = citaRepository.findById(citaId);
        
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            cita.setEstado(nuevoEstado);
            return citaRepository.save(cita);
        }
        
        return null;
    }
}
