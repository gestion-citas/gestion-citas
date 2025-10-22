package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.entity.Paciente;
import com.cibertec.gestioncitas.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    
    private final PacienteService pacienteService;
    
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    
    // Listar todos los pacientes
    @GetMapping
    public List<Paciente> listar() {
        return pacienteService.listarTodos();
    }
    
    // Obtener paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Integer id) {
        return pacienteService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Buscar paciente por DNI
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Paciente> buscarPorDni(@PathVariable String dni) {
        return pacienteService.buscarPorDni(dni)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Buscar pacientes por nombre o apellido
    @GetMapping("/buscar")
    public List<Paciente> buscar(@RequestParam String texto) {
        return pacienteService.buscarPorNombreOApellido(texto);
    }
    
    // Crear nuevo paciente
    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        try {
            Paciente nuevo = pacienteService.crear(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Actualizar paciente existente
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Integer id, 
            @RequestBody Paciente paciente) {
        try {
            Paciente actualizado = pacienteService.actualizar(id, paciente);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            pacienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
