package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.dto.JogoDto;
import io.github.nivaldosilva.catalogo_jogos.service.JogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jogos")
@RequiredArgsConstructor
@Slf4j
public class JogoController {

	private final JogoService jogoService;

	@PostMapping
	public ResponseEntity<JogoDto.jogoResponse> addJogo(
			@RequestBody @Valid JogoDto.jogoRequest request) {
		log.info("Requisição para adicionar jogo: {}", request.nome());
		JogoDto.jogoResponse response = jogoService.addJogo(request, request.urlDaImagem());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<JogoDto.jogoResponse>> findAll() {
		log.info("Requisição para listar todos os jogos");
		return ResponseEntity.ok(jogoService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<JogoDto.jogoResponse> findById(@PathVariable UUID id) {
		log.info("Requisição para buscar jogo com id: {}", id);
		return ResponseEntity.ok(jogoService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<JogoDto.jogoResponse> updateById(
			@PathVariable UUID id,
			@RequestBody @Valid JogoDto.jogoRequest request) {
		log.info("Requisição para atualizar jogo com id: {}", id);
		return ResponseEntity.ok(jogoService.updateById(id, request, request.urlDaImagem()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		log.info("Requisição para deletar jogo com id: {}", id);
		jogoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}