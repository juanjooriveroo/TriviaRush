package com.triviarush.mapper;

import com.triviarush.dto.PlayerDto;
import com.triviarush.dto.RegisterRequestDto;
import com.triviarush.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerMapper {
    private final PasswordEncoder passwordEncoder;

    public PlayerDto toDtoFromPlayer(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .email(player.getEmail())
                .level(player.getLevel())
                .totalXP(player.getTotalXP())
                .karmaPoints(player.getKarmaPoints())
                .maxDailyStreak(player.getMaxDailyStreak())
                .bestInfiniteScore(player.getBestInfiniteScore())
                .username(player.getUsername())
                .build();
    }

    public Player toEntityFromRegisterRequest(RegisterRequestDto request) {
        return Player.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .roles(request.role())
                .level(1)
                .karmaPoints(0)
                .active(true)
                .bestInfiniteScore(0)
                .maxDailyStreak(0)
                .totalXP(0)
                .build();
    }
}
