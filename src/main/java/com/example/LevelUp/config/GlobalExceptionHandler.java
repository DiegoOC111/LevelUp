package com.example.LevelUp.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. ATRAPAR ERRORES DE BASE DE DATOS (Tu problema actual)
    // Esto pasa cuando intentas borrar algo que tiene hijos (FK constraint)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDbConstraint(DataIntegrityViolationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "No se puede eliminar este registro.");
        error.put("detalle", "Este elemento está siendo usado en otras partes del sistema (ej: Boletas) y no puede borrarse.");

        // Devolvemos 409 CONFLICT en lugar de 500
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 2. ATRAPAR CUALQUIER OTRO ERROR NO ESPERADO
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un error interno en el servidor.");
        error.put("detalle", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}