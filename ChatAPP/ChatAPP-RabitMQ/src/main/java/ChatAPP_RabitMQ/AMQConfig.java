package ChatAPP_RabitMQ;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQConfig {

	@Value("${topicExchangeName}")
	private String topicExchangeName;
	@Bean
	public TopicExchange createTopicExchange() {
		return new TopicExchange(this.topicExchangeName);
	}
}
