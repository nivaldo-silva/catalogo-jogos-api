package io.github.nivaldosilva.catalogo_jogos.repository;

import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ConsoleRepository extends JpaRepository<Console, UUID>, JpaSpecificationExecutor<Console> {
	Optional<Console> findByNome(String nome);
}