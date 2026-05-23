package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.dto.JogoDto;
import io.github.nivaldosilva.catalogo_jogos.service.JogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jogos")
@RequiredArgsConstructor
public class JogoController {

	private final JogoService jogoService;

	@PostMapping
	public ResponseEntity<JogoDto.jogoResponse> addJogo(
			@RequestBody @Valid JogoDto.jogoRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(jogoService.addJogo(request));
	}

	@GetMapping
	public ResponseEntity<List<JogoDto.jogoResponse>> findAll(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) String desenvolvedora,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
			@RequestParam(required = false) UUID consoleId,
			@RequestParam(required = false) UUID categoriaId) {
		return ResponseEntity.ok(jogoService.findAll(nome, desenvolvedora, dataInicio, dataFim, consoleId, categoriaId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<JogoDto.jogoResponse> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(jogoService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<JogoDto.jogoResponse> updateById(
			@PathVariable UUID id,
			@RequestBody @Valid JogoDto.jogoRequest request) {
		return ResponseEntity.ok(jogoService.updateById(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		jogoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}