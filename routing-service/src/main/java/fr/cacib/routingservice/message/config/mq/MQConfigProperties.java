package fr.cacib.routingservice.message.config.mq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "ibm.mq")
@Validated
@Getter
@Setter
public class MQConfigProperties {

	@NotNull
	private String hostname;

	@NotNull
	private int port;

	@NotNull
	private String queueManager;

	@NotNull
	private String channel;

	@NotNull
	private String user;

	@NotNull
	private String password;

	@NotNull
	private int transportType;

	@NotNull
	private String deadLetterQueue;

	@NotNull
	private int backOffPolicy;

	@NotNull
	private int maxRetries;
}
