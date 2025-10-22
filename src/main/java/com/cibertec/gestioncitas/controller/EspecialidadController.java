package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.entity.Especialidad;
import com.cibertec.gestioncitas.service.EspecialidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {
    
    private final EspecialidadService especialidadService;
    
    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }
    
    // GET /api/especialidades - Listar todas
    @GetMapping
    public List<Especialidad> listar() {
        return especialidadService.listarTodas();
    }
    
    // GET /api/especialidades/{id} - Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtenerPorId(@PathVariable Integer id) {
        return especialidadService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/especialidades - Crear nueva
    @PostMapping
    public ResponseEntity<Especialidad> crear(@RequestBody Especialidad especialidad) {
        try {
            Especialidad nueva = especialidadService.crear(especialidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT /api/especialidades/{id} - Actualizar existente
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> actualizar(
            @PathVariable Integer id, 
            @RequestBody Especialidad especialidad) {
        try {
            Especialidad actualizada = especialidadService.actualizar(id, especialidad);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/especialidades/{id} - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            especialidadService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
