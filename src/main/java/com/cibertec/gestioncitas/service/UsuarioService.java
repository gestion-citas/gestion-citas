package com.cibertec.gestioncitas.service;

import com.cibertec.gestioncitas.entity.Usuario;
import com.cibertec.gestioncitas.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // Listar todos los usuarios
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    // Obtener usuario por ID
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }
    
    // Buscar usuario por nombre_usuario
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    
    // Crear nuevo usuario
    public Usuario crear(Usuario usuario) {
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        
        // Verificar si ya existe un usuario con ese nombre
        if (usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre");
        }
        
        // Encriptar la contraseña
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        
        return usuarioRepository.save(usuario);
    }
    
    // Actualizar usuario existente
    public Usuario actualizar(Integer id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuarioExistente.setRol(usuarioActualizado.getRol());
        
        // Si viene una nueva contraseña, encriptarla
        if (usuarioActualizado.getClave() != null && !usuarioActualizado.getClave().isEmpty()) {
            usuarioExistente.setClave(passwordEncoder.encode(usuarioActualizado.getClave()));
        }
        
        return usuarioRepository.save(usuarioExistente);
    }
    
    // Cambiar contraseña
    public void cambiarClave(Integer id, String nuevaClave) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        usuario.setClave(passwordEncoder.encode(nuevaClave));
        usuarioRepository.save(usuario);
    }
    
    // Eliminar usuario
    public void eliminar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}

