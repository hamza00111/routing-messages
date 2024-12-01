package fr.cacib.routingservice.message.infrastructure.listener;

import fr.cacib.routingservice.message.config.mq.MQConfigProperties;
import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.inbound.IReadMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.inbound.ISaveMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.outbound.ISendMessageContent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MQListenerTest {

	@Mock
	private IReadMessageUseCase readMessageUseCase;

	@Mock
	private ISaveMessageUseCase saveMessageUseCase;

	@Mock
	private RetryTemplate retryTemplate;

	@Mock
	private ISendMessageContent sendMessageContent;

	@Mock
	private MQConfigProperties properties;

	@Mock
	private jakarta.jms.Message jmsMessage;

	@InjectMocks
	private MQListener mqListener;

	@Test
	void testOnMessage_SuccessfulProcessing() throws Exception {
		String content = "Test Message";
		Message message = new Message();

		when(readMessageUseCase.readMessage(content)).thenReturn(message);

		doAnswer(invocation -> {
			RetryCallback<?, ?> callback = invocation.getArgument(0);
			return callback.doWithRetry(mock(RetryContext.class));
		}).when(retryTemplate).execute(any(RetryCallback.class));

		mqListener.onMessage(content, jmsMessage);

		verify(readMessageUseCase).readMessage(content);
		verify(saveMessageUseCase).save(message);
		verify(jmsMessage).acknowledge();
		verify(sendMessageContent, never()).sendMessageContent(anyString(), anyString());
	}

	@Test
	void testOnMessage_SendToDeadLetterAfterExceptionDuringProcessing() {
		String content = "Test Message";
		String deadLetterQueue = "deadLetterQueue";

		when(properties.getDeadLetterQueue()).thenReturn(deadLetterQueue);
		doThrow(new RuntimeException("Processing error")).when(retryTemplate).execute(any());

		mqListener.onMessage(content, jmsMessage);

		verify(readMessageUseCase, never()).readMessage(anyString());
		verify(saveMessageUseCase, never()).save(any());
		verify(sendMessageContent).sendMessageContent(deadLetterQueue, content);
	}
}
