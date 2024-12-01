package fr.cacib.routingservice.message.domain.ports;

import java.time.LocalDateTime;

public interface Clock {

	default LocalDateTime now() {
		return LocalDateTime.now();
	}
}
