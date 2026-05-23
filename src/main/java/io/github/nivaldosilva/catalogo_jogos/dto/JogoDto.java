package io.github.nivaldosilva.catalogo_jogos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class JogoDto {

	@Builder
	public record jogoRequest(
			@NotBlank @Size(max = 150) String nome,
			@Size(max = 500) String descricao,
			@NotNull LocalDate dataLancamento,
			@Size(max = 100) String desenvolvedora,
			@Size(max = 255) String urlDaImagem,
			List<UUID> consolesIds,
			List<UUID> categoriasIds
	) {}

	@Builder
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record jogoResponse(
			UUID id,
			String nome,
			String descricao,
			LocalDate dataLancamento,
			String desenvolvedora,
			String urlDaImagem,
			List<ConsoleDto.consoleResponse> consoles,
			List<CategoriaDto.categoriaResponse> categorias
	) {}
}