package com.triviarush.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modo de juego disponible en la plataforma.")
public enum Mode {

    @Schema(description = "Modo diario con preguntas limitadas por día.")
    DAILY,

    @Schema(description = "Modo normal con una cantidad fija de preguntas.")
    NORMAL,

    @Schema(description = "Modo infinito con preguntas continuas.")
    INFINITE
}