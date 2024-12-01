package fr.cacib.routingservice.message.application;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.inbound.ISaveMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.outbound.MessageRepository;
import org.springframework.stereotype.Component;

@Component
public class SaveMessageUseCase implements ISaveMessageUseCase {

	private final MessageRepository messageRepository;

	public SaveMessageUseCase(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Override
	public void save(Message message) {
		messageRepository.save(message);
	}
}
