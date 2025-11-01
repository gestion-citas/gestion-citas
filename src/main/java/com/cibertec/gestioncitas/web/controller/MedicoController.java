package com.cibertec.gestioncitas.web.controller;

import com.cibertec.gestioncitas.entities.Medico;
import com.cibertec.gestioncitas.entities.Usuario;
import com.cibertec.gestioncitas.entities.Paciente;
import com.cibertec.gestioncitas.repositories.CitaRepository;
import com.cibertec.gestioncitas.repositories.MedicoRepository;
import com.cibertec.gestioncitas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.*;
import org.springframework.http.ResponseEntity;
import java.net.URLEncoder;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            String role = (String) session.getAttribute("role");

            if (username == null || role == null) {
                return "redirect:/login";
            }

            if (!role.equals("MEDICO")) {
                return "redirect:/?error=" + URLEncoder.encode("Acceso no autorizado", "UTF-8");
            }

            Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
            if (!usuario.isPresent()) {
                return "redirect:/?error=" + URLEncoder.encode("Usuario no encontrado", "UTF-8");
            }

            model.addAttribute("usuario", usuario.get());

            Optional<Medico> medicoOpt = medicoRepository.findByUsuarioIncludeInactive(usuario.get());
            if (!medicoOpt.isPresent()) {
                return "redirect:/?error=" + URLEncoder.encode("Médico no encontrado", "UTF-8");
            }

            Medico medico = medicoOpt.get();

            if (medico.getActivo() == null || medico.getActivo() == 0) {
                model.addAttribute("error", "Su cuenta está inactiva. Por favor contacte al administrador.");
                model.addAttribute("medicoInactivo", true);
                model.addAttribute("nombreMedico", medico.getNombres() + " " + medico.getApellidos());
                return "dashboard/medico/dashboard";
            }

            model.addAttribute("medico", medico);
            model.addAttribute("nombreMedico", medico.getNombres() + " " + medico.getApellidos());

            try {
                long totalCitas = citaRepository.countByMedico(medico);
                long citasPendientes = citaRepository.countByMedicoAndEstado(medico, "PROGRAMADA") +
                        citaRepository.countByMedicoAndEstado(medico, "CONFIRMADA");
                long citasAtendidas = citaRepository.countByMedicoAndEstado(medico, "ATENDIDA") +
                        citaRepository.countByMedicoAndEstado(medico, "COMPLETADA");
                long citasCanceladas = citaRepository.countByMedicoAndEstado(medico, "CANCELADA");
                long citasHoy = citaRepository.countCitasHoyByMedico(medico.getIdMedico());
                long totalPacientes = citaRepository.countPacientesUnicosByMedico(medico.getIdMedico());

                model.addAttribute("totalCitas", totalCitas);
                model.addAttribute("citasPendientes", citasPendientes);
                model.addAttribute("citasAtendidas", citasAtendidas);
                model.addAttribute("citasCanceladas", citasCanceladas);
                model.addAttribute("citasHoy", citasHoy);
                model.addAttribute("totalPacientes", totalPacientes);
            } catch (Exception e) {
                model.addAttribute("errorEstadisticas", "No se pudieron cargar algunas estadísticas");
            }

            // Cargar próximas citas
            try {
                var proximasCitas = citaRepository.findAllCitasByMedicoWithPaciente(medico.getIdMedico())
                        .stream()
                        .limit(10)
                        .toList();
                model.addAttribute("proximasCitas", proximasCitas);
            } catch (Exception e) {
                model.addAttribute("errorCitas", "No se pudieron cargar las citas");
            }

            // Cargar información de especialidad
            try {
                String especialidadNombre = medico.getEspecialidad() != null ?
                        medico.getEspecialidad().getNombre() : "Sin especialidad";
                String especialidadDescripcion = medico.getEspecialidad() != null ?
                        medico.getEspecialidad().getDescripcion() : "No especificada";
                model.addAttribute("especialidadMedico", especialidadNombre);
                model.addAttribute("especialidadDescripcion", especialidadDescripcion);
            } catch (Exception e) {
                model.addAttribute("errorEspecialidad", "No se pudo cargar la información de especialidad");
            }



            return "dashboard/medico/dashboard";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar el dashboard: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/citas")
    public String citas(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (username == null || !role.equals("MEDICO")) {
            return "redirect:/?error=unauthorized";
        }

        // Obtener información del médico para la página de citas
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            Optional<Medico> medicoOpt = medicoRepository.findByUsuario(usuario.get());
            if (medicoOpt.isPresent()) {
                Medico medico = medicoOpt.get();
                model.addAttribute("medico", medico);
                model.addAttribute("nombreMedico", medico.getNombres() + " " + medico.getApellidos());

                // ========== OBTENER CITAS REALES DEL MÉDICO ==========
                var citasMedico = citaRepository.findAllCitasByMedicoWithPaciente(medico.getIdMedico());
                model.addAttribute("citas", citasMedico);

                // ========== ESTADÍSTICAS PARA LA VISTA DE CITAS ==========
                long totalCitasMedico = citaRepository.countByMedico(medico);
                long citasPendientes = citaRepository.countByMedicoAndEstado(medico, "PROGRAMADA") +
                        citaRepository.countByMedicoAndEstado(medico, "CONFIRMADA");
                model.addAttribute("totalCitas", totalCitasMedico);
                model.addAttribute("citasPendientes", citasPendientes);
            }
        }
        return "dashboard/medico/citas";
    }


    @GetMapping("/pacientes")
    public String pacientes(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (username == null || !role.equals("MEDICO")) {
            return "redirect:/?error=unauthorized";
        }

        // Obtener información del médico para la página de pacientes
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            Optional<Medico> medicoOpt = medicoRepository.findByUsuario(usuario.get());
            if (medicoOpt.isPresent()) {
                Medico medico = medicoOpt.get();
                model.addAttribute("medico", medico);
                model.addAttribute("nombreMedico", medico.getNombres() + " " + medico.getApellidos());

                // ========== OBTENER PACIENTES REALES DEL MÉDICO ==========
                var pacientesMedico = citaRepository.findPacientesByMedico(medico.getIdMedico());
                
                // ========== CONTAR CITAS POR PACIENTE ==========
                Map<Integer, Long> citasPorPaciente = new HashMap<>();
                for (Paciente paciente : pacientesMedico) {
                    long countCitas = citaRepository.countCitasByPacienteAndMedico(paciente.getIdPaciente(), medico.getIdMedico());
                    citasPorPaciente.put(paciente.getIdPaciente(), countCitas);
                }
                
                model.addAttribute("pacientes", pacientesMedico);
                model.addAttribute("citasPorPaciente", citasPorPaciente);

                // ========== ESTADÍSTICAS PARA LA VISTA DE PACIENTES ==========
                long totalPacientesMedico = citaRepository.countPacientesUnicosByMedico(medico.getIdMedico());
                model.addAttribute("totalPacientes", totalPacientesMedico);
            }
        }
        return "dashboard/medico/pacientes";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (username == null || !role.equals("MEDICO")) {
            return "redirect:/?error=unauthorized";
        }

        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            // Obtener información completa del médico para el perfil
            Optional<Medico> medicoOpt = medicoRepository.findByUsuario(usuario.get());
            if (medicoOpt.isPresent()) {
                Medico medico = medicoOpt.get();
                model.addAttribute("medico", medico);
                model.addAttribute("nombreMedico", medico.getNombres() + " " + medico.getApellidos());
            }
        }
        return "dashboard/medico/perfil";
    }

    // ✅ NUEVO: Actualizar perfil del médico
    @PostMapping("/actualizar-perfil")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> actualizarPerfil(
            @RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam String email,
            @RequestParam(required = false) String telefono,
            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.put("success", false);
                response.put("message", "Usuario no autenticado");
                return ResponseEntity.ok(response);
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            if (!usuarioOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Usuario no encontrado");
                return ResponseEntity.ok(response);
            }

            Usuario usuario = usuarioOpt.get();
            Optional<Medico> medicoOpt = medicoRepository.findByUsuario(usuario);
            if (!medicoOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Médico no encontrado");
                return ResponseEntity.ok(response);
            }

            Medico medico = medicoOpt.get();
            medico.setNombres(nombres);
            medico.setApellidos(apellidos);
            medico.setEmail(email);
            if (telefono != null && !telefono.trim().isEmpty()) {
                medico.setTelefono(telefono);
            }

            medicoRepository.save(medico);
            response.put("success", true);
            response.put("message", "Perfil actualizado correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar perfil: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // ✅ NUEVO: Cambiar estado de cita
    @PostMapping("/cambiar-estado-cita")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cambiarEstadoCita(
            @RequestParam Integer citaId,
            @RequestParam String nuevoEstado,
            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.put("success", false);
                response.put("message", "Usuario no autenticado");
                return ResponseEntity.ok(response);
            }

            // Verificar que la cita existe y pertenece al médico
            Optional<com.cibertec.gestioncitas.entities.Cita> citaOpt = citaRepository.findById(citaId);
            if (!citaOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Cita no encontrada");
                return ResponseEntity.ok(response);
            }

            com.cibertec.gestioncitas.entities.Cita cita = citaOpt.get();

            // Verificar que el médico logueado es el dueño de la cita
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            if (usuarioOpt.isPresent()) {
                Optional<Medico> medicoOpt = medicoRepository.findByUsuario(usuarioOpt.get());
                if (medicoOpt.isPresent() && cita.getMedico().getIdMedico().equals(medicoOpt.get().getIdMedico())) {
                    cita.setEstado(nuevoEstado);
                    citaRepository.save(cita);
                    response.put("success", true);
                    response.put("message", "Estado de cita actualizado correctamente");
                    return ResponseEntity.ok(response);
                }
            }

            response.put("success", false);
            response.put("message", "No tienes permisos para modificar esta cita");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al cambiar estado: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
