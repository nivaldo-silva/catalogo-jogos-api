package io.github.nivaldosilva.catalogo_jogos.mapper;

import io.github.nivaldosilva.catalogo_jogos.dto.CategoriaDto;
import io.github.nivaldosilva.catalogo_jogos.dto.ConsoleDto;
import io.github.nivaldosilva.catalogo_jogos.dto.JogoDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
import lombok.experimental.UtilityClass;
import java.util.List;

@UtilityClass
public class JogoMapper {

	public Jogo toEntity(JogoDto.jogoRequest request, List<Console> consoles, List<Categoria> categorias) {
		return Jogo.builder()
				.nome(request.nome())
				.descricao(request.descricao())
				.dataLancamento(request.dataLancamento())
				.desenvolvedora(request.desenvolvedora())
				.urlDaImagem(request.urlDaImagem())
				.consoles(consoles)
				.categorias(categorias)
				.build();
	}

	public JogoDto.jogoResponse toResponse(Jogo jogo) {
		List<ConsoleDto.consoleResponse> consoles = jogo.getConsoles() == null ? List.of() :
				jogo.getConsoles().stream()
						.map(ConsoleMapper::toResponse)
						.toList();

		List<CategoriaDto.categoriaResponse> categorias = jogo.getCategorias() == null ? List.of() :
				jogo.getCategorias().stream()
						.map(CategoriaMapper::toResponse)
						.toList();

		return JogoDto.jogoResponse.builder()
				.id(jogo.getId())
				.nome(jogo.getNome())
				.descricao(jogo.getDescricao())
				.dataLancamento(jogo.getDataLancamento())
				.desenvolvedora(jogo.getDesenvolvedora())
				.urlDaImagem(jogo.getUrlDaImagem())
				.consoles(consoles)
				.categorias(categorias)
				.build();
	}
}