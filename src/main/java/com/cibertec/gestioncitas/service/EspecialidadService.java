package com.cibertec.gestioncitas.service;

import com.cibertec.gestioncitas.entity.Especialidad;
import com.cibertec.gestioncitas.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EspecialidadService {
    
    private final EspecialidadRepository especialidadRepository;
    
    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Especialidad> obtenerPorId(Integer id) {
        return especialidadRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Especialidad> buscarPorNombre(String nombre) {
        return especialidadRepository.findByNombre(nombre);
    }
    
    public Especialidad crear(Especialidad especialidad) {
        if (especialidadRepository.existsByNombre(especialidad.getNombre())) {
            throw new IllegalArgumentException("Ya existe una especialidad con ese nombre");
        }
        return especialidadRepository.save(especialidad);
    }
    
    public Especialidad actualizar(Integer id, Especialidad especialidad) {
        Especialidad existente = especialidadRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada"));
        
        existente.setNombre(especialidad.getNombre());
        return especialidadRepository.save(existente);
    }
    
    public void eliminar(Integer id) {
        if (!especialidadRepository.existsById(id)) {
            throw new IllegalArgumentException("Especialidad no encontrada");
        }
        especialidadRepository.deleteById(id);
    }
}
