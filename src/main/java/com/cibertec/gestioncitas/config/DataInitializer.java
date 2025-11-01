package com.cibertec.gestioncitas.config;

import com.cibertec.gestioncitas.entities.*;
import com.cibertec.gestioncitas.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    public void run(String... args) throws Exception {
        long totalUsuarios = usuarioRepository.count();
        long totalMedicos = medicoRepository.count();
        long totalEspecialidades = especialidadRepository.count();

        if (totalUsuarios == 0 && totalEspecialidades == 0 && totalMedicos == 0) {

            Especialidad cardiologia = new Especialidad();
            cardiologia.setNombre("Cardiología");
            cardiologia.setDescripcion("Especialidad del corazón y sistema cardiovascular");
            cardiologia.setActivo(1);
            especialidadRepository.save(cardiologia);

            Especialidad neurologia = new Especialidad();
            neurologia.setNombre("Neurología");
            neurologia.setDescripcion("Especialidad del sistema nervioso");
            neurologia.setActivo(1);
            especialidadRepository.save(neurologia);

            Especialidad dermatologia = new Especialidad();
            dermatologia.setNombre("Dermatología");
            dermatologia.setDescripcion("Especialidad de la piel y sus enfermedades");
            dermatologia.setActivo(1);
            especialidadRepository.save(dermatologia);

            // ========== USUARIO ADMIN ==========
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("1234");
            admin.setEmail("admin@hospital.com");
            admin.setNombres("Administrador");
            admin.setApellidos("Sistema");
            admin.setRole("ADMIN");
            admin.setActivo(1);
            usuarioRepository.save(admin);

            // ========== USUARIOS MÉDICOS ==========
            Usuario usuarioMedico1 = new Usuario();
            usuarioMedico1.setUsername("medico");
            usuarioMedico1.setPassword("1234");
            usuarioMedico1.setEmail("medico@hospital.com");
            usuarioMedico1.setNombres("Dr. Juan");
            usuarioMedico1.setApellidos("García López");
            usuarioMedico1.setRole("MEDICO");
            usuarioMedico1.setActivo(1);
            usuarioRepository.save(usuarioMedico1);

            Medico medico1 = new Medico();
            medico1.setNombres("Juan");
            medico1.setApellidos("García López");
            medico1.setDni("12345678");
            medico1.setEmail("medico@hospital.com");
            medico1.setTelefono("+51987654321");
            medico1.setEspecialidad(cardiologia);
            medico1.setUsuario(usuarioMedico1);
            medico1.setActivo(1);
            medicoRepository.save(medico1);

        } else {
        }

    }
}
