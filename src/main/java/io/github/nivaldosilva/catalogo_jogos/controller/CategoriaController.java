package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.dto.CategoriaDto;
import io.github.nivaldosilva.catalogo_jogos.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

	private final CategoriaService categoriaService;

	@PostMapping
	public ResponseEntity<CategoriaDto.categoriaResponse> addCategoria(
			@RequestBody @Valid CategoriaDto.categoriaRequest request) {
		CategoriaDto.categoriaResponse response = categoriaService.addCategoria(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<CategoriaDto.categoriaResponse>> findAll(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) String descricao) {
		return ResponseEntity.ok(categoriaService.findAll(nome, descricao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDto.categoriaResponse> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(categoriaService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDto.categoriaResponse> updateById(
			@PathVariable UUID id,
			@RequestBody @Valid CategoriaDto.categoriaRequest request) {
		return ResponseEntity.ok(categoriaService.updateById(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		categoriaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}