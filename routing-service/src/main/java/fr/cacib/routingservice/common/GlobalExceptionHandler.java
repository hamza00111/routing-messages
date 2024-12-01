package fr.cacib.routingservice.common;

import fr.cacib.routingservice.message.domain.exceptions.MessageNotFoundException;
import fr.cacib.routingservice.message.domain.exceptions.NotUUIDException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MessageNotFoundException.class)
	public ResponseEntity<String> handleMessageNotFoundException(MessageNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ex.getMessage());
	}

	@ExceptionHandler(NotUUIDException.class)
	public ResponseEntity<String> handleNotUUIDException(NotUUIDException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Invalid UUID: %s".formatted(ex.getMessage()));
	}
}
