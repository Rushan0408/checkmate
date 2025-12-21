package io.github.Rushan0408.checkmate.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import io.github.Rushan0408.checkmate.model.Player;

import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {

    Optional<Player> findByUsername(String username);
}
