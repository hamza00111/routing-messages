package fr.cacib.routingservice.message.domain.ports.outbound;

public interface ISendMessageContent {

	void sendMessageContent(String queue, String content);
}
