package rs.ac.ni.pmf.rwa.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.ni.pmf.rwa.bookstore.exception.ResourceNotFoundException;
import rs.ac.ni.pmf.rwa.bookstore.model.ErrorDto;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ErrorHandler
{
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDto handleResourceNotFoundException(final ResourceNotFoundException ex)
	{
		return ErrorDto.builder()
		               .timestamp(OffsetDateTime.now())
		               .message(ex.getMessage())
		               .build();
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorDto> handleResponseStatusException(final ResponseStatusException ex)
	{
		final ErrorDto error =  ErrorDto.builder()
		               .timestamp(OffsetDateTime.now())
		               .message(ex.getReason())
		               .build();

		return ResponseEntity.status(ex.getStatusCode()).body(error);
	}
}
