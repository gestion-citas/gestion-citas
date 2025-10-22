package com.cibertec.gestioncitas.service;

import com.cibertec.gestioncitas.entity.Medico;
import com.cibertec.gestioncitas.repository.MedicoRepository;
import com.cibertec.gestioncitas.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones de negocio relacionadas con Medicos
 * Maneja CRUD completo y validaciones de negocio
 */
@Service
@Transactional  // Todas las operaciones de escritura son transaccionales
public class MedicoService {
    
    // Inyeccion de dependencias por constructor (recomendado por Spring)
    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;
    
    public MedicoService(MedicoRepository medicoRepository, 
                        EspecialidadRepository especialidadRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
    }
    
    /**
     * Lista todos los medicos con su especialidad cargada (EAGER)
     * @return Lista de medicos ordenados por apellido y nombre
     */
    @Transactional(readOnly = true)  // Optimizacion para solo lectura
    public List<Medico> listarTodos() {
        return medicoRepository.findAllWithEspecialidad();
    }
    
    /**
     * Obtiene un medico por su ID
     * @param id Identificador del medico
     * @return Optional con el medico si existe, vacio si no
     */
    @Transactional(readOnly = true)
    public Optional<Medico> obtenerPorId(Integer id) {
        return medicoRepository.findById(id);
    }
    
    /**
     * Busca un medico por su DNI
     * @param dni Documento de identidad del medico
     * @return Optional con el medico si existe
     */
    @Transactional(readOnly = true)
    public Optional<Medico> buscarPorDni(String dni) {
        return medicoRepository.findByDni(dni);
    }
    
    /**
     * Lista medicos filtrados por especialidad
     * @param idEspecialidad ID de la especialidad a filtrar
     * @return Lista de medicos de esa especialidad
     */
    @Transactional(readOnly = true)
    public List<Medico> listarPorEspecialidad(Integer idEspecialidad) {
        return medicoRepository.findByEspecialidadIdEspecialidad(idEspecialidad);
    }
    
    /**
     * Crea un nuevo medico en el sistema
     * Valida que no exista DNI duplicado
     * @param medico Objeto medico a crear
     * @return Medico creado con ID generado
     * @throws IllegalArgumentException si ya existe el DNI o faltan datos
     */
    public Medico crear(Medico medico) {
        // Validar DNI unico
        if (medicoRepository.existsByDni(medico.getDni())) {
            throw new IllegalArgumentException("Ya existe un medico con ese DNI");
        }
        // Validar datos obligatorios
        validarMedico(medico);
        // Guardar en base de datos
        return medicoRepository.save(medico);
    }
    
    /**
     * Actualiza un medico existente
     * @param id ID del medico a actualizar
     * @param medico Datos nuevos del medico
     * @return Medico actualizado
     * @throws IllegalArgumentException si no existe o DNI duplicado
     */
    public Medico actualizar(Integer id, Medico medico) {
        // Buscar medico existente
        Medico existente = medicoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Medico no encontrado"));
        
        // Validar DNI unico (solo si cambio)
        if (!existente.getDni().equals(medico.getDni()) && 
            medicoRepository.existsByDni(medico.getDni())) {
            throw new IllegalArgumentException("Ya existe un medico con ese DNI");
        }
        
        // Validar datos obligatorios
        validarMedico(medico);
        
        // Actualizar campos
        existente.setDni(medico.getDni());
        existente.setNombres(medico.getNombres());
        existente.setApellidos(medico.getApellidos());
        existente.setEspecialidad(medico.getEspecialidad());
        existente.setCorreo(medico.getCorreo());
        existente.setTelefono(medico.getTelefono());
        
        return medicoRepository.save(existente);
    }
    
    /**
     * Elimina un medico del sistema
     * @param id ID del medico a eliminar
     * @throws IllegalArgumentException si el medico no existe
     */
    public void eliminar(Integer id) {
        if (!medicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Medico no encontrado");
        }
        medicoRepository.deleteById(id);
    }
    
    /**
     * Valida que un medico tenga todos los datos obligatorios
     * @param medico Medico a validar
     * @throws IllegalArgumentException si falta algun dato obligatorio
     */
    private void validarMedico(Medico medico) {
        if (medico.getDni() == null || medico.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        if (medico.getNombres() == null || medico.getNombres().trim().isEmpty()) {
            throw new IllegalArgumentException("Los nombres son obligatorios");
        }
        if (medico.getApellidos() == null || medico.getApellidos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }
        if (medico.getEspecialidad() == null) {
            throw new IllegalArgumentException("La especialidad es obligatoria");
        }
    }
}

