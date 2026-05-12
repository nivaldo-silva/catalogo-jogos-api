package io.github.nivaldosilva.catalogo_jogos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.util.UUID;

public class CategoriaDto {

	@Builder
	public record categoriaRequest(@NotBlank @Size(max = 100) String nome,
								   @Size(max = 255) String descricao
	) {}

	@Builder
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record categoriaResponse(UUID id,
									String nome,
									String descricao
	) {}
}