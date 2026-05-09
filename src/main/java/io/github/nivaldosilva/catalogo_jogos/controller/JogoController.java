package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
import io.github.nivaldosilva.catalogo_jogos.service.JogoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	public ResponseEntity<Jogo> addJogo(@RequestBody Jogo jogo) {
		log.info("Jogo adicionado com sucesso");
		return ResponseEntity.accepted().body(jogoService.addJogo(jogo));
	}

	@GetMapping
	public ResponseEntity<List<Jogo>> findAll() {
		log.info("Listando todos os jogos");
		return ResponseEntity.ok(jogoService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Jogo> findById(@PathVariable UUID id) {
		log.info("Listando jogo com id: {}", id);
		return ResponseEntity.ok(jogoService.findById(id));
	}

	@PutMapping
	public ResponseEntity<Jogo> updateById(@PathVariable UUID id, @RequestBody Jogo jogo) {
		log.info("Jogo atualizado com sucesso");
		return ResponseEntity.ok(jogoService.updateById(id, jogo));
	}

	@DeleteMapping
	public ResponseEntity<Jogo> deleteById(@PathVariable UUID id) {
		log.info("Jogo deletado com sucesso");
		jogoService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
