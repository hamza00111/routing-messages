package fr.cacib.routingservice.message.domain.ports.outbound;

import fr.cacib.routingservice.message.domain.model.Message;

public interface ISaveMessageUseCase {

	void save(Message message);
}
