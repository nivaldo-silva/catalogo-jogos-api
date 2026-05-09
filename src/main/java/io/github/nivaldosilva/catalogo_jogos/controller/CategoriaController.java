package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import io.github.nivaldosilva.catalogo_jogos.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	public ResponseEntity<Categoria> addCategoria(@RequestBody Categoria categoria) {
		log.info("Categoria adicionada com sucesso");
		return ResponseEntity.accepted().body(categoriaService.addCategoria(categoria));
	}

	@GetMapping
	public ResponseEntity<List<Categoria>> findAll() {
		log.info("Listando todas as categorias");
		return ResponseEntity.ok(categoriaService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable UUID id) {
		log.info("Listando categoria com id: {}", id);
		return ResponseEntity.ok(categoriaService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> updateById(@PathVariable UUID id, @RequestBody Categoria categoria) {
		log.info("Categoria atualizada com sucesso");
		return ResponseEntity.ok(categoriaService.updateById(id, categoria));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
		log.info("Categoria deletada com sucesso");
		categoriaService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
