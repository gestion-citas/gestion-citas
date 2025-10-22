package com.cibertec.gestioncitas.service;

import com.cibertec.gestioncitas.entity.Paciente;
import com.cibertec.gestioncitas.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteService {
    
    private final PacienteRepository pacienteRepository;
    
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorId(Integer id) {
        return pacienteRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }
    
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorNombreOApellido(String texto) {
        return pacienteRepository.buscarPorNombreOApellido(texto);
    }
    
    public Paciente crear(Paciente paciente) {
        if (pacienteRepository.existsByDni(paciente.getDni())) {
            throw new IllegalArgumentException("Ya existe un paciente con ese DNI");
        }
        validarPaciente(paciente);
        return pacienteRepository.save(paciente);
    }
    
    public Paciente actualizar(Integer id, Paciente paciente) {
        Paciente existente = pacienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        
        if (!existente.getDni().equals(paciente.getDni()) && 
            pacienteRepository.existsByDni(paciente.getDni())) {
            throw new IllegalArgumentException("Ya existe un paciente con ese DNI");
        }
        
        validarPaciente(paciente);
        existente.setDni(paciente.getDni());
        existente.setNombres(paciente.getNombres());
        existente.setApellidos(paciente.getApellidos());
        existente.setCorreo(paciente.getCorreo());
        existente.setTelefono(paciente.getTelefono());
        
        return pacienteRepository.save(existente);
    }
    
    public void eliminar(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
    }
    
    private void validarPaciente(Paciente paciente) {
        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        if (paciente.getNombres() == null || paciente.getNombres().trim().isEmpty()) {
            throw new IllegalArgumentException("Los nombres son obligatorios");
        }
        if (paciente.getApellidos() == null || paciente.getApellidos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }
    }
}
