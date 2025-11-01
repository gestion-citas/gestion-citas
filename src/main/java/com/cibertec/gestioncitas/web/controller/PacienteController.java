package com.cibertec.gestioncitas.web.controller;

import com.cibertec.gestioncitas.entities.Paciente;
import com.cibertec.gestioncitas.entities.Usuario;
import com.cibertec.gestioncitas.entities.Medico;
import com.cibertec.gestioncitas.entities.Cita;
import com.cibertec.gestioncitas.repositories.CitaRepository;
import com.cibertec.gestioncitas.repositories.EspecialidadRepository;
import com.cibertec.gestioncitas.repositories.MedicoRepository;
import com.cibertec.gestioncitas.repositories.PacienteRepository;
import com.cibertec.gestioncitas.repositories.UsuarioRepository;
import com.cibertec.gestioncitas.services.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired 
    private EstadisticaService estadisticaService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        
        if (username == null || !role.equals("PACIENTE")) {
            return "redirect:/?error=unauthorized";
        }
        
        
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            
            // Buscar el paciente asociado al usuario
            Optional<Paciente> pacienteOpt = pacienteRepository.findByUsuario(usuario.get());
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                model.addAttribute("paciente", paciente);
                
                // Obtener estadísticas del paciente
                var estadisticas = estadisticaService.obtenerEstadisticasPaciente(paciente.getIdPaciente());
                model.addAllAttributes(estadisticas);
                
                // Obtener próximas citas
                var proximasCitas = citaRepository.findProximasCitasPaciente(paciente.getIdPaciente());
                model.addAttribute("proximasCitas", proximasCitas);
                
                // Obtener historial de citas
                var historialCitas = citaRepository.findByPacienteId(paciente.getIdPaciente());
                model.addAttribute("historialCitas", historialCitas);
                
                // Obtener médicos disponibles para nuevas citas
                var medicosDisponibles = medicoRepository.findAllActivos();
                model.addAttribute("medicosDisponibles", medicosDisponibles);
                
            }
        }
        
        return "dashboard/paciente/dashboard";
    }

    @GetMapping("/citas")
    public String verCitas(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        
        if (username == null || !role.equals("PACIENTE")) {
            return "redirect:/?error=unauthorized";
        }
        
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            Optional<Paciente> pacienteOpt = pacienteRepository.findByUsuario(usuario.get());
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                
                // Obtener todas las citas del paciente
                var citas = citaRepository.findByPacienteId(paciente.getIdPaciente());
                model.addAttribute("citas", citas);
                model.addAttribute("nombrePaciente", paciente.getNombres() + " " + paciente.getApellidos());
                
                // Estadísticas de citas
                var estadisticas = estadisticaService.obtenerEstadisticasPaciente(paciente.getIdPaciente());
                model.addAllAttributes(estadisticas);
                
                model.addAttribute("paciente", paciente);
            }
        }
        
        return "dashboard/paciente/citas";
    }

    @GetMapping("/registrar-cita")
    public String registrarCita(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        
        if (username == null || !role.equals("PACIENTE")) {
            return "redirect:/?error=unauthorized";
        }
        
        
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            Optional<Paciente> pacienteOpt = pacienteRepository.findByUsuario(usuario.get());
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                model.addAttribute("nombrePaciente", paciente.getNombres() + " " + paciente.getApellidos());
                model.addAttribute("paciente", paciente);
            }
        }
        
        model.addAttribute("especialidades", especialidadRepository.findEspecialidadesConMedicosActivos());
        model.addAttribute("medicos", medicoRepository.findAllActivos());
        return "dashboard/paciente/registrar-cita";
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        
        if (username == null || !role.equals("PACIENTE")) {
            return "redirect:/?error=unauthorized";
        }
        
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            Optional<Paciente> pacienteOpt = pacienteRepository.findByUsuario(usuario.get());
            if (pacienteOpt.isPresent()) {
                model.addAttribute("paciente", pacienteOpt.get());
                model.addAttribute("usuario", usuario.get());
                
                // Obtener estadísticas básicas
                var estadisticas = estadisticaService.obtenerEstadisticasPaciente(pacienteOpt.get().getIdPaciente());
                model.addAllAttributes(estadisticas);
            }
        }
        
        return "dashboard/paciente/perfil";
    }

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

            Optional<Paciente> pacienteOpt = pacienteRepository.findByUsuario(usuarioOpt.get());
            if (!pacienteOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Paciente no encontrado");
                return ResponseEntity.ok(response);
            }

            Paciente paciente = pacienteOpt.get();
            paciente.setNombres(nombres);
            paciente.setApellidos(apellidos);
            paciente.setEmail(email);
            if (telefono != null && !telefono.trim().isEmpty()) {
                paciente.setTelefono(telefono);
            }

            pacienteRepository.save(paciente);
            response.put("success", true);
            response.put("message", "Perfil actualizado correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar perfil: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/registrar-cita")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrarCita(
            @RequestParam Integer medicoId,
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam(required = false) String motivo,
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

            Optional<Paciente> pacienteOpt = pacienteRepository.findByUsuario(usuarioOpt.get());
            if (!pacienteOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Paciente no encontrado");
                return ResponseEntity.ok(response);
            }

            Optional<Medico> medicoOpt = medicoRepository.findById(medicoId);
            if (!medicoOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Médico no encontrado");
                return ResponseEntity.ok(response);
            }

            Cita nuevaCita = new Cita();
            nuevaCita.setPaciente(pacienteOpt.get());
            nuevaCita.setMedico(medicoOpt.get());
            nuevaCita.setFecha(java.sql.Date.valueOf(fecha));
            nuevaCita.setHora(hora);
            nuevaCita.setMotivo(motivo);
            nuevaCita.setEstado("PROGRAMADA");
            nuevaCita.setDuracion(30); // Estableciendo duración predeterminada
            nuevaCita.setAsistencia(false); // Inicializando asistencia
            nuevaCita.setObservaciones(""); // Inicializando observaciones vacías

            citaRepository.save(nuevaCita);

            response.put("success", true);
            response.put("message", "Cita registrada correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al registrar la cita: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/medicos-por-especialidad/{especialidadId}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerMedicosPorEspecialidad(
            @PathVariable Integer especialidadId,
            HttpSession session) {
        try {
            
            String username = (String) session.getAttribute("username");
            String role = (String) session.getAttribute("role");
            
            if (username == null || !role.equals("PACIENTE")) {
                return ResponseEntity.status(401).build();
            }

            List<Medico> medicos = medicoRepository.findByEspecialidadId(especialidadId);
            
            List<Map<String, Object>> medicosResponse = new ArrayList<>();
            
            for (Medico medico : medicos) {
                if (medico.getActivo() != null && medico.getActivo() == 1) {
                    Map<String, Object> medicoData = new HashMap<>();
                    medicoData.put("idMedico", medico.getIdMedico());
                    medicoData.put("nombres", medico.getNombres());
                    medicoData.put("apellidos", medico.getApellidos());
                    medicosResponse.add(medicoData);
                }
            }
            
            return ResponseEntity.ok(medicosResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
