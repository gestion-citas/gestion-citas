package com.cibertec.gestioncitas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medico")
public class Medico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Integer idMedico;
    
    @Column(name = "dni", nullable = false, unique = true, length = 12)
    private String dni;
    
    @Column(name = "nombres", nullable = false, length = 80)
    private String nombres;
    
    @Column(name = "apellidos", nullable = false, length = 80)
    private String apellidos;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Especialidad especialidad;
    
    @Column(name = "correo", length = 120)
    private String correo;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    public Medico() {
    }
    
    public Integer getIdMedico() {
        return idMedico;
    }
    
    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
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
    
    public Especialidad getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
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
}
