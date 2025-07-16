package com.triviarush.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estado de la partida. Puede ser un estado en curso o finalizado.")
public enum Status {

    @Schema(description = "La partida está en curso")
    IN_PROGRESS,

    @Schema(description = "La partida ha finalizado")
    FINISHED
}