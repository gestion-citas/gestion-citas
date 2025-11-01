package com.cibertec.gestioncitas.exception;

/**
 * Excepción personalizada cuando no se encuentra una cita
 */
public class CitaNotFoundException extends RuntimeException {

    public CitaNotFoundException(String message) {
        super(message);
    }

    public CitaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
