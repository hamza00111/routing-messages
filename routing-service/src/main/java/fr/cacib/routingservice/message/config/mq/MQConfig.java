package fr.cacib.routingservice.message.config.mq;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.common.CommonConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import jakarta.jms.JMSException;


@Configuration
@EnableJms
public class MQConfig {

	private final MQConfigProperties mqConfigProperties;

	public MQConfig(MQConfigProperties mqConfigProperties) {
		this.mqConfigProperties = mqConfigProperties;
	}

	@Bean
	public MQConnectionFactory mqConnectionFactory() throws JMSException {
		MQConnectionFactory factory = new MQConnectionFactory();
		factory.setHostName(mqConfigProperties.getHostname());
		factory.setPort(mqConfigProperties.getPort());
		factory.setQueueManager(mqConfigProperties.getQueueManager());
		factory.setChannel(mqConfigProperties.getChannel());
		factory.setPort(mqConfigProperties.getPort());
		factory.setStringProperty(CommonConstants.USERID,
				mqConfigProperties.getUser());
		factory.setStringProperty(CommonConstants.PASSWORD,
				mqConfigProperties.getPassword());
		factory.setTransportType(mqConfigProperties.getTransportType());
		factory.setIntProperty(CommonConstants.WMQ_CONNECTION_MODE,
				CommonConstants.WMQ_CM_CLIENT);

		return factory;
	}

	@Bean
	public JmsListenerContainerFactory<?> mqFactory(ConnectionFactory mqConnectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(mqConnectionFactory);
		factory.setConcurrency("5-10");  // Adjust concurrency to your needs
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		return factory;
	}

	@Bean
	public JmsTemplate jmsTemplate(MQConnectionFactory mqConnectionFactory) {
		return new JmsTemplate(mqConnectionFactory);
	}
}
