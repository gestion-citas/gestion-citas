package com.cibertec.gestioncitas.services;

import com.cibertec.gestioncitas.entities.Medico;
import com.cibertec.gestioncitas.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public List<Medico> obtenerTodos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> obtenerPorId(Integer id) {
        return medicoRepository.findById(id);
    }

    // ✅ CORREGIDO: Usar el método correcto
    public Medico obtenerPorUsuarioId(Integer usuarioId) {
        return medicoRepository.findByUsuarioId(usuarioId).orElse(null);
    }

    // ✅ CORREGIDO: Usar el método correcto
    public List<Medico> obtenerPorEspecialidad(Integer especialidadId) {
        return medicoRepository.findByEspecialidadId(especialidadId);
    }

    public Medico guardar(Medico medico) {
        return medicoRepository.save(medico);
    }

    public void eliminar(Integer id) {
        medicoRepository.deleteById(id);
    }

    public long contarTotal() {
        return medicoRepository.count();
    }
}
