package fr.cacib.routingservice.message.domain.exceptions;

import java.util.UUID;

public class MessageNotFoundException extends RuntimeException {

	public MessageNotFoundException(String id) {
		super("Message with id %s was not found".formatted(id));
	}
}
