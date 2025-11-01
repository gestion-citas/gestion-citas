package com.cibertec.gestioncitas.exception;

/**
 * Excepción personalizada cuando no se encuentra un paciente
 */
public class PacienteNotFoundException extends RuntimeException {

    public PacienteNotFoundException(String message) {
        super(message);
    }

    public PacienteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
