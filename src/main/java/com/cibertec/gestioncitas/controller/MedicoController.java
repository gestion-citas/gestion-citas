package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.entity.Medico;
import com.cibertec.gestioncitas.service.MedicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    
    private final MedicoService medicoService;
    
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }
    
    // Listar todos los medicos
    @GetMapping
    public List<Medico> listar() {
        return medicoService.listarTodos();
    }
    
    // Obtener medico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerPorId(@PathVariable Integer id) {
        return medicoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Buscar medico por DNI
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Medico> buscarPorDni(@PathVariable String dni) {
        return medicoService.buscarPorDni(dni)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Listar medicos por especialidad
    @GetMapping("/especialidad/{idEspecialidad}")
    public List<Medico> listarPorEspecialidad(@PathVariable Integer idEspecialidad) {
        return medicoService.listarPorEspecialidad(idEspecialidad);
    }
    
    // Crear nuevo medico
    @PostMapping
    public ResponseEntity<Medico> crear(@RequestBody Medico medico) {
        try {
            Medico nuevo = medicoService.crear(medico);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Actualizar medico existente
    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(
            @PathVariable Integer id, 
            @RequestBody Medico medico) {
        try {
            Medico actualizado = medicoService.actualizar(id, medico);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Eliminar medico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            medicoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
