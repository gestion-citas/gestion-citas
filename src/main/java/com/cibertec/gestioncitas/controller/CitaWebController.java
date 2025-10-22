package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.entity.Cita;
import com.cibertec.gestioncitas.service.CitaService;
import com.cibertec.gestioncitas.service.MedicoService;
import com.cibertec.gestioncitas.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/citas")
public class CitaWebController {
    
    private final CitaService citaService;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    
    public CitaWebController(CitaService citaService, 
                            MedicoService medicoService,
                            PacienteService pacienteService) {
        this.citaService = citaService;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }
    
    // Listar todas las citas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaService.listarTodas());
        model.addAttribute("titulo", "Listado de Citas");
        return "citas/listar";
    }
    
    // Mostrar formulario para crear nueva cita
    @GetMapping("/nueva")
    public String mostrarFormularioNuevo(Model model) {
        Cita cita = new Cita();
        // Valores por defecto
        cita.setFecha(LocalDate.now());
        cita.setHora(LocalTime.of(9, 0));
        cita.setEstado(Cita.ESTADO_PROGRAMADA);
        
        model.addAttribute("cita", cita);
        model.addAttribute("medicos", medicoService.listarTodos());
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("titulo", "Nueva Cita");
        model.addAttribute("accion", "nueva");
        return "citas/formulario";
    }
    
    // Mostrar formulario para editar cita existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Cita cita = citaService.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada: " + id));
        
        model.addAttribute("cita", cita);
        model.addAttribute("medicos", medicoService.listarTodos());
        model.addAttribute("pacientes", pacienteService.listarTodos());
        model.addAttribute("titulo", "Editar Cita");
        model.addAttribute("accion", "editar");
        return "citas/formulario";
    }
    
    // Guardar cita (nueva o editada)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cita cita, 
                         RedirectAttributes redirectAttributes) {
        try {
            if (cita.getIdCita() == null) {
                citaService.crear(cita);
                redirectAttributes.addFlashAttribute("success", 
                    "Cita creada exitosamente");
            } else {
                citaService.actualizar(cita.getIdCita(), cita);
                redirectAttributes.addFlashAttribute("success", 
                    "Cita actualizada exitosamente");
            }
            return "redirect:/citas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al guardar la cita: " + e.getMessage());
            return "redirect:/citas/nueva";
        }
    }
    
    // Cancelar cita
    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Integer id, 
                          RedirectAttributes redirectAttributes) {
        try {
            Cita cita = citaService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
            
            cita.setEstado(Cita.ESTADO_CANCELADA);
            citaService.actualizar(id, cita);
            
            redirectAttributes.addFlashAttribute("success", 
                "Cita cancelada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al cancelar la cita: " + e.getMessage());
        }
        return "redirect:/citas";
    }
    
    // Eliminar cita
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, 
                          RedirectAttributes redirectAttributes) {
        try {
            citaService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", 
                "Cita eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al eliminar la cita: " + e.getMessage());
        }
        return "redirect:/citas";
    }
}
