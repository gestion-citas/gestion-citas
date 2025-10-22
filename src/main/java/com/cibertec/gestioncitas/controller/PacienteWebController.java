package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.entity.Paciente;
import com.cibertec.gestioncitas.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes")
public class PacienteWebController {
    
    private final PacienteService pacienteService;
    
    public PacienteWebController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    
    // Listar todos los pacientes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("titulo", "Listado de Pacientes");
        return "pacientes/listar";
    }
    
    // Mostrar formulario para crear nuevo paciente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Paciente paciente = new Paciente();
        model.addAttribute("paciente", paciente);
        model.addAttribute("titulo", "Nuevo Paciente");
        model.addAttribute("accion", "nuevo");
        return "pacientes/formulario";
    }
    
    // Mostrar formulario para editar paciente existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Paciente paciente = pacienteService.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado: " + id));
        
        model.addAttribute("paciente", paciente);
        model.addAttribute("titulo", "Editar Paciente");
        model.addAttribute("accion", "editar");
        return "pacientes/formulario";
    }
    
    // Guardar paciente (nuevo o editado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Paciente paciente, 
                         RedirectAttributes redirectAttributes) {
        try {
            if (paciente.getIdPaciente() == null) {
                pacienteService.crear(paciente);
                redirectAttributes.addFlashAttribute("success", 
                    "Paciente creado exitosamente");
            } else {
                pacienteService.actualizar(paciente.getIdPaciente(), paciente);
                redirectAttributes.addFlashAttribute("success", 
                    "Paciente actualizado exitosamente");
            }
            return "redirect:/pacientes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al guardar el paciente: " + e.getMessage());
            return "redirect:/pacientes/nuevo";
        }
    }
    
    // Eliminar paciente
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, 
                          RedirectAttributes redirectAttributes) {
        try {
            pacienteService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", 
                "Paciente eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "No se puede eliminar el paciente. Tiene citas asociadas.");
        }
        return "redirect:/pacientes";
    }
}
