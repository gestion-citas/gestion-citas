package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;

@Controller
public class HomeController {
    
    private final EspecialidadService especialidadService;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    private final CitaService citaService;
    
    public HomeController(
            EspecialidadService especialidadService,
            MedicoService medicoService,
            PacienteService pacienteService,
            CitaService citaService) {
        this.especialidadService = especialidadService;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
        this.citaService = citaService;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        // Obtener datos para la página principal
        model.addAttribute("especialidades", especialidadService.listarTodas());
        model.addAttribute("totalMedicos", medicoService.listarTodos().size());
        model.addAttribute("totalPacientes", pacienteService.listarTodos().size());
        model.addAttribute("totalEspecialidades", especialidadService.listarTodas().size());
        
        // Contar citas de hoy
        long citasHoy = citaService.listarTodas().stream()
            .filter(c -> c.getFecha().equals(LocalDate.now()))
            .count();
        model.addAttribute("totalCitas", citasHoy);
        
        return "index";
    }
}
