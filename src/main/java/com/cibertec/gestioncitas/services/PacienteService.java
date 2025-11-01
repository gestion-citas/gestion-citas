package com.cibertec.gestioncitas.services;

import com.cibertec.gestioncitas.entities.Paciente;
import com.cibertec.gestioncitas.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    // ✅ NUEVO: Obtener paciente por ID de usuario
    public Paciente obtenerPorUsuarioId(Integer usuarioId) {
        return pacienteRepository.findAll().stream()
                .filter(p -> p.getUsuario() != null && p.getUsuario().getIdUsuario().equals(usuarioId))
                .findFirst()
                .orElse(null);
    }

    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void eliminar(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public long contarTotal() {
        return pacienteRepository.count();
    }
}
