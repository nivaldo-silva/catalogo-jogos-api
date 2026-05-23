package io.github.nivaldosilva.catalogo_jogos.specification;

import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.UUID;

public class JogoSpecification {

	private JogoSpecification() {}

	public static Specification<Jogo> nomeContains(String nome) {
		return (root, query, cb) ->
				nome == null || nome.isBlank() ? null
						: cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
	}

	public static Specification<Jogo> desenvolvedoraContains(String desenvolvedora) {
		return (root, query, cb) ->
				desenvolvedora == null || desenvolvedora.isBlank() ? null
						: cb.like(cb.lower(root.get("desenvolvedora")), "%" + desenvolvedora.toLowerCase() + "%");
	}

	public static Specification<Jogo> dataLancamentoAfter(LocalDate dataInicio) {
		return (root, query, cb) ->
				dataInicio == null ? null
						: cb.greaterThanOrEqualTo(root.get("dataLancamento"), dataInicio);
	}

	public static Specification<Jogo> dataLancamentoBefore(LocalDate dataFim) {
		return (root, query, cb) ->
				dataFim == null ? null
						: cb.lessThanOrEqualTo(root.get("dataLancamento"), dataFim);
	}

	public static Specification<Jogo> temConsole(UUID consoleId) {
		return (root, query, cb) -> {
			if (consoleId == null) return null;
			query.distinct(true);
			Join<Jogo, Console> join = root.join("consoles");
			return cb.equal(join.get("id"), consoleId);
		};
	}

	public static Specification<Jogo> temCategoria(UUID categoriaId) {
		return (root, query, cb) -> {
			if (categoriaId == null) return null;
			query.distinct(true);
			Join<Jogo, Categoria> join = root.join("categorias");
			return cb.equal(join.get("id"), categoriaId);
		};
	}
}