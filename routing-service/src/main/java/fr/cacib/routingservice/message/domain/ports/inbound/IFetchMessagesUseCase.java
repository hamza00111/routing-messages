package fr.cacib.routingservice.message.domain.ports.inbound;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;

public interface IFetchMessagesUseCase {

	PaginatedResponse<Message> getMessages(int offset, int limit);
}
