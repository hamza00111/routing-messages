package fr.cacib.routingservice.message.application;

import fr.cacib.routingservice.message.domain.exceptions.MessageNotFoundException;
import fr.cacib.routingservice.common.NotUUIDException;
import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.outbound.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindMessageUseCaseTest {

	@Mock
	private MessageRepository messageRepository;

	@InjectMocks
	private FindMessageUseCase findMessageUseCase;

	@Test
	void findById_ShouldReturnMessage_WhenMessageExists() {
		// Arrange
		UUID messageId = UUID.randomUUID();
		Message expectedMessage = Message.builder()
				.content("Sample content")
				.id(messageId)
				.timestamp(Timestamp.valueOf(LocalDateTime.now()))
				.build();
		when(messageRepository.findById(messageId)).thenReturn(Optional.of(expectedMessage));

		// Act
		Message actualMessage = findMessageUseCase.findById(messageId.toString());

		// Assert
		assertNotNull(actualMessage);
		assertEquals(expectedMessage,
				actualMessage);
		verify(messageRepository).findById(messageId);
	}

	@Test
	void findById_ShouldThrowNotUUIDException_WhenInvalidUUID() {
		// Arrange
		String invalidUUID = "invalid-uuid";

		// Act & Assert
		NotUUIDException exception = assertThrows(NotUUIDException.class,
				() -> {
					findMessageUseCase.findById(invalidUUID);
				});

		assertEquals("The id %s is not a UUID".formatted(invalidUUID),
				exception.getMessage());
		verifyNoInteractions(messageRepository);
	}

	@Test
	void findById_ShouldThrowMessageNotFoundException_WhenMessageDoesNotExist() {
		// Arrange
		UUID messageId = UUID.randomUUID();
		when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

		// Act & Assert
		MessageNotFoundException exception = assertThrows(MessageNotFoundException.class,
				() -> {
					findMessageUseCase.findById(messageId.toString());
				});

		assertEquals("Message with id %s was not found".formatted(messageId),
				exception.getMessage());
		verify(messageRepository).findById(messageId);
	}

}
