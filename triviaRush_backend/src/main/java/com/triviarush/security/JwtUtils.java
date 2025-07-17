package com.triviarush.security;

import com.triviarush.exception.PlayerNotFoundException;
import com.triviarush.model.Player;
import com.triviarush.repository.PlayerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessExpirationMs}")
    private long accessExpirationMs;

    private final PlayerRepository playerRepository;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Player player) {
        String token = Jwts.builder()
                .setSubject(player.getId().toString())
                .claim("roles", player.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public Player getUserFromToken(String token) {
        String jwt = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        UUID playerId = UUID.fromString(claims.getSubject());
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Jugador no encontrado"));
    }
}