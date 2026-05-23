package io.github.nivaldosilva.catalogo_jogos.service;

import io.github.nivaldosilva.catalogo_jogos.dto.CategoriaDto;
import io.github.nivaldosilva.catalogo_jogos.entity.Categoria;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceAlreadyExistsException;
import io.github.nivaldosilva.catalogo_jogos.exception.ResourceNotFoundException;
import io.github.nivaldosilva.catalogo_jogos.mapper.CategoriaMapper;
import io.github.nivaldosilva.catalogo_jogos.repository.CategoriaRepository;
import io.github.nivaldosilva.catalogo_jogos.specification.CategoriaSpecification;
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
public class CategoriaService {

	private final CategoriaRepository categoriaRepository;

	@Transactional
	public CategoriaDto.categoriaResponse addCategoria(CategoriaDto.categoriaRequest request) {
		log.info("Iniciando cadastro de categoria com nome '{}'", request.nome());

		categoriaRepository.findByNome(request.nome()).ifPresent(existing -> {
			log.warn("Cadastro rejeitado: já existe uma categoria com o nome '{}'", request.nome());
			throw new ResourceAlreadyExistsException("Categoria com nome '" + request.nome() + "' já existe");
		});

		Categoria categoria = CategoriaMapper.toEntity(request);
		Categoria saved = categoriaRepository.save(categoria);
		log.info("Categoria '{}' cadastrada com sucesso. ID gerado: {}", saved.getNome(), saved.getId());
		return CategoriaMapper.toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<CategoriaDto.categoriaResponse> findAll(String nome, String descricao) {
		log.info("Buscando categorias com filtros: nome='{}', descricao='{}'", nome, descricao);

		Specification<Categoria> spec = Specification
				.where(CategoriaSpecification.nomeContains(nome))
				.and(CategoriaSpecification.descricaoContains(descricao));

		List<CategoriaDto.categoriaResponse> categorias = categoriaRepository.findAll(spec)
				.stream()
				.map(CategoriaMapper::toResponse)
				.toList();

		log.info("Consulta concluída. Total de categorias encontradas: {}", categorias.size());
		return categorias;
	}

	@Transactional(readOnly = true)
	public CategoriaDto.categoriaResponse findById(UUID id) {
		log.info("Buscando categoria com ID {}", id);
		return categoriaRepository.findById(id)
				.map(categoria -> {
					log.info("Categoria encontrada: '{}' (ID: {})", categoria.getNome(), categoria.getId());
					return CategoriaMapper.toResponse(categoria);
				})
				.orElseThrow(() -> {
					log.warn("Categoria não encontrada para o ID {}", id);
					return new ResourceNotFoundException("Categoria", id);
				});
	}

	@Transactional
	public CategoriaDto.categoriaResponse updateById(UUID id, CategoriaDto.categoriaRequest request) {
		log.info("Iniciando atualização da categoria com ID {}", id);
		Categoria existing = categoriaRepository.findById(id)
				.orElseThrow(() -> {
					log.warn("Atualização cancelada: categoria não encontrada para o ID {}", id);
					return new ResourceNotFoundException("Categoria", id);
				});

		log.info("Categoria '{}' localizada. Aplicando alterações: nome='{}', descricao='{}'",
				existing.getNome(), request.nome(), request.descricao());
		existing.setNome(request.nome());
		existing.setDescricao(request.descricao());

		Categoria updated = categoriaRepository.save(existing);
		log.info("Categoria com ID {} atualizada com sucesso para o nome '{}'", id, updated.getNome());
		return CategoriaMapper.toResponse(updated);
	}

	@Transactional
	public void deleteById(UUID id) {
		log.info("Iniciando exclusão da categoria com ID {}", id);
		if (!categoriaRepository.existsById(id)) {
			log.warn("Exclusão cancelada: categoria não encontrada para o ID {}", id);
			throw new ResourceNotFoundException("Categoria", id);
		}
		categoriaRepository.deleteById(id);
		log.info("Categoria com ID {} excluída com sucesso", id);
	}
}