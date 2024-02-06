package ChatAPP_RabitMQ.Producer;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ChatAPP_RabitMQ.RabitMQProperties;
import chatAPP_CommontPart.ThreadLocal.RabitMQThreadLocalSession;
import chatAPP_DTO.DTO;

@Component
public class PushMessageRabitMQService implements RabitMQMessageProducerInterface {


	@Autowired
    private RabbitTemplate rabbitTemplate;
	@Autowired
	private RabitMQProperties properties;
//	@Autowired
//	private ThreadLocalSimpMessageHeaderAccessor threadLocalWebSocketSession;
	@Autowired
	private RabitMQThreadLocalSession.RabitMQThreadLocalSessionValue rabitMQPropertiesThreadLocal;
	private void PushMessageToRabitMQ(String exchangeKey,DTO message,MessagePostProcessor messagePostProcessor ) {
	
        this.rabbitTemplate.convertAndSend(exchangeKey, message, messagePostProcessor);

	}
	
	private MessagePostProcessor getMessagePostProcessor(DTO message) {
		 return RBMQMessage -> {
			   MessageProperties messageProperties =new MessageProperties();
			   messageProperties.setMessageId(message.getMessageID());
			  // messageProperties.setType(mesType.name());
			   messageProperties.setPriority(this.rabitMQPropertiesThreadLocal.getRabitMQPriority()); 
			   messageProperties.setHeader(this.properties.getHaveToBeMessageRequiredHeaderName(), this.rabitMQPropertiesThreadLocal.isHaveToBeMessageReDeliver());
			   messageProperties.setHeader(this.properties.getMessagePropertiesWebSocketEndPointHeaderName(), this.rabitMQPropertiesThreadLocal.getWebSocketEndPointPath());
			   return RBMQMessage;
	        };
	}
	

	@Override
	public void PushMessageToRabitMQ(DTO message, long UserRecipientId) {
		this.PushMessageToRabitMQ(String.valueOf(UserRecipientId), message, this.getMessagePostProcessor(message)); 
	}
	@Override
	public void PushMessageToRabitMQ(DTO message, String queueName) {
		this.PushMessageToRabitMQ(queueName, message, this.getMessagePostProcessor(message));

	}
}