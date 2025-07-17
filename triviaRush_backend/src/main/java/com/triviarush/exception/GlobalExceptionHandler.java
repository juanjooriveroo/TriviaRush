package com.triviarush.exception;

import org.springframework.validation.FieldError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CorrectAnswerNotOptionException.class)
    @ApiResponse(
            responseCode = "422",
            description = "La respuesta correcta debe estar en las opciones.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> correctAnswerNotOptionException(CorrectAnswerNotOptionException ex) {
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(PasswordNotCorrectException.class)
    @ApiResponse(
            responseCode = "401",
            description = "La contraseña introducida no es correcta.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> passwordNotCorrectException(PasswordNotCorrectException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    @ApiResponse(
            responseCode = "404",
            description = "El jugador buscado no se encuentra en el sistema.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> playerNotFoundException(PlayerNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ApiResponse(
            responseCode = "400",
            description = "Parámetro no válido en la ruta o en la query",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Parámetro inválido: " + ex.getName());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ApiResponse(
            responseCode = "409",
            description = "Violación de integridad (duplicado en campos únicos)",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Usuario o email ya existentes");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(
            responseCode = "400",
            description = "Error de validación en los campos del DTO",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errores.toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ApiResponse(
            responseCode = "400",
            description = "Error de validación en parámetros de entrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String campo = violation.getPropertyPath().toString();
            String mensaje = violation.getMessage();
            errores.put(campo, mensaje);
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errores.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ApiResponse(
            responseCode = "400",
            description = "Valor inválido para un campo enumerado o similar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Valor inválido: " + ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidFormat(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String targetType = ife.getTargetType().getSimpleName();
            String invalidValue = ife.getValue().toString();
            String allowedValues = "";
            if (ife.getTargetType().isEnum()) {
                Object[] enumConstants = ife.getTargetType().getEnumConstants();
                allowedValues = java.util.Arrays.stream(enumConstants)
                        .map(Object::toString)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
            }
            String message = String.format("Valor inválido para %s: '%s'. Valores permitidos: [%s]", targetType, invalidValue, allowedValues);
            return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "JSON inválido o mal formado. El rol debe de ser un array");
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class))
    )
    public ResponseEntity<ApiErrorResponse> handleAllOtherExceptions(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno no controlado: " + ex.getMessage());
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ApiErrorResponse error = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return new ResponseEntity<>(error, status);
    }
}