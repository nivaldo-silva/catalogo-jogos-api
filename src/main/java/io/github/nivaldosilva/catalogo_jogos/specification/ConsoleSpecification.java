package io.github.nivaldosilva.catalogo_jogos.specification;

import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import org.springframework.data.jpa.domain.Specification;

public class ConsoleSpecification {

	private ConsoleSpecification() {}

	public static Specification<Console> nomeContains(String nome) {
		return (root, query, cb) ->
				nome == null || nome.isBlank() ? null
						: cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
	}

	public static Specification<Console> descricaoContains(String descricao) {
		return (root, query, cb) ->
				descricao == null || descricao.isBlank() ? null
						: cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
	}
}