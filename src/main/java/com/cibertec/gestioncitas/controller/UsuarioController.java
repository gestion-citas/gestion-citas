package com.cibertec.gestioncitas.controller;

import com.cibertec.gestioncitas.entity.Usuario;
import com.cibertec.gestioncitas.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    // Listar todos los usuarios
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }
    
    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Buscar usuario por nombre_usuario
    @GetMapping("/nombre/{nombreUsuario}")
    public ResponseEntity<Usuario> buscarPorNombreUsuario(@PathVariable String nombreUsuario) {
        return usuarioService.buscarPorNombreUsuario(nombreUsuario)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.crear(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Integer id, 
            @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.actualizar(id, usuario);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Cambiar contraseña
    @PatchMapping("/{id}/clave")
    public ResponseEntity<Void> cambiarClave(
            @PathVariable Integer id,
            @RequestBody String nuevaClave) {
        try {
            usuarioService.cambiarClave(id, nuevaClave);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
