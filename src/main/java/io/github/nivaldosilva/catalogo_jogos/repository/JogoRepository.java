package io.github.nivaldosilva.catalogo_jogos.repository;

import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JogoRepository extends JpaRepository<Jogo, UUID> {
}
