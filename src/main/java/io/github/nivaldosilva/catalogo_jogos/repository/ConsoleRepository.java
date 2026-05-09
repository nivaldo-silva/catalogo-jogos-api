package io.github.nivaldosilva.catalogo_jogos.repository;

import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConsoleRepository extends JpaRepository<Console, UUID> {
}
