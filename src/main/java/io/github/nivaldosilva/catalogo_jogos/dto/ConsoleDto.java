package io.github.nivaldosilva.catalogo_jogos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.util.UUID;

public class ConsoleDto {

	@Builder
	public record consoleRequest(@NotBlank @Size(max = 100) String nome,
								 @NotBlank @Size(max = 255) String descricao) {}

	@Builder
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record consoleResponse(UUID id,
								  String nome,
								  String descricao) {}
}
