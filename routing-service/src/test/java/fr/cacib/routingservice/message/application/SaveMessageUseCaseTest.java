package fr.cacib.routingservice.message.application;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.outbound.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SaveMessageUseCaseTest {

	@Mock
	private MessageRepository messageRepository;

	@InjectMocks
	private SaveMessageUseCase saveMessageUseCase;

	private final ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

	@Test
	void shouldSaveMessage() {
		// Arrange
		Message message = Message.builder()
				.id(UUID.randomUUID())
				.content("Message to save")
				.timestamp(Timestamp.valueOf(LocalDateTime.now()))
				.build();

		// Act
		saveMessageUseCase.save(message);

		// Assert
		Mockito.verify(messageRepository).save(messageArgumentCaptor.capture());
		assertEquals(message.getId(), messageArgumentCaptor.getValue().getId());
		assertEquals(message.getContent(), messageArgumentCaptor.getValue().getContent());
		assertEquals(message.getTimestamp(), messageArgumentCaptor.getValue().getTimestamp());
	}
}
