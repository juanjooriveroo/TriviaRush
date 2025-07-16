package com.triviarush.model;

import com.triviarush.enums.Category;
import com.triviarush.enums.Mode;
import com.triviarush.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_sessions")
@Schema(description = "Representa una partida de un jugador.")
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(
            description = "Identificador único de una partida.",
            example = "550e8400-e29b-41d4-a716-446655440000",
            format = "UUID",
            type = "UUID",
            nullable = false
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    @Schema(
            description = "Jugador asociado a la partida.",
            format = "Objeto Player",
            type = "Player",
            nullable = false
    )
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Modo de juego de la partida.",
            example = "DAILY",
            format = "Enum Mode",
            type = "Mode",
            nullable = false
    )
    private Mode mode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Categoría de preguntas de la partida.",
            example = "CIENCIA",
            format = "Enum Category",
            type = "Category",
            nullable = false
    )
    private Category category;

    @Column(nullable = false)
    @Schema(
            description = "Ronda actual en la que se encuentra la partida.",
            example = "3",
            format = "Integer",
            type = "Integer",
            nullable = false
    )
    private Integer currentRound;

    @Column(nullable = false)
    @Schema(
            description = "Aciertos acumulados en la partida.",
            example = "1500",
            format = "Integer",
            type = "Integer",
            nullable = false
    )
    private Integer score;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "game_session_used_questions",
            joinColumns = @JoinColumn(name = "game_session_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    @Schema(
            description = "Conjunto de preguntas ya utilizadas en la partida.",
            format = "Set<Question>",
            type = "Set",
            nullable = false
    )
    private Set<Question> usedQuestions;

    @Column(nullable = false)
    @Schema(
            description = "Fecha y hora de creación de la partida.",
            example = "2023-01-01T12:00:00",
            format = "LocalDateTime",
            type = "LocalDateTime",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Estado actual de la partida.",
            example = "IN_PROGRESS",
            format = "Enum Status",
            type = "Status",
            nullable = false
    )
    private Status status;
}