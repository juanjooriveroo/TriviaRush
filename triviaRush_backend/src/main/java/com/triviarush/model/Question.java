package com.triviarush.model;

import com.triviarush.enums.Category;
import com.triviarush.enums.Difficult;
import com.triviarush.exception.CorrectAnswerNotOptionException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Table(name = "questions")
@Schema(description = "Representa una pregunta del juego.")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(
            description = "Identificador único de la pregunta.",
            example = "550e8400-e29b-41d4-a716-446655440000",
            format = "UUID",
            type = "UUID",
            nullable = false
    )
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(
            description = "Texto de la pregunta.",
            example = "¿Cuál es la capital de Francia?",
            format = "String",
            type = "String",
            nullable = false
    )
    private String question;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_text", nullable = false)
    @Size(min = 2, max = 4, message = "Debe haber entre 2 y 4 opciones.")
    @Schema(
            description = "Lista de opciones de respuesta.",
            example = "[\"Londres\", \"París\", \"Berlín\", \"Madrid\"]",
            format = "List<String>",
            type = "List",
            nullable = false
    )
    private List<String> options;

    @Column(nullable = false)
    @Schema(
            description = "Respuesta correcta a la pregunta.",
            example = "París",
            format = "String",
            type = "String",
            nullable = false
    )
    private String correctAnswer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Categoría a la que pertenece la pregunta.",
            example = "GEOGRAFIA",
            format = "Enum Category",
            type = "Category",
            nullable = false
    )
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Dificultad de la pregunta.",
            example = "DIFICIL",
            format = "Enum Dificult",
            type = "Dificult",
            nullable = false
    )
    private Difficult difficult;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (!options.contains(correctAnswer)) {
            throw new CorrectAnswerNotOptionException("La respuesta correcta debe estar en las opciones.");
        }
    }
}