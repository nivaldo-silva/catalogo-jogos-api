package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.dto.ConsoleDto;
import io.github.nivaldosilva.catalogo_jogos.service.ConsoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consoles")
@RequiredArgsConstructor
@Slf4j
public class ConsoleController {

	private final ConsoleService consoleService;

	@PostMapping
	public ResponseEntity<ConsoleDto.consoleResponse> addConsole(
			@RequestBody @Valid ConsoleDto.consoleRequest request) {
		log.info("Requisição para adicionar console: {}", request.nome());
		ConsoleDto.consoleResponse response = consoleService.addConsole(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<ConsoleDto.consoleResponse>> findAll() {
		log.info("Requisição para listar todos os consoles");
		return ResponseEntity.ok(consoleService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ConsoleDto.consoleResponse> findById(@PathVariable UUID id) {
		log.info("Requisição para buscar console com id: {}", id);
		return ResponseEntity.ok(consoleService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ConsoleDto.consoleResponse> updateById(
			@PathVariable UUID id,
			@RequestBody @Valid ConsoleDto.consoleRequest request) {
		log.info("Requisição para atualizar console com id: {}", id);
		return ResponseEntity.ok(consoleService.updateById(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		log.info("Requisição para deletar console com id: {}", id);
		consoleService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}