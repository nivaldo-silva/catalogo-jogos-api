package io.github.nivaldosilva.catalogo_jogos.repository;

import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JogoRepository extends JpaRepository<Jogo, UUID> {
	Optional<Object> findByNome(@NotBlank @Size(max = 150) String nome);
}
