package com.cibertec.gestioncitas.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
public class Cita {
    
	public static final String ESTADO_PROGRAMADA = "PROGRAMADA";
	public static final String ESTADO_COMPLETADA = "COMPLETADA";
	public static final String ESTADO_ATENDIDA = "ATENDIDA";  // ← NUEVO
	public static final String ESTADO_CANCELADA = "CANCELADA";

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "hora", nullable = false)
    private LocalTime hora;
    
    @Column(name = "estado", nullable = false, length = 20)
    private String estado = ESTADO_PROGRAMADA;
    
    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;
    
    public Cita() {
    }
    
    public boolean estaProgramada() {
        return ESTADO_PROGRAMADA.equals(estado);
    }
    
    public boolean esHoy() {
        return fecha != null && fecha.equals(LocalDate.now());
    }
    
    public void validar() {
        if (paciente == null) {
            throw new IllegalArgumentException("Debe seleccionar un paciente valido");
        }
        if (medico == null) {
            throw new IllegalArgumentException("Debe seleccionar un medico valido");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (hora == null) {
            throw new IllegalArgumentException("La hora es obligatoria");
        }
    }
    
    public Integer getIdCita() {
        return idCita;
    }
    
    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public Medico getMedico() {
        return medico;
    }
    
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public LocalTime getHora() {
        return hora;
    }
    
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
