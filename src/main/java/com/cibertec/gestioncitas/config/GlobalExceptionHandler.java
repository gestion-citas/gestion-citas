package com.cibertec.gestioncitas.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.cibertec.gestioncitas.exception.CitaNotFoundException;
import com.cibertec.gestioncitas.exception.HorarioNoDisponibleException;
import com.cibertec.gestioncitas.exception.IllegalOperationException;
import com.cibertec.gestioncitas.exception.UsuarioExistenteException;
import com.cibertec.gestioncitas.exception.InvalidCredentialsException;
import com.cibertec.gestioncitas.exception.MedicoNotFoundException;
import com.cibertec.gestioncitas.exception.PacienteNotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CitaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCitaNotFoundException(CitaNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Cita no encontrada");
        response.put("mensaje", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HorarioNoDisponibleException.class)
    public ResponseEntity<Map<String, Object>> handleHorarioNoDisponibleException(HorarioNoDisponibleException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Horario no disponible");
        response.put("mensaje", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalOperationException(IllegalOperationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Operación no permitida");
        response.put("mensaje", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioExistenteException(UsuarioExistenteException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Usuario ya existe");
        response.put("mensaje", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Credenciales inválidas");
        response.put("mensaje", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MedicoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMedicoNotFoundException(MedicoNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Médico no encontrado");
        response.put("mensaje", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PacienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePacienteNotFoundException(PacienteNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Paciente no encontrado");
        response.put("mensaje", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Argumento inválido");
        response.put("mensaje", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Error interno del servidor");
        response.put("mensaje", "Ha ocurrido un error inesperado. Por favor, inténtelo más tarde.");
        response.put("timestamp", System.currentTimeMillis());
        response.put("exception", ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
