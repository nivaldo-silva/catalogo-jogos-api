package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.dto.ConsoleDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceAlreadyExistsException;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceNotFoundException;
import io.github.nivaldosilva.catalogo_jogos.mapper.ConsoleMapper;
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
	public ConsoleDto.consoleResponse addConsole(ConsoleDto.consoleRequest request) {

		consoleRepository.findByNome(request.nome()).ifPresent(existing -> {
			throw new ResourceAlreadyExistsException("Console com nome '" + request.nome() + "' já existe");
		});

		Console console = ConsoleMapper.toEntity(request);
		Console saved = consoleRepository.save(console);
		log.info("Console cadastrado com id: {}", saved.getId());
		return ConsoleMapper.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<ConsoleDto.consoleResponse> findAll() {
		log.info("Buscando todos os consoles");
		return consoleRepository.findAll()
				.stream()
				.map(ConsoleMapper::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public ConsoleDto.consoleResponse findById(UUID id) {
		log.info("Buscando console com id: {}", id);
		return consoleRepository.findById(id)
				.map(ConsoleMapper::toResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Console", id));
	}

	@Transactional
	public ConsoleDto.consoleResponse updateById(UUID id, ConsoleDto.consoleRequest request) {
		Console existing = consoleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Console", id));

		existing.setNome(request.nome());
		existing.setDescricao(request.descricao());

		Console updated = consoleRepository.save(existing);
		log.info("Console com id: {} atualizado", id);
		return ConsoleMapper.toResponse(updated);
	}

	@Transactional
	public void deleteById(UUID id) {
		if (!consoleRepository.existsById(id)) {
			throw new ResourceNotFoundException("Console", id);
		}
		consoleRepository.deleteById(id);
		log.info("Console com id: {} deletado", id);
	}
}