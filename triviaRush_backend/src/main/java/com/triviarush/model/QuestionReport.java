package com.triviarush.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_reports")
@Schema(description = "Representa un reporte de una pregunta por parte de un jugador.")
public class QuestionReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(
            description = "Identificador único del reporte.",
            example = "550e8400-e29b-41d4-a716-446655440000",
            format = "UUID",
            type = "UUID",
            nullable = false
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    @Schema(
            description = "Pregunta que ha sido reportada.",
            format = "Question",
            type = "Question",
            nullable = false
    )
    private Question question;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reported_by_player_id", nullable = false)
    @Schema(
            description = "Jugador que realizó el reporte.",
            format = "Player",
            type = "Player",
            nullable = false
    )
    private Player reportedBy;

    @Column(nullable = false)
    @Schema(
            description = "Fecha y hora en que se realizó el reporte.",
            example = "2023-01-01T12:00:00",
            format = "LocalDateTime",
            type = "LocalDateTime",
            nullable = false
    )
    private LocalDateTime reportedAt;

    @Column(nullable = false)
    @Schema(
            description = "Indica si el reporte ha sido revisado por los administradores.",
            example = "false",
            format = "Boolean",
            type = "Boolean",
            nullable = false
    )
    private Boolean reviewed;
}