package com.triviarush.repository;

import com.triviarush.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findPlayerByEmail(String email);

    Optional<Player> findById(UUID playerId);

    @Query("SELECT p FROM Player p WHERE p.email = :input OR p.username = :input")
    Optional<Player> findByEmailOrUsername(@Param("input") String input);

}
