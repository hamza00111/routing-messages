package fr.cacib.routingservice.message.application;

import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.Clock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReadMessageUseCaseTest {

	private Clock clock;
	private ReadMessageUseCase readMessageUseCase;

	@BeforeEach
	void setUp() {
		clock = mock(Clock.class);
		readMessageUseCase = new ReadMessageUseCase(clock);
	}

	@Test
	void testReadMessageValidContent() {
		String content = "Test message content";
		LocalDateTime now = LocalDateTime.of(
				2024,
				11,
				30,
				12,
				0
		);
		when(clock.now()).thenReturn(now);

		Message result = readMessageUseCase.readMessage(content);

		assertNotNull(result.getId());
		assertEquals(content, result.getContent());
		assertEquals(now, result.getTimestamp().toLocalDateTime());
	}

	@Test
	void testReadMessageEmptyContent() {
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				readMessageUseCase.readMessage(""));
		assertEquals("Message is empty", exception.getMessage());
	}

	@Test
	void testReadMessageNullContent() {
		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				readMessageUseCase.readMessage(null));
		assertEquals("Message is empty", exception.getMessage());
	}
}
