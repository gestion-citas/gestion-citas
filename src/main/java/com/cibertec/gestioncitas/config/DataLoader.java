package com.cibertec.gestioncitas.config;

import com.cibertec.gestioncitas.entity.*;
import com.cibertec.gestioncitas.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
public class DataLoader {
    
    @Bean
    CommandLineRunner initDatabase(
            EspecialidadRepository especialidadRepo,
            MedicoRepository medicoRepo,
            PacienteRepository pacienteRepo,
            CitaRepository citaRepo,
            UsuarioRepository usuarioRepo,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            // Solo cargar datos si la BD esta vacia
            if (especialidadRepo.count() > 0) {
                return;
            }
            
            // 1. Crear Especialidades
            Especialidad cardio = new Especialidad(null, "Cardiologia");
            Especialidad dermato = new Especialidad(null, "Dermatologia");
            Especialidad pediatria = new Especialidad(null, "Pediatria");
            
            especialidadRepo.save(cardio);
            especialidadRepo.save(dermato);
            especialidadRepo.save(pediatria);
            
            // 2. Crear Medicos
            Medico medico1 = new Medico();
            medico1.setDni("12345678");
            medico1.setNombres("Juan");
            medico1.setApellidos("Perez Garcia");
            medico1.setEspecialidad(cardio);
            medico1.setCorreo("jperez@hospital.com");
            medico1.setTelefono("987654321");
            medicoRepo.save(medico1);
            
            Medico medico2 = new Medico();
            medico2.setDni("87654321");
            medico2.setNombres("Maria");
            medico2.setApellidos("Lopez Sanchez");
            medico2.setEspecialidad(dermato);
            medico2.setCorreo("mlopez@hospital.com");
            medico2.setTelefono("987654322");
            medicoRepo.save(medico2);
            
            // 3. Crear Pacientes
            Paciente paciente1 = new Paciente();
            paciente1.setDni("11111111");
            paciente1.setNombres("Carlos");
            paciente1.setApellidos("Rodriguez");
            paciente1.setCorreo("carlos@email.com");
            paciente1.setTelefono("999888777");
            pacienteRepo.save(paciente1);
            
            Paciente paciente2 = new Paciente();
            paciente2.setDni("22222222");
            paciente2.setNombres("Ana");
            paciente2.setApellidos("Martinez");
            paciente2.setCorreo("ana@email.com");
            paciente2.setTelefono("999888778");
            pacienteRepo.save(paciente2);
            
            // 4. Crear Citas
            Cita cita1 = new Cita();
            cita1.setPaciente(paciente1);
            cita1.setMedico(medico1);
            cita1.setFecha(LocalDate.now());
            cita1.setHora(LocalTime.of(10, 0));
            cita1.setEstado(Cita.ESTADO_PROGRAMADA);
            cita1.setMotivo("Control cardiologico");
            citaRepo.save(cita1);
            
            Cita cita2 = new Cita();
            cita2.setPaciente(paciente2);
            cita2.setMedico(medico2);
            cita2.setFecha(LocalDate.now().plusDays(1));
            cita2.setHora(LocalTime.of(14, 30));
            cita2.setEstado(Cita.ESTADO_PROGRAMADA);
            cita2.setMotivo("Consulta dermatologica");
            citaRepo.save(cita2);
            
            // 5. Crear Usuarios (YA NO TIENE ACTIVO NI ID_MEDICO)
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setClave(passwordEncoder.encode("admin123"));
            admin.setRol(Usuario.ROL_ADMIN);
            usuarioRepo.save(admin);
            
            Usuario recep = new Usuario();
            recep.setNombreUsuario("recepcion");
            recep.setClave(passwordEncoder.encode("recep123"));
            recep.setRol(Usuario.ROL_RECEPCIONISTA);
            usuarioRepo.save(recep);
            
            Usuario userMedico = new Usuario();
            userMedico.setNombreUsuario("doctor");
            userMedico.setClave(passwordEncoder.encode("doc123"));
            userMedico.setRol(Usuario.ROL_MEDICO);
            usuarioRepo.save(userMedico);
            
            System.out.println("=== DATOS INICIALES CARGADOS ===");
            System.out.println("Usuarios creados:");
            System.out.println("- admin/admin123 (Administrador)");
            System.out.println("- recepcion/recep123 (Recepcionista)");
            System.out.println("- doctor/doc123 (Medico)");
        };
    }
}
