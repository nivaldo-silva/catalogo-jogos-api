package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.dto.CategoriaDto;
import io.github.nivaldosilva.catalogo_jogos.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Slf4j
public class CategoriaController {

	private final CategoriaService categoriaService;

	@PostMapping
	public ResponseEntity<CategoriaDto.categoriaResponse> addCategoria(
			@RequestBody @Valid CategoriaDto.categoriaRequest request) {
		log.info("Requisição para adicionar categoria: {}", request.nome());
		CategoriaDto.categoriaResponse response = categoriaService.addCategoria(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<CategoriaDto.categoriaResponse>> findAll() {
		log.info("Requisição para listar todas as categorias");
		return ResponseEntity.ok(categoriaService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDto.categoriaResponse> findById(@PathVariable UUID id) {
		log.info("Requisição para buscar categoria com id: {}", id);
		return ResponseEntity.ok(categoriaService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDto.categoriaResponse> updateById(
			@PathVariable UUID id,
			@RequestBody @Valid CategoriaDto.categoriaRequest request) {
		log.info("Requisição para atualizar categoria com id: {}", id);
		return ResponseEntity.ok(categoriaService.updateById(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		log.info("Requisição para deletar categoria com id: {}", id);
		categoriaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}