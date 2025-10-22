package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.entity.Medico;
import com.cibertec.gestioncitas.service.MedicoService;
import com.cibertec.gestioncitas.service.EspecialidadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medicos")
public class MedicoWebController {
    
    private final MedicoService medicoService;
    private final EspecialidadService especialidadService;
    
    public MedicoWebController(MedicoService medicoService, 
                               EspecialidadService especialidadService) {
        this.medicoService = medicoService;
        this.especialidadService = especialidadService;
    }
    
    // Listar todos los médicos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("medicos", medicoService.listarTodos());
        model.addAttribute("titulo", "Listado de Médicos");
        return "medicos/listar";
    }
    
    // Mostrar formulario para crear nuevo médico
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Medico medico = new Medico();
        model.addAttribute("medico", medico);
        model.addAttribute("especialidades", especialidadService.listarTodas());
        model.addAttribute("titulo", "Nuevo Médico");
        model.addAttribute("accion", "nuevo");
        return "medicos/formulario";
    }
    
    // Mostrar formulario para editar médico existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Medico medico = medicoService.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado: " + id));
        
        model.addAttribute("medico", medico);
        model.addAttribute("especialidades", especialidadService.listarTodas());
        model.addAttribute("titulo", "Editar Médico");
        model.addAttribute("accion", "editar");
        return "medicos/formulario";
    }
    
    // Guardar médico (nuevo o editado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Medico medico, 
                         RedirectAttributes redirectAttributes) {
        try {
            if (medico.getIdMedico() == null) {
                medicoService.crear(medico);
                redirectAttributes.addFlashAttribute("success", 
                    "Médico creado exitosamente");
            } else {
                medicoService.actualizar(medico.getIdMedico(), medico);
                redirectAttributes.addFlashAttribute("success", 
                    "Médico actualizado exitosamente");
            }
            return "redirect:/medicos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al guardar el médico: " + e.getMessage());
            return "redirect:/medicos/nuevo";
        }
    }
    
    // Eliminar médico
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, 
                          RedirectAttributes redirectAttributes) {
        try {
            medicoService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", 
                "Médico eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "No se puede eliminar el médico. Tiene citas asociadas.");
        }
        return "redirect:/medicos";
    }
}
