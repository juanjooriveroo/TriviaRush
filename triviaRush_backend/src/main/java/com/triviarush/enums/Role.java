package com.triviarush.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rol asignado al usuario dentro de la plataforma.")
public enum Role {

    @Schema(description = "Usuario con permisos de administración.")
    ADMIN,

    @Schema(description = "Usuario jugador estándar.")
    PLAYER
}