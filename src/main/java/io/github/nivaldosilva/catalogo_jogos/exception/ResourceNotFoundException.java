package io.github.nivaldosilva.catalogo_jogos.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String resource, UUID id) {
		super(resource + " com id: " + id + " não encontrado(a)");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}