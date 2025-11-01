package com.cibertec.gestioncitas.exception;

/**
 * Excepción personalizada para operaciones ilegales
 */
public class IllegalOperationException extends RuntimeException {

    public IllegalOperationException(String message) {
        super(message);
    }

    public IllegalOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
