package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import io.github.nivaldosilva.catalogo_jogos.repository.ConsoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsoleService {

	private final ConsoleRepository consoleRepository;

	@Transactional
	public Console addConsole(Console console) {
		if (consoleRepository.existsById(console.getId())) {
			throw new RuntimeException("Console com id: " + console.getId() + " já adicionado");
		}
		log.info("Cadastrando console: {}", console);
		return consoleRepository.save(console);
	}

	@Transactional(readOnly = true)
	public List<Console> findAll() {
		log.info("Buscando todos os consoles");
		return consoleRepository.findAll();

	}

	@Transactional(readOnly = true)
	public Console findById(UUID id) {
		log.info("Buscando console com id: {}", id);
		return consoleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Console com id: " + id + " não encontrado"));
	}

	@Transactional
	public Console updateById(UUID id, Console console) {
		console.setId(id);
		log.info("Atualizando console com id: {}", id);
		return consoleRepository.save(console);
	}

	@Transactional
	public void deleteById(UUID id) {
		log.info("Deletando console com id: {}", id);
		consoleRepository.deleteById(id);
	}

}
