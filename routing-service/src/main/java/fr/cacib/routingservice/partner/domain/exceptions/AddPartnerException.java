package fr.cacib.routingservice.partner.domain.exceptions;

public class AddPartnerException extends RuntimeException {

	public AddPartnerException(Exception e) {
		super("Failed to add partner: %s".formatted(e.getMessage()), e);
	}
}
