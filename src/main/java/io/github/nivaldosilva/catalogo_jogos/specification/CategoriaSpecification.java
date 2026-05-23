package io.github.nivaldosilva.catalogo_jogos.specification;

import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import org.springframework.data.jpa.domain.Specification;

public class CategoriaSpecification {

	private CategoriaSpecification() {}

	public static Specification<Categoria> nomeContains(String nome) {
		return (root, query, cb) ->
				nome == null || nome.isBlank() ? null
						: cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
	}

	public static Specification<Categoria> descricaoContains(String descricao) {
		return (root, query, cb) ->
				descricao == null || descricao.isBlank() ? null
						: cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
	}
}