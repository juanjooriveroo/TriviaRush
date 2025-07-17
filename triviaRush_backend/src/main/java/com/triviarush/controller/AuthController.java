package com.triviarush.controller;

import com.triviarush.dto.LoginRequestDto;
import com.triviarush.dto.LoginResponseDto;
import com.triviarush.dto.RegisterRequestDto;
import com.triviarush.dto.RegisterResponseDto;
import com.triviarush.exception.ApiErrorResponse;
import com.triviarush.security.JwtUtils;
import com.triviarush.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autentificación",
        description = "Endpoints para la autentificacion de usuarios"
)
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @Operation(
            summary = "Registro de un nuevo jugador",
            description = "Crea un nuevo jugador en el sistema si los datos son válidos.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Registro exitoso",
                            content = @Content(schema = @Schema(implementation = RegisterResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida o parámetros incorrectos",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflicto de datos (duplicados, estado inválido, etc.)",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto request){
        RegisterResponseDto response = authService.register(request);
        return ResponseEntity.created(URI.create(
                "/player/" + jwtUtils.getUserFromToken(response.token()).getId()))
                .body(response);
    }

    @Operation(
            summary = "Logueo de un jugador",
            description = "Loguea un jugador en el sistema si los datos son válidos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Logueo exitoso",
                            content = @Content(schema = @Schema(implementation = LoginResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida o parámetros incorrectos",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No autenticado o credenciales inválidas",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso no encontrado",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    )
            }
    )
    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request){
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
