package fr.cacib.routingservice.message.application;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.outbound.MessageRepository;
import fr.cacib.routingservice.message.domain.valueobject.PaginatedResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadMessagesUseCaseTest {

	@Mock
	private MessageRepository messageRepository;

	@InjectMocks
	private ReadMessagesUseCase readMessagesUseCase;

	@Test
	void testGetMessages() {
		// Arrange
		int offset = 0;
		int limit = 5;

		Message message1 = Message.builder()
				.id(UUID.randomUUID())
				.content("Message 1")
				.build();

		Message message2 = Message.builder()
				.id(UUID.randomUUID())
				.content("Message 2")
				.build();

		PaginatedResponse<Message> paginatedResponse = new PaginatedResponse<>(
				List.of(message1,
						message2),
				0,
				5,
				10,
				2,
				false,
				true
		);

		when(messageRepository.getMessages(offset,
				limit)).thenReturn(paginatedResponse);

		// Act
		PaginatedResponse<Message> result = readMessagesUseCase.getMessages(offset,
				limit);

		// Assert
		assertEquals(2,
				result.getContent()
						.size());
		assertEquals(10,
				result.getTotalElements());
		assertEquals(2,
				result.getTotalPages());
		assertFalse(result.isLast());
		assertTrue(result.isFirst());
		Assertions.assertThat(result.getContent())
				.containsExactlyInAnyOrder(message1,
						message2);
		verify(messageRepository).getMessages(offset,
				limit);
	}
}
