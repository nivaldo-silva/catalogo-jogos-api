package io.github.nivaldosilva.catalogo_jogos.mapper;

import io.github.nivaldosilva.catalogo_jogos.dto.JogoDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
import lombok.experimental.UtilityClass;
import java.util.stream.Collectors;

@UtilityClass
public class JogoMapper {

	public Jogo toEntity(JogoDto.jogoRequest request, String urlDaImagem) {
		return Jogo.builder()
				.nome(request.nome())
				.descricao(request.descricao())
				.dataLancamento(request.dataLancamento())
				.desenvolvedora(request.desenvolvedora())
				.urlDaImagem(urlDaImagem)
				.build();
	}

	public JogoDto.jogoResponse toResponse(Jogo jogo) {
		return JogoDto.jogoResponse.builder()
				.id(jogo.getId())
				.nome(jogo.getNome())
				.descricao(jogo.getDescricao())
				.dataLancamento(jogo.getDataLancamento())
				.desenvolvedora(jogo.getDesenvolvedora())
				.urlDaImagem(jogo.getUrlDaImagem())
				.consoles(jogo.getConsoles().stream()
						.map(ConsoleMapper::toResponse)
						.collect(Collectors.toList()))
				.categorias(jogo.getCategorias().stream()
						.map(CategoriaMapper::toResponse)
						.collect(Collectors.toList()))
				.build();
	}
}