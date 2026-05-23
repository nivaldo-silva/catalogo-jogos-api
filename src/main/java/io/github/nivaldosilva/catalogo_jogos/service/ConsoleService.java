package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.dto.ConsoleDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceAlreadyExistsException;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceNotFoundException;
import io.github.nivaldosilva.catalogo_jogos.mapper.ConsoleMapper;
import io.github.nivaldosilva.catalogo_jogos.repository.ConsoleRepository;
import io.github.nivaldosilva.catalogo_jogos.specification.ConsoleSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
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
		log.info("Iniciando cadastro de console com nome '{}'", request.nome());

		consoleRepository.findByNome(request.nome()).ifPresent(existing -> {
			log.warn("Cadastro rejeitado: já existe um console com o nome '{}'", request.nome());
			throw new ResourceAlreadyExistsException("Console com nome '" + request.nome() + "' já existe");
		});

		Console console = ConsoleMapper.toEntity(request);
		Console saved = consoleRepository.save(console);
		log.info("Console '{}' cadastrado com sucesso. ID gerado: {}", saved.getNome(), saved.getId());
		return ConsoleMapper.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<ConsoleDto.consoleResponse> findAll(String nome, String descricao) {
		log.info("Buscando consoles com filtros: nome='{}', descricao='{}'", nome, descricao);

		Specification<Console> spec = Specification
				.where(ConsoleSpecification.nomeContains(nome))
				.and(ConsoleSpecification.descricaoContains(descricao));

		List<ConsoleDto.consoleResponse> consoles = consoleRepository.findAll(spec)
				.stream()
				.map(ConsoleMapper::toResponse)
				.toList();

		log.info("Consulta concluída. Total de consoles encontrados: {}", consoles.size());
		return consoles;
	}

	@Transactional(readOnly = true)
	public ConsoleDto.consoleResponse findById(UUID id) {
		log.info("Buscando console com ID {}", id);
		return consoleRepository.findById(id)
				.map(console -> {
					log.info("Console encontrado: '{}' (ID: {})", console.getNome(), console.getId());
					return ConsoleMapper.toResponse(console);
				})
				.orElseThrow(() -> {
					log.warn("Console não encontrado para o ID {}", id);
					return new ResourceNotFoundException("Console", id);
				});
	}

	@Transactional
	public ConsoleDto.consoleResponse updateById(UUID id, ConsoleDto.consoleRequest request) {
		log.info("Iniciando atualização do console com ID {}", id);
		Console existing = consoleRepository.findById(id)
				.orElseThrow(() -> {
					log.warn("Atualização cancelada: console não encontrado para o ID {}", id);
					return new ResourceNotFoundException("Console", id);
				});

		log.info("Console '{}' localizado. Aplicando alterações: nome='{}', descricao='{}'",
				existing.getNome(), request.nome(), request.descricao());
		existing.setNome(request.nome());
		existing.setDescricao(request.descricao());

		Console updated = consoleRepository.save(existing);
		log.info("Console com ID {} atualizado com sucesso para o nome '{}'", id, updated.getNome());
		return ConsoleMapper.toResponse(updated);
	}

	@Transactional
	public void deleteById(UUID id) {
		log.info("Iniciando exclusão do console com ID {}", id);
		if (!consoleRepository.existsById(id)) {
			log.warn("Exclusão cancelada: console não encontrado para o ID {}", id);
			throw new ResourceNotFoundException("Console", id);
		}
		consoleRepository.deleteById(id);
		log.info("Console com ID {} excluído com sucesso", id);
	}
}