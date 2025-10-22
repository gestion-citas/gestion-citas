package com.cibertec.gestioncitas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;  // Cambió de "username" a "nombre_usuario"
    
    @Column(name = "clave", nullable = false, length = 255)
    private String clave;  // Cambió de "password" a "clave"
    
    @Column(name = "rol", nullable = false, length = 20)
    private String rol;
    
    // Constantes para roles
    public static final String ROL_ADMIN = "ADMIN";
    public static final String ROL_RECEPCIONISTA = "RECEPCION";
    public static final String ROL_MEDICO = "MEDICO";
    
    // Constructor vacío
    public Usuario() {}
    
    // Constructor con parámetros
    public Usuario(String nombreUsuario, String clave, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.rol = rol;
    }
    
    // Getters y Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getClave() {
        return clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
}
