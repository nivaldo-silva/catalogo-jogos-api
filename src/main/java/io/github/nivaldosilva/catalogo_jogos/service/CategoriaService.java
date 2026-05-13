package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.dto.CategoriaDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceAlreadyExistsException;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceNotFoundException;
import io.github.nivaldosilva.catalogo_jogos.mapper.CategoriaMapper;
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
	public CategoriaDto.categoriaResponse addCategoria(CategoriaDto.categoriaRequest request) {

		categoriaRepository.findByNome(request.nome()).ifPresent(existing -> {
			throw new ResourceAlreadyExistsException("Categoria com nome '" + request.nome() + "' já existe");
		});

		Categoria categoria = CategoriaMapper.toEntity(request);
		Categoria saved = categoriaRepository.save(categoria);
		log.info("Categoria cadastrada com id: {}", saved.getId());
		return CategoriaMapper.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<CategoriaDto.categoriaResponse> findAll() {
		log.info("Buscando todas as categorias");
		return categoriaRepository.findAll()
				.stream()
				.map(CategoriaMapper::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public CategoriaDto.categoriaResponse findById(UUID id) {
		log.info("Buscando categoria com id: {}", id);
		return categoriaRepository.findById(id)
				.map(CategoriaMapper::toResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
	}

	@Transactional
	public CategoriaDto.categoriaResponse updateById(UUID id, CategoriaDto.categoriaRequest request) {
		Categoria existing = categoriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria", id));

		existing.setNome(request.nome());
		existing.setDescricao(request.descricao());

		Categoria updated = categoriaRepository.save(existing);
		log.info("Categoria com id: {} atualizada", id);
		return CategoriaMapper.toResponse(updated);
	}

	@Transactional
	public void deleteById(UUID id) {
		if (!categoriaRepository.existsById(id)) {
			throw new ResourceNotFoundException("Categoria", id);
		}
		categoriaRepository.deleteById(id);
		log.info("Categoria com id: {} deletada", id);
	}
}