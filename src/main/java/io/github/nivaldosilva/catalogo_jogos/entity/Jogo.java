package io.github.nivaldosilva.catalogo_jogos.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "jogos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Jogo {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotBlank
	@Size(max = 150)
	@Column(nullable = false, length = 150)
	private String nome;

	@Size(max = 500)
	@Column(length = 500)
	private String descricao;

	@NotNull
	@Column(name = "data_lancamento",nullable = false)
	private LocalDate dataLancamento;

	@Size(max = 100)
	@Column(length = 100)
	private String desenvolvedora;

	@Size(max = 255)
	@Column(name = "url_da_imagem",length = 255)
	private String urlDaImagem;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "jogo_console",
			joinColumns = @JoinColumn(name = "jogo_id"),
			inverseJoinColumns = @JoinColumn(name = "console_id"))
	private List<Console> consoles = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "jogo_categoria",
			joinColumns = @JoinColumn(name = "jogo_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private List<Categoria> categorias = new ArrayList<>();
}
