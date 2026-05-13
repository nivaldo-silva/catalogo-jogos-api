package io.github.nivaldosilva.catalogo_jogos.mapper;

import io.github.nivaldosilva.catalogo_jogos.dto.ConsoleDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConsoleMapper {

	public Console toEntity(ConsoleDto.consoleRequest request) {
		return Console.builder()
				.nome(request.nome())
				.descricao(request.descricao())
				.build();
	}

	public ConsoleDto.consoleResponse toResponse(Console console) {
		return ConsoleDto.consoleResponse.builder()
				.id(console.getId())
				.nome(console.getNome())
				.descricao(console.getDescricao())
				.build();
	}
}