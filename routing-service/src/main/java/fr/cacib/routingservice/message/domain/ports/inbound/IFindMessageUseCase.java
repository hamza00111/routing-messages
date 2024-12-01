package fr.cacib.routingservice.message.domain.ports.inbound;

import fr.cacib.routingservice.message.domain.model.Message;

import java.util.UUID;

public interface IFindMessageUseCase {

	Message findById(String id);
}
