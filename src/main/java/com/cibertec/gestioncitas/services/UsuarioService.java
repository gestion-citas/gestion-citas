package com.cibertec.gestioncitas.services;

import com.cibertec.gestioncitas.entities.Usuario;
import com.cibertec.gestioncitas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public long contarTotal() {
        return usuarioRepository.count();
    }
    public boolean existeUsername(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

    public boolean existeEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

}
