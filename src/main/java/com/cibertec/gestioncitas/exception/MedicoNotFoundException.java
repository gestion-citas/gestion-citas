package com.cibertec.gestioncitas.exception;

/**
 * Excepción personalizada cuando no se encuentra un médico
 */
public class MedicoNotFoundException extends RuntimeException {

    public MedicoNotFoundException(String message) {
        super(message);
    }

    public MedicoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
