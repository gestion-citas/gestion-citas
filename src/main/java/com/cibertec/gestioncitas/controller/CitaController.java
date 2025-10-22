package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.entity.Cita;
import com.cibertec.gestioncitas.service.CitaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    
    private final CitaService citaService;
    
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }
    
    // Listar todas las citas
    @GetMapping
    public List<Cita> listar() {
        return citaService.listarTodas();
    }
    
    // Obtener cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Integer id) {
        return citaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Listar citas por estado
    @GetMapping("/estado/{estado}")
    public List<Cita> listarPorEstado(@PathVariable String estado) {
        return citaService.listarPorEstado(estado);
    }
    
    // Citas programadas de hoy
    @GetMapping("/hoy")
    public List<Cita> citasHoy() {
        return citaService.citasProgramadasHoy();
    }
    
    // Citas por medico y rango de fechas
    @GetMapping("/medico/{idMedico}")
    public List<Cita> citasPorMedico(
            @PathVariable Integer idMedico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return citaService.listarPorMedicoYFechas(idMedico, desde, hasta);
    }
    
    // Citas por paciente y rango de fechas
    @GetMapping("/paciente/{idPaciente}")
    public List<Cita> citasPorPaciente(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return citaService.listarPorPacienteYFechas(idPaciente, desde, hasta);
    }
    
    // Crear nueva cita
    @PostMapping
    public ResponseEntity<Cita> crear(@RequestBody Cita cita) {
        try {
            Cita nueva = citaService.crear(cita);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Actualizar cita existente
    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizar(
            @PathVariable Integer id, 
            @RequestBody Cita cita) {
        try {
            Cita actualizada = citaService.actualizar(id, cita);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Cancelar cita
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        try {
            citaService.cancelar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Atender cita
    @PatchMapping("/{id}/atender")
    public ResponseEntity<Void> atender(@PathVariable Integer id) {
        try {
            citaService.atender(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Eliminar cita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            citaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
