package fr.cacib.routingservice.message.application;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.inbound.IReadMessagesUseCase;
import fr.cacib.routingservice.message.domain.ports.outbound.MessageRepository;
import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class ReadMessagesUseCase implements IReadMessagesUseCase {

	private final MessageRepository messageRepository;

	public ReadMessagesUseCase(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Override
	public PaginatedResponse<Message> getMessages(int offset,
												  int limit) {
		return messageRepository.getMessages(offset, limit);
	}
}
