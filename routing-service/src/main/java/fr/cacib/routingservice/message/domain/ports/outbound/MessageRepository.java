package fr.cacib.routingservice.message.domain.ports.outbound;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

	void save(Message message);

	PaginatedResponse<Message> getMessages(int offset,
										   int limit);

	Optional<Message> findById(UUID id);
}
