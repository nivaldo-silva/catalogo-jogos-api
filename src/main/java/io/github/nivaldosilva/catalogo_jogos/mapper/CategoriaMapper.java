package io.github.nivaldosilva.catalogo_jogos.mapper;

import io.github.nivaldosilva.catalogo_jogos.dto.CategoriaDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoriaMapper {

	public Categoria toEntity(CategoriaDto.categoriaRequest request) {
		return Categoria.builder()
				.nome(request.nome())
				.descricao(request.descricao())
				.build();
	}

	public CategoriaDto.categoriaResponse toResponse(Categoria categoria) {
		return CategoriaDto.categoriaResponse.builder()
				.id(categoria.getId())
				.nome(categoria.getNome())
				.descricao(categoria.getDescricao())
				.build();
	}
}