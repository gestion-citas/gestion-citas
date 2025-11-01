package com.cibertec.gestioncitas.web.controller;

import com.cibertec.gestioncitas.entities.*;
import com.cibertec.gestioncitas.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private CitaService citaService;

    @Autowired
    private EstadisticaService estadisticaService;

    @GetMapping({"/dashboard", ""})
    public String dashboard(Model model, jakarta.servlet.http.HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        
        if (username == null || !role.equals("ADMIN")) {
            return "redirect:/?error=unauthorized";
        }
        long totalUsuarios = usuarioService.obtenerTodos().size();
        long totalMedicos = medicoService.obtenerTodos().size();
        long totalPacientes = pacienteService.obtenerTodos().size();
        long totalEspecialidades = especialidadService.obtenerTodas().size();
        long totalCitas = citaService.obtenerTodas().size();
        
        // Estadísticas de citas usando CitaRepository directamente
        long citasPendientes = citaService.obtenerPorEstado("PROGRAMADA").size() + 
                             citaService.obtenerPorEstado("CONFIRMADA").size();
        long citasCompletadas = citaService.obtenerPorEstado("COMPLETADA").size();
        long citasCanceladas = citaService.obtenerPorEstado("CANCELADA").size();
        long citasHoy = citaService.obtenerTodas().stream()
                       .filter(c -> c.getFecha().equals(new java.util.Date()))
                       .count();
        
        // Lista de próximas citas
        var proximasCitas = citaService.obtenerTodas().stream()
                           .filter(c -> c.getFecha().after(new java.util.Date()) || 
                                       c.getFecha().equals(new java.util.Date()))
                           .limit(10)
                           .toList();
        
        // Datos para gráficos usando EstadisticaService
        var citasPorEspecialidad = estadisticaService.obtenerCitasPorEspecialidad();
        var citasPorMes = estadisticaService.obtenerCitasPorMes();
        
        // Agregar datos al modelo
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalMedicos", totalMedicos);
        model.addAttribute("totalPacientes", totalPacientes);
        model.addAttribute("totalEspecialidades", totalEspecialidades);
        model.addAttribute("totalCitas", totalCitas);
        model.addAttribute("citasPendientes", citasPendientes);
        model.addAttribute("citasCompletadas", citasCompletadas);
        model.addAttribute("citasCanceladas", citasCanceladas);
        model.addAttribute("citasHoy", citasHoy);
        model.addAttribute("proximasCitas", proximasCitas);
        model.addAttribute("citasPorEspecialidad", citasPorEspecialidad);
        model.addAttribute("citasPorMes", citasPorMes);
        
        return "dashboard/admin/dashboard";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model, jakarta.servlet.http.HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        
        if (username == null || !role.equals("ADMIN")) {
            return "redirect:/?error=unauthorized";
        }
        
        model.addAttribute("usuarios", usuarioService.obtenerTodos());
        return "dashboard/admin/usuarios/listar";
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "dashboard/admin/usuarios/formulario";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dashboard/admin/usuarios/formulario";
        }

        try {
            usuarioService.guardar(usuario);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        return "dashboard/admin/usuarios/editar";
    }

    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(
            @RequestParam Integer idUsuario,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String nombres,
            @RequestParam(required = false) String apellidos,
            @RequestParam String role,
            @RequestParam(required = false) String activo,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.obtenerPorId(idUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            usuario.setUsername(username);
            usuario.setEmail(email);
            usuario.setNombres(nombres);
            usuario.setApellidos(apellidos);
            usuario.setRole(role);
            
            if (password != null && !password.isEmpty()) {
                usuario.setPassword(password);
            }
            
            if (activo != null) {
                if ("true".equalsIgnoreCase(activo) || "1".equals(activo)) {
                    usuario.setActivo(1);
                } else {
                    usuario.setActivo(0);
                }
            }

            usuarioService.guardar(usuario);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("successMessage", "Usuario eliminado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar");
        }

        return "redirect:/admin/usuarios";
    }

    // ============= MÉDICOS - LISTAR =============
    @GetMapping("/medicos")
    public String listarMedicos(Model model) {
        model.addAttribute("medicos", medicoService.obtenerTodos());
        model.addAttribute("especialidades", especialidadService.obtenerTodas());
        return "dashboard/admin/medicos/listar";
    }

    // ============= MÉDICOS - CREAR =============
    @GetMapping("/medicos/nuevo")
    public String nuevoMedico(Model model) {
        model.addAttribute("medico", new Medico());
        model.addAttribute("especialidades", especialidadService.obtenerTodas());
        return "dashboard/admin/medicos/formulario";
    }

    @PostMapping("/medicos/guardar")
    public String guardarMedico(@Valid @ModelAttribute Medico medico,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dashboard/admin/medicos/formulario";
        }

        try {
            medicoService.guardar(medico);
            redirectAttributes.addFlashAttribute("successMessage", "Médico creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/admin/medicos";
    }

    // ============= MÉDICOS - EDITAR =============
    @GetMapping("/medicos/editar/{id}")
    public String editarMedico(@PathVariable Integer id, Model model) {
        Medico medico = medicoService.obtenerPorId(id).orElse(null);
        if (medico == null) {
            return "redirect:/admin/medicos";
        }

        model.addAttribute("medico", medico);
        model.addAttribute("especialidades", especialidadService.obtenerTodas());
        return "dashboard/admin/medicos/formulario";
    }

    // ============= MÉDICOS - ACTUALIZAR =============
    @PostMapping("/medicos/actualizar")
    public String actualizarMedico(
            @RequestParam Integer idMedico,
            @RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam String dni,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam(required = false) String direccion,
            @RequestParam Integer especialidad,
            RedirectAttributes redirectAttributes) {
        try {
            Medico medico = medicoService.obtenerPorId(idMedico)
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
            medico.setNombres(nombres);
            medico.setApellidos(apellidos);
            medico.setDni(dni);
            medico.setEmail(email);
            medico.setCorreo(email);
            medico.setTelefono(telefono);
            medico.setDireccion(direccion);
            Especialidad esp = especialidadService.obtenerPorId(especialidad)
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
            medico.setEspecialidad(esp);

            medicoService.guardar(medico);
            redirectAttributes.addFlashAttribute("successMessage", "Médico actualizado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar médico: " + e.getMessage());
        }

        return "redirect:/admin/medicos";
    }

    // ============= MÉDICOS - ELIMINAR =============
    @PostMapping("/medicos/eliminar/{id}")
    public String eliminarMedico(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            medicoService.eliminar(id);
            redirectAttributes.addFlashAttribute("successMessage", "Médico eliminado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar");
        }

        return "redirect:/admin/medicos";
    }

    // ============= PACIENTES - LISTAR =============
    @GetMapping("/pacientes")
    public String listarPacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        return "dashboard/admin/pacientes/listar";
    }

    // ============= PACIENTES - CREAR =============
    @GetMapping("/pacientes/nuevo")
    public String nuevoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "dashboard/admin/pacientes/formulario";
    }

    @PostMapping("/pacientes/guardar")
    public String guardarPaciente(@Valid @ModelAttribute Paciente paciente,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dashboard/admin/pacientes/formulario";
        }

        try {
            pacienteService.guardar(paciente);
            redirectAttributes.addFlashAttribute("successMessage", "Paciente creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/admin/pacientes";
    }

    // ============= PACIENTES - EDITAR =============
    @GetMapping("/pacientes/editar/{id}")
    public String editarPaciente(@PathVariable Integer id, Model model) {
        Paciente paciente = pacienteService.obtenerPorId(id).orElse(null);
        if (paciente == null) {
            return "redirect:/admin/pacientes";
        }

        model.addAttribute("paciente", paciente);
        return "dashboard/admin/pacientes/formulario";
    }

    // ============= PACIENTES - ACTUALIZAR =============
    @PostMapping("/pacientes/actualizar")
    public String actualizarPaciente(
            @RequestParam Integer idPaciente,
            @RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam String dni,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam(required = false) String direccion,
            RedirectAttributes redirectAttributes) {
        try {
            Paciente paciente = pacienteService.obtenerPorId(idPaciente)
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            paciente.setNombres(nombres);
            paciente.setApellidos(apellidos);
            paciente.setDni(dni);
            paciente.setEmail(email);
            paciente.setTelefono(telefono);
            paciente.setDireccion(direccion);

            pacienteService.guardar(paciente);
            redirectAttributes.addFlashAttribute("successMessage", "Paciente actualizado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar paciente: " + e.getMessage());
        }

        return "redirect:/admin/pacientes";
    }

    // ============= PACIENTES - ELIMINAR =============
    @PostMapping("/pacientes/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            
            // Verificar si el paciente existe
            if (!pacienteService.obtenerPorId(id).isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Paciente no encontrado");
                return "redirect:/admin/pacientes";
            }
            
            pacienteService.eliminar(id);
            redirectAttributes.addFlashAttribute("successMessage", "Paciente eliminado exitosamente");
            
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "No se puede eliminar el paciente porque tiene citas asociadas. Cancele primero todas sus citas.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar paciente: " + e.getMessage());
        }

        return "redirect:/admin/pacientes";
    }

    // ============= ESPECIALIDADES - LISTAR =============
    @GetMapping("/especialidades")
    public String listarEspecialidades(Model model) {
        model.addAttribute("especialidades", especialidadService.obtenerTodas());
        return "dashboard/admin/especialidades/listar";
    }

    // ============= ESPECIALIDADES - CREAR =============
    @GetMapping("/especialidades/nuevo")
    public String nuevoEspecialidad(Model model) {
        model.addAttribute("especialidad", new Especialidad());
        return "dashboard/admin/especialidades/formulario";
    }

    @PostMapping("/especialidades/guardar")
    public String guardarEspecialidad(@Valid @ModelAttribute Especialidad especialidad,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dashboard/admin/especialidades/formulario";
        }

        try {
            especialidadService.guardar(especialidad);
            redirectAttributes.addFlashAttribute("successMessage", "Especialidad creada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/admin/especialidades";
    }

    // ============= ESPECIALIDADES - EDITAR =============
    @GetMapping("/especialidades/editar/{id}")
    public String editarEspecialidad(@PathVariable Integer id, Model model) {
        Especialidad especialidad = especialidadService.obtenerPorId(id).orElse(null);
        if (especialidad == null) {
            return "redirect:/admin/especialidades";
        }

        model.addAttribute("especialidad", especialidad);
        return "dashboard/admin/especialidades/formulario";
    }

    // ============= ESPECIALIDADES - ACTUALIZAR =============
    @PostMapping("/especialidades/actualizar")
    public String actualizarEspecialidad(
            @RequestParam Integer idEspecialidad,
            @RequestParam String nombre,
            @RequestParam(required = false) String descripcion,
            @RequestParam String activo,
            RedirectAttributes redirectAttributes) {
        try {
            
            Especialidad especialidad = especialidadService.obtenerPorId(idEspecialidad)
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
            
            especialidad.setNombre(nombre);
            especialidad.setDescripcion(descripcion);
            
            // Convertir string a integer correctamente
            if ("true".equalsIgnoreCase(activo) || "1".equals(activo)) {
                especialidad.setActivo(1);
            } else {
                especialidad.setActivo(0);
            }

            especialidadService.guardar(especialidad);
            redirectAttributes.addFlashAttribute("successMessage", "Especialidad actualizada exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar especialidad: " + e.getMessage());
        }

        return "redirect:/admin/especialidades";
    }

    // ============= ESPECIALIDADES - ELIMINAR =============
    @PostMapping("/especialidades/eliminar/{id}")
    public String eliminarEspecialidad(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            especialidadService.eliminar(id);
            redirectAttributes.addFlashAttribute("successMessage", "Especialidad eliminada");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar");
        }

        return "redirect:/admin/especialidades";
    }

    // ============= CITAS - LISTAR =============
    @GetMapping("/citas")
    public String listarCitas(Model model) {
        model.addAttribute("citas", citaService.obtenerTodas());
        model.addAttribute("medicos", medicoService.obtenerTodos());
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        return "dashboard/admin/citas/listar";
    }

    // ============= CITAS - CREAR =============
    @GetMapping("/citas/nuevo")
    public String nuevaCita(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("medicos", medicoService.obtenerTodos());
        model.addAttribute("pacientes", pacienteService.obtenerTodos());
        return "dashboard/admin/citas/formulario";
    }

    @PostMapping("/citas/guardar")
    public String guardarCita(@Valid @ModelAttribute Cita cita,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dashboard/admin/citas/formulario";
        }

        try {
            citaService.guardar(cita);
            redirectAttributes.addFlashAttribute("successMessage", "Cita creada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/admin/citas";
    }

    // ============= CITAS - ACTUALIZAR =============
    @PostMapping("/citas/actualizar")
    public String actualizarCita(
            @RequestParam Integer idCita,
            @RequestParam Integer paciente,
            @RequestParam Integer medico,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @RequestParam String hora,
            @RequestParam String estado,
            @RequestParam(required = false) String motivo,
            RedirectAttributes redirectAttributes) {
        try {
            Cita cita = citaService.obtenerPorId(idCita)
                    .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
            Paciente pac = pacienteService.obtenerPorId(paciente)
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            Medico med = medicoService.obtenerPorId(medico)
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

            cita.setPaciente(pac);
            cita.setMedico(med);
            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setEstado(estado);
            cita.setMotivo(motivo);

            citaService.guardar(cita);
            redirectAttributes.addFlashAttribute("successMessage", "Cita actualizada exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar cita: " + e.getMessage());
        }

        return "redirect:/admin/citas";
    }

    // ============= CITAS - ELIMINAR =============
    @PostMapping("/citas/eliminar/{id}")
    public String eliminarCita(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            citaService.eliminar(id);
            redirectAttributes.addFlashAttribute("successMessage", "Cita eliminada");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar");
        }

        return "redirect:/admin/citas";
    }

    // ============= MÉDICOS - CAMBIAR ESTADO =============
    @PostMapping("/medicos/cambiar-estado")
    @ResponseBody
    public Map<String, Object> cambiarEstadoMedico(
            @RequestParam Integer idMedico,
            @RequestParam Integer nuevoEstado,
            jakarta.servlet.http.HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = (String) session.getAttribute("username");
            String role = (String) session.getAttribute("role");
            
            if (username == null || !role.equals("ADMIN")) {
                response.put("success", false);
                response.put("message", "No autorizado");
                return response;
            }

            
            Medico medico = medicoService.obtenerPorId(idMedico)
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
            
            medico.setActivo(nuevoEstado);
            medicoService.guardar(medico);
            
            String estadoTexto = nuevoEstado == 1 ? "activo" : "inactivo";
            
            response.put("success", true);
            response.put("message", "Estado del médico cambiado a " + estadoTexto + " correctamente");
            response.put("nuevoEstado", nuevoEstado);
            response.put("nombreMedico", medico.getNombres() + " " + medico.getApellidos());
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error al cambiar estado: " + e.getMessage());
        }
        
        return response;
    }
}
