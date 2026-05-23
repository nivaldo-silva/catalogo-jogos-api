package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.dto.ConsoleDto;
import io.github.nivaldosilva.catalogo_jogos.service.ConsoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consoles")
@RequiredArgsConstructor
public class ConsoleController {

	private final ConsoleService consoleService;

	@PostMapping
	public ResponseEntity<ConsoleDto.consoleResponse> addConsole(
			@RequestBody @Valid ConsoleDto.consoleRequest request) {
		ConsoleDto.consoleResponse response = consoleService.addConsole(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<ConsoleDto.consoleResponse>> findAll(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) String descricao) {
		return ResponseEntity.ok(consoleService.findAll(nome, descricao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ConsoleDto.consoleResponse> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(consoleService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ConsoleDto.consoleResponse> updateById(
			@PathVariable UUID id,
			@RequestBody @Valid ConsoleDto.consoleRequest request) {
		return ResponseEntity.ok(consoleService.updateById(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		consoleService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}