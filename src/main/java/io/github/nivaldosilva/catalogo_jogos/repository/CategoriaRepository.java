package io.github.nivaldosilva.catalogo_jogos.repository;

import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
