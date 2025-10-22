package com.cibertec.gestioncitas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "especialidad")
public class Especialidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Integer idEspecialidad;
    
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    
    public Especialidad() {
    }
    
    public Especialidad(Integer idEspecialidad, String nombre) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
    }
    
    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }
    
    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
