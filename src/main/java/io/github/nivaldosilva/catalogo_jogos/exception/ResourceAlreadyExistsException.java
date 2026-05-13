package io.github.nivaldosilva.catalogo_jogos.exception;

import java.util.UUID;

public class ResourceAlreadyExistsException extends RuntimeException {

	public ResourceAlreadyExistsException(String resource, UUID id) {
		super(resource + " com id: " + id + " já existe");
	}

	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
}