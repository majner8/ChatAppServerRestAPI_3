package ChatAPP_RabitMQ;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import chatAPP_CommontPart.Log4j2.Log4j2;

@Configuration
public class AMQConfig {

	@Autowired
    private AmqpAdmin amqpAdmin;
	
	@Value("${topicExchangeName}")
	private String topicExchangeName;
	@Autowired
	private TopicExchange ex;
	@PostConstruct
	private void init() {
		Log4j2.log.info(Log4j2.MarkerLog.RabitMQ.getMarker(),"Creating topicExchange with name: "+this.topicExchangeName);
		this.amqpAdmin.declareExchange(ex);
	}
	@Bean
	public TopicExchange createTopicExchange() {
		TopicExchange x= new TopicExchange(this.topicExchangeName);
		return x;
	}
}
