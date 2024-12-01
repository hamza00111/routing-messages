package fr.cacib.routingservice.message.infrastructure.listener;

import fr.cacib.routingservice.message.config.mq.MQConfigProperties;
import fr.cacib.routingservice.message.domain.model.Message;
import fr.cacib.routingservice.message.domain.ports.inbound.IReadMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.outbound.ISaveMessageUseCase;
import fr.cacib.routingservice.message.domain.ports.outbound.ISendMessageContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQListener {

	private final IReadMessageUseCase readMessageUseCase;
	private final ISaveMessageUseCase saveMessageUseCase;
	private final RetryTemplate retryTemplate;
	private final ISendMessageContent sendMessageContent;
	private final MQConfigProperties properties;

	public MQListener(IReadMessageUseCase readMessageUseCase,
					  ISaveMessageUseCase saveMessageUseCase,
					  RetryTemplate retryTemplate,
					  ISendMessageContent sendMessageContent,
					  MQConfigProperties properties) {
		this.readMessageUseCase = readMessageUseCase;
		this.saveMessageUseCase = saveMessageUseCase;
		this.retryTemplate = retryTemplate;
		this.sendMessageContent = sendMessageContent;
		this.properties = properties;
	}

	@JmsListener(destination = "${ibm.mq.queue}", containerFactory = "mqFactory")
	public void onMessage(String content, jakarta.jms.Message jmsMessage) {
		try {
			log.info("message received: {}", content);
			retryTemplate.execute(retryContext -> {
				Message message = readMessageUseCase.readMessage(content);
				saveMessageUseCase.save(message);
				jmsMessage.acknowledge();
				return null;
			});
		} catch (Exception e) {
			log.error("Failed to process message: {}", content, e);
			String deadLetterQueue = properties.getDeadLetterQueue();
			sendMessageContent.sendMessageContent(deadLetterQueue, content);
		}
	}
}
