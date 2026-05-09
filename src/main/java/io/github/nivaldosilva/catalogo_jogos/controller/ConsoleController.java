package io.github.nivaldosilva.catalogo_jogos.controller;

import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import io.github.nivaldosilva.catalogo_jogos.service.ConsoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	public ResponseEntity<Console> addConsole(@RequestBody Console console) {
		log.info("Console adicionado com sucesso");
		return ResponseEntity.accepted().body(consoleService.addConsole(console));
	}

	@GetMapping
	public ResponseEntity<List<Console>> findAll() {
		log.info("Listando todos os consoles");
		return ResponseEntity.ok(consoleService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Console> findById(@PathVariable UUID id) {
		log.info("Listando console com id: {}", id);
		return ResponseEntity.ok(consoleService.findById(id));
	}

	@PutMapping
	public ResponseEntity<Console> updateById(@PathVariable UUID id, @RequestBody Console console) {
		log.info("Console atualizado com sucesso");
		return ResponseEntity.ok(consoleService.updateById(id, console));
	}

	@DeleteMapping
	public ResponseEntity<Console> deleteById(@PathVariable UUID id) {
		log.info("Console deletado com sucesso");
		consoleService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
