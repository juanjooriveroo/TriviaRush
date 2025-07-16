package com.triviarush.model;

import com.triviarush.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suggested_questions")
@Schema(description = "Representa una pregunta sugerida por un jugador para el juego.")
public class SuggestedQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(
            description = "Identificador único de la pregunta sugerida.",
            example = "550e8400-e29b-41d4-a716-446655440000",
            format = "UUID",
            type = "UUID",
            nullable = false
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "suggested_by_player_id", nullable = false)
    @Schema(
            description = "Jugador que sugirió la pregunta.",
            format = "Player",
            type = "Player",
            nullable = false
    )
    private Player suggestedBy;

    @Column(name = "question_text", nullable = false)
    @Schema(
            description = "Texto de la pregunta sugerida.",
            example = "¿Qué lenguaje de programación usa Spring Boot?",
            format = "String",
            type = "String",
            nullable = false
    )
    private String questionText;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "suggested_question_options", joinColumns = @JoinColumn(name = "suggested_question_id"))
    @Column(name = "option_text", nullable = false)
    @Schema(
            description = "Opciones de respuesta para la pregunta sugerida.",
            example = "[\"Python\", \"Java\", \"C++\", \"JavaScript\"]",
            format = "List<String>",
            type = "List",
            nullable = false
    )
    private List<String> options;

    @Column(nullable = false)
    @Schema(
            description = "Respuesta correcta a la pregunta sugerida.",
            example = "Java",
            format = "String",
            type = "String",
            nullable = false
    )
    private String correctAnswer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Categoría sugerida para la pregunta.",
            example = "TECNOLOGIA",
            format = "Enum Category",
            type = "Category",
            nullable = false
    )
    private Category category;

    @Column(nullable = true)
    @Schema(
            description = "Indica si la pregunta fue aprobada por los administradores.",
            example = "true",
            format = "Boolean",
            type = "Boolean",
            nullable = true
    )
    private Boolean approved;
}