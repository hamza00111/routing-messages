package fr.cacib.routingservice.common;

public class NotUUIDException extends RuntimeException {

	public NotUUIDException(String id) {
		super("The id %s is not a UUID".formatted(id));
	}
}
