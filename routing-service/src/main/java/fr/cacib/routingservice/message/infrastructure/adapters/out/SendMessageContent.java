package fr.cacib.routingservice.message.infrastructure.adapters.out;

import fr.cacib.routingservice.message.config.mq.MQConfigProperties;
import fr.cacib.routingservice.message.domain.ports.outbound.ISendMessageContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SendMessageContent implements ISendMessageContent {

	private final JmsTemplate jmsTemplate;

	public SendMessageContent(JmsTemplate jmsTemplate,
							  MQConfigProperties properties) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void sendMessageContent(String letterQueue, String content) {
		try {
			log.info("Sending message content {} to dead letter queue", content);
			jmsTemplate.convertAndSend(letterQueue,
					content);
		} catch (Exception e) {
			log.error("Failed to send to dead letter the message content: {}", content, e);
		}
	}
}
