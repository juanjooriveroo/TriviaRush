package com.triviarush.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Schema(description = "Request de login en el que introduces la credencial y la contraseña.")
public record LoginRequestDto (
        @Schema(
                description = "Credencial del jugador (puede ser el email o el username).",
                example = "ejemplo@example.com",
                format = "String",
                type = "String",
                nullable = false
        )
        @NotBlank(message = "Necesitas introducir o el correo o el username") String credential,

        @Schema(
                description = "Contraseña del usuario. Debe tener 8 caracteres de mínimo",
                example = "Password1234",
                format = "String",
                type = "String",
                nullable = false
        )
        @NotBlank(message = "Necesitas introducir la contraseña") @Size(min = 8, message = "La contraseña tiene minimo 8 caracteres") String password
){}