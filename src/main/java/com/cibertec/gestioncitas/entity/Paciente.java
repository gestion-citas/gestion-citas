package com.cibertec.gestioncitas.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Integer idPaciente;
    
    @Column(name = "dni", nullable = false, unique = true, length = 12)
    private String dni;
    
    @Column(name = "nombres", nullable = false, length = 80)
    private String nombres;
    
    @Column(name = "apellidos", nullable = false, length = 80)
    private String apellidos;
    
    @Column(name = "correo", length = 120)
    private String correo;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
    
    public Paciente() {
    }
    
    public Integer getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
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
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
