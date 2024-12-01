package fr.cacib.routingservice.message.application;

import fr.cacib.routingservice.message.domain.exceptions.MessageNotFoundException;
import fr.cacib.routingservice.message.domain.exceptions.NotUUIDException;
import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.inbound.IFindMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.outbound.MessageRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindMessageUseCase implements IFindMessageUseCase {

	private final MessageRepository messageRepository;

	public FindMessageUseCase(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Override
	public Message findById(String id) {
		UUID uuid;
		try {
			uuid = UUID.fromString(id);
		} catch (Exception e) {
			throw new NotUUIDException(id);
		}
		Optional<Message> message = messageRepository.findById(uuid);
		if (message.isEmpty()) {
			throw new MessageNotFoundException(id);
		}
		return message.get();
	}
}
