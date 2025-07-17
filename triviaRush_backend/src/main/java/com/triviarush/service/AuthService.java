package com.triviarush.service;

import com.triviarush.dto.*;
import com.triviarush.exception.PasswordNotCorrectException;
import com.triviarush.exception.PlayerNotFoundException;
import com.triviarush.mapper.PlayerMapper;
import com.triviarush.model.Player;
import com.triviarush.repository.PlayerRepository;
import com.triviarush.security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlayerMapper playerMapper;
    private final JwtUtils jwtUtils;

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request){
        Player player = playerMapper.toEntityFromRegisterRequest(request);

        playerRepository.save(player);
        Player playerToReturn = playerRepository.findPlayerByEmail(request.email());

        return RegisterResponseDto.builder()
                .token(jwtUtils.generateToken(playerToReturn))
                .build();
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto request){
        Player player = playerRepository.findByEmailOrUsername(request.credential())
                .orElseThrow(() -> new PlayerNotFoundException("Jugador no encontrado"));

        if (!passwordEncoder.matches(request.password(), player.getPassword())) {
            throw new PasswordNotCorrectException("La contraseña introducida no es correcta");
        }

        return LoginResponseDto.builder()
                .token(jwtUtils.generateToken(player))
                .build();
    }
}
