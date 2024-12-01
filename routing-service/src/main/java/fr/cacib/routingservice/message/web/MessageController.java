package fr.cacib.routingservice.message.web;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.inbound.IFindMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.inbound.IReadMessagesUseCase;
import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

	private final IReadMessagesUseCase readMessagesUseCase;
	private final IFindMessageUseCase findMessageUseCase;

	public MessageController(IReadMessagesUseCase readMessagesUseCase,
							 IFindMessageUseCase findMessageUseCase) {
		this.readMessagesUseCase = readMessagesUseCase;
		this.findMessageUseCase = findMessageUseCase;
	}

	@GetMapping
	public ResponseEntity<PaginatedResponse<Message>> getMessages(
			@RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "10") int limit) {

		PaginatedResponse<Message> response = readMessagesUseCase.getMessages(offset,
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
