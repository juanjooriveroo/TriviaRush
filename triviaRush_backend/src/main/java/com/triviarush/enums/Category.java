package com.triviarush.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categorías disponibles para las preguntas.")
public enum Category {

    @Schema(description = "Todas las categorías.")
    TODOS,

    @Schema(description = "Categoría de Historia.")
    HISTORIA,

    @Schema(description = "Categoría de Ciencia.")
    CIENCIA,

    @Schema(description = "Categoría de Geografía.")
    GEOGRAFIA,

    @Schema(description = "Categoría de Música.")
    MUSICA,

    @Schema(description = "Categoría de Literatura.")
    LITERATURA,

    @Schema(description = "Categoría de Biología.")
    BIOLOGIA,

    @Schema(description = "Categoría de Deportes.")
    DEPORTES,

    @Schema(description = "Categoría de Arte.")
    ARTE,

    @Schema(description = "Categoría de Cine.")
    CINE,

    @Schema(description = "Categoría de Videojuegos.")
    VIDEOJUEGOS
}