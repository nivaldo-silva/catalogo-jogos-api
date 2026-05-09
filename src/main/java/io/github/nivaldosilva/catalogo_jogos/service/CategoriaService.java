package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import io.github.nivaldosilva.catalogo_jogos.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

	private final CategoriaRepository categoriaRepository;

	@Transactional
	public Categoria addCategoria(Categoria categoria) {
		if (categoriaRepository.existsById(categoria.getId())) {
			throw new RuntimeException("Categoria com id: " + categoria.getId() + " já adicionada");
		}
		log.info("Cadastrando categoria: {}", categoria);
		return categoriaRepository.save(categoria);
	}

	@Transactional(readOnly = true)
	public List<Categoria> findAll() {
		log.info("Buscando todos as categorias");
		return categoriaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Categoria findById(UUID id) {
		log.info("Buscando categoria com id: {}", id);
		return categoriaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria com id: " + id + " não encontrada"));
	}

	@Transactional
	public Categoria updateById(UUID id, Categoria categoria) {
		log.info("Atualizando categoria com id: {}", id);
		return categoriaRepository.save(categoria);
	}

	@Transactional
	public void deleteById(UUID id) {
		log.info("Deletando categoria com id: {}", id);
		categoriaRepository.deleteById(id);
	}
}
