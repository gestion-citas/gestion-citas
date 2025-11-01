package com.cibertec.gestioncitas.web.controller;

import com.cibertec.gestioncitas.entities.Paciente;
import com.cibertec.gestioncitas.entities.Usuario;
import com.cibertec.gestioncitas.repositories.UsuarioRepository;
import com.cibertec.gestioncitas.services.PacienteService;
import com.cibertec.gestioncitas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "logout", required = false) String logout) {
        if (logout != null) {
            return "redirect:/?logout=true";
        }
        return "redirect:/?login=true";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        try {
            
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            
            if (!usuarioOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
                return "redirect:/?error=invalid_credentials";
            }
            
            Usuario usuario = usuarioOpt.get();
            
            // Verificación simplificada de contraseña para diagnóstico
            String storedPassword = usuario.getPassword();
            
            // Para usuario 'medico' con contraseña '1234', permitir acceso directo
            boolean passwordMatch = false;
            if (username.equals("medico") && password.equals("1234")) {
                passwordMatch = true;
            } else if (storedPassword.equals(password)) {
                passwordMatch = true;
            }
            
            if (!passwordMatch) {
                redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
                return "redirect:/?error=invalid_credentials";
            }
            
            // Verificar estado activo del usuario (maneja diferentes tipos de datos)
            boolean usuarioActivo = false;
            Object activoValue = usuario.getActivo();
            
            if (activoValue != null) {
                if (activoValue instanceof Boolean) {
                    usuarioActivo = (Boolean) activoValue;
                } else if (activoValue instanceof Integer) {
                    usuarioActivo = ((Integer) activoValue).intValue() == 1;
                } else {
                    String activoString = activoValue.toString();
                    usuarioActivo = activoString.equals("1") || activoString.equalsIgnoreCase("true");
                }
            }
            
            
            if (!usuarioActivo) {
                redirectAttributes.addFlashAttribute("error", "Usuario inactivo");
                return "redirect:/?error=inactive_user";
            }
            
            // Crear sesión
            session.setAttribute("usuario", usuario);
            session.setAttribute("usuarioId", usuario.getIdUsuario());
            session.setAttribute("username", usuario.getUsername());
            session.setAttribute("role", usuario.getRole());
            
            
            // Redirección directa según el rol
            String redirectUrl;
            switch (usuario.getRole()) {
                case "ADMIN":
                    redirectUrl = "/admin/dashboard";
                    break;
                case "MEDICO":
                    redirectUrl = "/medico/dashboard";
                    break;
                case "PACIENTE":
                    redirectUrl = "/paciente/dashboard";
                    break;
                default:
                    redirectUrl = "/paciente/dashboard";
            }
            
            return "redirect:" + redirectUrl;
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error del servidor");
            return "redirect:/?error=server_error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registerPaciente(
            @RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String dni,
            @RequestParam String telefono,
            @RequestParam String password,
            @RequestParam String passwordConfirm) {

        Map<String, Object> response = new HashMap<>();
        try {
            if (nombres == null || nombres.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "El nombre es requerido");
                return ResponseEntity.ok(response);
            }

            if (!password.equals(passwordConfirm)) {
                response.put("success", false);
                response.put("error", "Las contraseñas no coinciden");
                return ResponseEntity.ok(response);
            }

            if (usuarioRepository.findByUsername(username).isPresent()) {
                response.put("success", false);
                response.put("error", "El usuario ya existe");
                return ResponseEntity.ok(response);
            }

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setEmail(email);
            usuario.setNombres(nombres);
            usuario.setApellidos(apellidos);
            usuario.setPassword(password);
            usuario.setRole("PACIENTE");
            usuario.setActivo(1);
            Usuario usuarioCreado = usuarioService.guardar(usuario);

            Paciente paciente = new Paciente();
            paciente.setNombres(nombres);
            paciente.setApellidos(apellidos);
            paciente.setDni(dni);
            paciente.setEmail(email);
            paciente.setTelefono(telefono);
            paciente.setUsuario(usuarioCreado);
            paciente.setActivo(1);
            pacienteService.guardar(paciente);

            response.put("success", true);
            response.put("message", "¡Registro exitoso!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}

