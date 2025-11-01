package com.cibertec.gestioncitas.web.controller;

import com.cibertec.gestioncitas.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * CONTROLADOR DE INICIO
 * Maneja la página principal del sistema
 */

@Controller
public class HomeController {

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * PÁGINA PRINCIPAL - MUESTRA INDEX CON MODAL LOGIN
     */
    @GetMapping({"/", "/index", "/index.html"})
    public String index(Model model) {
        try {

            // ✅ CORRECCIÓN: obtenerTodos() en lugar de obtenerTodas()
            model.addAttribute("especialidades", especialidadService.obtenerTodas());

            model.addAttribute("medicos", medicoService.obtenerTodos());

            model.addAttribute("totalPacientes", pacienteService.obtenerTodos().size());

            // ✅ CORRECCIÓN: obtenerTodos() en lugar de obtenerTodas()
            model.addAttribute("totalEspecialidades", especialidadService.obtenerTodas().size());

            model.addAttribute("totalMedicos", medicoService.obtenerTodos().size());

            model.addAttribute("totalUsuarios", usuarioService.obtenerTodos().size());

            return "index";

        } catch (Exception e) {
            e.printStackTrace();
            return "index";
        }
    }
}
