package com.cibertec.gestioncitas.services;

import com.cibertec.gestioncitas.entities.Especialidad;
import com.cibertec.gestioncitas.repositories.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public List<Especialidad> obtenerTodas() {
        return especialidadRepository.findAll();
    }

    public Optional<Especialidad> obtenerPorId(Integer id) {
        return especialidadRepository.findById(id);
    }

    public Especialidad guardar(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public void eliminar(Integer id) {
        especialidadRepository.deleteById(id);
    }

    public long contarTotal() {
        return especialidadRepository.count();
    }
}
