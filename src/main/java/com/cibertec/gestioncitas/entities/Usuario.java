package com.cibertec.gestioncitas.entities;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "role", length = 50)
    private String role;

    @Column(name = "activo")
    private Integer activo;

    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;

    public Usuario() {}

    public Usuario(String nombres, String apellidos, String email, String username, 
                   String password, String role, Integer activo, Timestamp fechaCreacion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
