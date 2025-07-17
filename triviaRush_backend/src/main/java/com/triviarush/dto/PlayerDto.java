package com.triviarush.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlayerDto(
        UUID id,
        String username,
        String email,
        Integer level,
        Integer totalXP,
        Integer karmaPoints,
        Integer maxDailyStreak,
        Integer bestInfiniteScore
){}