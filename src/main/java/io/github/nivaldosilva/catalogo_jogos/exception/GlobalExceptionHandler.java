package io.github.nivaldosilva.catalogo_jogos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final String BASE_URI = "https://catalogo-jogos.github.io/errors";

	@ExceptionHandler(ResourceNotFoundException.class)
	public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problem.setTitle("Recurso não encontrado");
		problem.setType(URI.create(BASE_URI + "/resource-not-found"));
		problem.setProperty("timestamp", Instant.now());
		return problem;
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ProblemDetail handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
		problem.setTitle("Recurso já existe");
		problem.setType(URI.create(BASE_URI + "/resource-already-exists"));
		problem.setProperty("timestamp", Instant.now());
		return problem;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
		String fields = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> "'" + error.getField() + "': " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));

		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.UNPROCESSABLE_ENTITY,
				"Erro(s) de validação: " + fields
		);
		problem.setTitle("Dados inválidos");
		problem.setType(URI.create(BASE_URI + "/validation-error"));
		problem.setProperty("timestamp", Instant.now());
		problem.setProperty("fields", ex.getBindingResult().getFieldErrors().stream()
				.map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList()));
		return problem;
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleGenericException(Exception ex) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"Ocorreu um erro inesperado. Tente novamente mais tarde."
		);
		problem.setTitle("Erro interno do servidor");
		problem.setType(URI.create(BASE_URI + "/internal-error"));
		problem.setProperty("timestamp", Instant.now());
		return problem;
	}

	// Record interno para serializar os erros de campo de forma limpa
	record FieldError(String field, String message) {}
}