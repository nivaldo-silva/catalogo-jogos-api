package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.dto.JogoDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceAlreadyExistsException;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceNotFoundException;
import io.github.nivaldosilva.catalogo_jogos.mapper.JogoMapper;
import io.github.nivaldosilva.catalogo_jogos.repository.JogoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JogoService {

	private final JogoRepository jogoRepository;

	@Transactional
	public JogoDto.jogoResponse addJogo(JogoDto.jogoRequest request, String urlDaImagem) {
		jogoRepository.findByNome(request.nome()).ifPresent(existing -> {
			throw new ResourceAlreadyExistsException("Jogo com nome '" + request.nome() + "' já existe");
		});

		Jogo jogo = JogoMapper.toEntity(request, urlDaImagem);
		Jogo saved = jogoRepository.save(jogo);
		log.info("Jogo cadastrado com id: {}", saved.getId());
		return JogoMapper.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<JogoDto.jogoResponse> findAll() {
		log.info("Buscando todos os jogos");
		return jogoRepository.findAll()
				.stream()
				.map(JogoMapper::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public JogoDto.jogoResponse findById(UUID id) {
		log.info("Buscando jogo com id: {}", id);
		return jogoRepository.findById(id)
				.map(JogoMapper::toResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Jogo", id));
	}

	@Transactional
	public JogoDto.jogoResponse updateById(UUID id, JogoDto.jogoRequest request, String urlDaImagem) {
		Jogo existing = jogoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Jogo", id));

		existing.setNome(request.nome());
		existing.setDescricao(request.descricao());
		existing.setDataLancamento(request.dataLancamento());
		existing.setDesenvolvedora(request.desenvolvedora());
		existing.setUrlDaImagem(urlDaImagem != null ? urlDaImagem : existing.getUrlDaImagem());

		Jogo updated = jogoRepository.save(existing);
		log.info("Jogo com id: {} atualizado", id);
		return JogoMapper.toResponse(updated);
	}

	@Transactional
	public void deleteById(UUID id) {
		if (!jogoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Jogo", id);
		}
		jogoRepository.deleteById(id);
		log.info("Jogo com id: {} deletado", id);
	}
}