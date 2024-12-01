package fr.cacib.routingservice.message.infrastructure.adapters.out;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SendMessageContentTest {

	@Mock
	private JmsTemplate jmsTemplate;

	@InjectMocks
	private SendMessageContent sendMessageContent;

	@Test
	void testSendMessageContent_SuccessfulSend() {
		String content = "Test Message";
		String deadLetterQueue = "deadLetterQueue";

		sendMessageContent.sendMessageContent(deadLetterQueue, content);

		verify(jmsTemplate).convertAndSend(deadLetterQueue, content);
	}

}
