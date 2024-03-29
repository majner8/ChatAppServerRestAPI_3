package ChatAPP_RabitMQ;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import chatAPP_CommontPart.Log4j2.Log4j2;

@Configuration
public class AMQConfig {

	@Autowired
    private AmqpAdmin amqpAdmin;
	@Autowired
	private RabitMQProperties properties;

	@Autowired
	private TopicExchange ex;
    @Autowired
    private RabbitTemplate rabbitTemplate;

	@PostConstruct
	private void init() {
		Log4j2.log.info(Log4j2.MarkerLog.RabitMQ.getMarker(),"Creating topicExchange with name: "+this.properties.getTopicExchangeName());
		this.amqpAdmin.declareExchange(ex);
	}
	@Bean
	public TopicExchange createTopicExchange() {
		TopicExchange x= new TopicExchange(this.properties.getTopicExchangeName());
		return x;
	}

    @Autowired
    public void configureRabbitTemplate(Jackson2JsonMessageConverter messageConverter) {
        rabbitTemplate.setMessageConverter(messageConverter);
    }
	
}
