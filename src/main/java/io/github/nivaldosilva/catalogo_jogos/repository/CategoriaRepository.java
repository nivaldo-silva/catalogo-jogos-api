package io.github.nivaldosilva.catalogo_jogos.repository;

import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID>, JpaSpecificationExecutor<Categoria> {
	Optional<Categoria> findByNome(String nome);
}