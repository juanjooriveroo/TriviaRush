package com.triviarush.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.triviarush.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
@Schema(description = "Representa un jugador en el sistema.")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(
            description = "Identificador único del jugador.",
            example = "550e8400-e29b-41d4-a716-446655440000",
            format = "UUID",
            type = "UUID",
            nullable = false
    )
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(
            description = "Nombre de usuario del jugador.",
            example = "jugador1",
            format = "String",
            type = "String",
            nullable = false
    )
    private String username;

    @Column(nullable = false, unique = true)
    @Schema(
            description = "Correo electrónico del jugador.",
            example = "jugador@example.com",
            format = "String",
            type = "String",
            nullable = false
    )
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    @Schema(
            description = "Contraseña del jugador (no se muestra en las respuestas).",
            format = "String",
            type = "String",
            nullable = false
    )
    private String password;

    @Column(nullable = false)
    @Min(value = 0, message = "Los puntos de karma no pueden ser negativos.")
    @Schema(
            description = "Puntos de karma acumulados por el jugador.",
            example = "100",
            format = "Integer",
            type = "Integer",
            nullable = false,
            minimum = "0"
    )
    private Integer karmaPoints;

    @Column(nullable = false)
    @Min(value = 0, message = "La experiencia no pueden ser negativa.")
    @Schema(
            description = "Experiencia total acumulada por el jugador.",
            example = "5000",
            format = "Integer",
            type = "Integer",
            nullable = false,
            minimum = "0"
    )
    private Integer totalXP;

    @Column(nullable = false)
    @Min(value = 0, message = "El nivel no pueden ser negativo.")
    @Schema(
            description = "Nivel actual del jugador.",
            example = "5",
            format = "Integer",
            type = "Integer",
            nullable = false,
            minimum = "0"
    )
    private Integer level;

    @Column(nullable = false)
    @Min(value = 0, message = "El récord infinito no puede ser negativo.")
    @Schema(
            description = "Mejor puntuación en modo infinito.",
            example = "47",
            format = "Integer",
            type = "Integer",
            nullable = false,
            minimum = "0"
    )
    private Integer bestInfiniteScore;

    @Column(nullable = false)
    @Min(value = 0, message = "La racha diaria no pueden ser negativa.")
    @Schema(
            description = "Máxima racha diaria conseguida.",
            example = "7",
            format = "Integer",
            type = "Integer",
            nullable = false,
            minimum = "0"
    )
    private Integer maxDailyStreak;

    @Column(nullable = false)
    @Schema(
            description = "Indica si el jugador está activo en el sistema.",
            example = "true",
            format = "Boolean",
            type = "Boolean",
            nullable = false
    )
    private Boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "player_roles", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(
            description = "Roles asignados al jugador.",
            example = "[\"USER\"]",
            format = "Set<Role>",
            type = "Set",
            nullable = false
    )
    private Set<Role> roles;
}