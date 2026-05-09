package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.entity.Jogo;
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
	public Jogo addJogo(Jogo jogo) {
		if (jogoRepository.existsById(jogo.getId())) {
			throw new RuntimeException("Jogo com id: " + jogo.getId() + " já adicionado");
		}
		log.info("Cadastrando jogo: {}", jogo);
		return jogoRepository.save(jogo);
	}

	@Transactional(readOnly = true)
	public List<Jogo> findAll() {
		log.info("Buscando todos os jogos");
		return jogoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Jogo findById(UUID id) {
		log.info("Buscando jogo com id: {}", id);
		return jogoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Jogo com id: " + id + " não encontrado"));
	}

	@Transactional
	public Jogo updateById(UUID id, Jogo jogo) {
		jogo.setId(id);
		log.info("Atualizando jogo com id: {}", id);
		return jogoRepository.save(jogo);
	}

	@Transactional
	public void deleteById(UUID id) {
		log.info("Deletando jogo com id: {}", id);
		jogoRepository.deleteById(id);
	}

}
