package fr.cacib.routingservice.message.web;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.inbound.IFindMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.inbound.IFetchMessagesUseCase;
import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

	private final IFetchMessagesUseCase fetchMessagesUseCase;
	private final IFindMessageUseCase findMessageUseCase;

	public MessageController(IFetchMessagesUseCase fetchMessagesUseCase,
							 IFindMessageUseCase findMessageUseCase) {
		this.fetchMessagesUseCase = fetchMessagesUseCase;
		this.findMessageUseCase = findMessageUseCase;
	}

	@GetMapping
	public ResponseEntity<PaginatedResponse<Message>> getMessages(
			@RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "10") int limit) {

		PaginatedResponse<Message> response = fetchMessagesUseCase.getMessages(offset,
				limit);

		if (response.getContent().isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Message> getMessage(
			@PathVariable String id) {
		return ResponseEntity.ok(findMessageUseCase.findById(id));
	}
}
