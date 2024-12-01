package fr.cacib.routingservice.message.application;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.inbound.IReadMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.Clock;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
public class ReadMessageUseCase implements IReadMessageUseCase {

	private final Clock clock;

	public ReadMessageUseCase(Clock clock) {
		this.clock = clock;
	}

	@Override
	public Message readMessage(String content) {
		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("Message is empty");
		}

		return Message.builder()
				.id(UUID.randomUUID())
				.content(content)
				.timestamp(Timestamp.valueOf(clock.now()))
				.build();
	}
}
