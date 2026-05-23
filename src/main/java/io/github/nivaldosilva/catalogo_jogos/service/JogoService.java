package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.dto.JogoDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import io.github.nivaldosilva.catalogo_jogos.entity.Console;
import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceAlreadyExistsException;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceNotFoundException;
import io.github.nivaldosilva.catalogo_jogos.mapper.JogoMapper;
import io.github.nivaldosilva.catalogo_jogos.repository.CategoriaRepository;
import io.github.nivaldosilva.catalogo_jogos.repository.ConsoleRepository;
import io.github.nivaldosilva.catalogo_jogos.repository.JogoRepository;
import io.github.nivaldosilva.catalogo_jogos.specification.JogoSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JogoService {

	private final JogoRepository jogoRepository;
	private final ConsoleRepository consoleRepository;
	private final CategoriaRepository categoriaRepository;

	@Transactional
	public JogoDto.jogoResponse addJogo(JogoDto.jogoRequest request) {
		log.info("Iniciando cadastro do jogo '{}'", request.nome());

		jogoRepository.findByNome(request.nome()).ifPresent(existing -> {
			log.warn("Cadastro rejeitado: já existe um jogo com o nome '{}'", request.nome());
			throw new ResourceAlreadyExistsException("Jogo com nome '" + request.nome() + "' já existe");
		});

		log.info("Resolvendo {} console(s) e {} categoria(s) para o jogo '{}'",
				request.consolesIds() != null ? request.consolesIds().size() : 0,
				request.categoriasIds() != null ? request.categoriasIds().size() : 0,
				request.nome());

		List<Console> consoles = resolveConsoles(request.consolesIds());
		List<Categoria> categorias = resolveCategorias(request.categoriasIds());

		Jogo jogo = JogoMapper.toEntity(request, consoles, categorias);
		Jogo saved = jogoRepository.save(jogo);
		log.info("Jogo '{}' cadastrado com sucesso. ID gerado: {}", saved.getNome(), saved.getId());
		return JogoMapper.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<JogoDto.jogoResponse> findAll(String nome, String desenvolvedora,
											  LocalDate dataInicio, LocalDate dataFim,
											  UUID consoleId, UUID categoriaId) {
		log.info("Buscando jogos com filtros: nome='{}', desenvolvedora='{}', dataInicio='{}', dataFim='{}', consoleId='{}', categoriaId='{}'",
				nome, desenvolvedora, dataInicio, dataFim, consoleId, categoriaId);

		Specification<Jogo> spec = Specification
				.where(JogoSpecification.nomeContains(nome))
				.and(JogoSpecification.desenvolvedoraContains(desenvolvedora))
				.and(JogoSpecification.dataLancamentoAfter(dataInicio))
				.and(JogoSpecification.dataLancamentoBefore(dataFim))
				.and(JogoSpecification.temConsole(consoleId))
				.and(JogoSpecification.temCategoria(categoriaId));

		List<JogoDto.jogoResponse> jogos = jogoRepository.findAll(spec)
				.stream()
				.map(JogoMapper::toResponse)
				.toList();

		log.info("Consulta concluída. Total de jogos encontrados: {}", jogos.size());
		return jogos;
	}

	@Transactional(readOnly = true)
	public JogoDto.jogoResponse findById(UUID id) {
		log.info("Buscando jogo com ID {}", id);
		return jogoRepository.findById(id)
				.map(jogo -> {
					log.info("Jogo encontrado: '{}' (ID: {})", jogo.getNome(), jogo.getId());
					return JogoMapper.toResponse(jogo);
				})
				.orElseThrow(() -> {
					log.warn("Jogo não encontrado para o ID {}", id);
					return new ResourceNotFoundException("Jogo", id);
				});
	}

	@Transactional
	public JogoDto.jogoResponse updateById(UUID id, JogoDto.jogoRequest request) {
		log.info("Iniciando atualização do jogo com ID {}", id);
		Jogo existing = jogoRepository.findById(id)
				.orElseThrow(() -> {
					log.warn("Atualização cancelada: jogo não encontrado para o ID {}", id);
					return new ResourceNotFoundException("Jogo", id);
				});

		log.info("Jogo '{}' localizado. Resolvendo {} console(s) e {} categoria(s)",
				existing.getNome(),
				request.consolesIds() != null ? request.consolesIds().size() : 0,
				request.categoriasIds() != null ? request.categoriasIds().size() : 0);

		List<Console> consoles = resolveConsoles(request.consolesIds());
		List<Categoria> categorias = resolveCategorias(request.categoriasIds());

		existing.setNome(request.nome());
		existing.setDescricao(request.descricao());
		existing.setDataLancamento(request.dataLancamento());
		existing.setDesenvolvedora(request.desenvolvedora());
		existing.setUrlDaImagem(request.urlDaImagem());
		existing.setConsoles(consoles);
		existing.setCategorias(categorias);

		Jogo updated = jogoRepository.save(existing);
		log.info("Jogo com ID {} atualizado com sucesso para o nome '{}'", id, updated.getNome());
		return JogoMapper.toResponse(updated);
	}

	@Transactional
	public void deleteById(UUID id) {
		log.info("Iniciando exclusão do jogo com ID {}", id);
		if (!jogoRepository.existsById(id)) {
			log.warn("Exclusão cancelada: jogo não encontrado para o ID {}", id);
			throw new ResourceNotFoundException("Jogo", id);
		}
		jogoRepository.deleteById(id);
		log.info("Jogo com ID {} excluído com sucesso", id);
	}

	private List<Console> resolveConsoles(List<UUID> ids) {
		if (ids == null || ids.isEmpty()) {
			log.debug("Nenhum console informado para o jogo");
			return List.of();
		}
		log.debug("Resolvendo {} console(s) por ID", ids.size());
		return ids.stream()
				.map(id -> consoleRepository.findById(id)
						.orElseThrow(() -> {
							log.warn("Console vinculado não encontrado para o ID {}", id);
							return new ResourceNotFoundException("Console", id);
						}))
				.toList();
	}

	private List<Categoria> resolveCategorias(List<UUID> ids) {
		if (ids == null || ids.isEmpty()) {
			log.debug("Nenhuma categoria informada para o jogo");
			return List.of();
		}
		log.debug("Resolvendo {} categoria(s) por ID", ids.size());
		return ids.stream()
				.map(id -> categoriaRepository.findById(id)
						.orElseThrow(() -> {
							log.warn("Categoria vinculada não encontrada para o ID {}", id);
							return new ResourceNotFoundException("Categoria", id);
						}))
				.toList();
	}
}