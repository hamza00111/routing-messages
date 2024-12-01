package fr.cacib.routingservice.message.domain.ports.inbound;

import fr.cacib.routingservice.message.domain.model.Message;

public interface IReadMessageUseCase {

	Message readMessage(String content);
}
