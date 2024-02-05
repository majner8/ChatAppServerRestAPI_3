package ChatAPP_RabitMQ.Listener;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import ChatAPP_RabitMQ.RabitMQProperties;
import ChatAPP_RabitMQ.Consumer.RabbitMQConsumerManager;

@Component
@Order(5)
public class rabitMQListenerService implements RabbitMQConsumerManager{


	@Autowired
	private RabitMQProperties amqProp;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private ChannelAwareMessageListener listener;

	@Override
	public void startConsume(String userdeviceID, StompHeaderAccessor headerAccessor) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(userdeviceID);
	    container.setMessageListener(listener);
	    headerAccessor.setHeader(userdeviceID, container);
	    headerAccessor.setHeader(this.amqProp.getContainerHeaderName(), container);
	    container.start();
	}

	@Override
	public void stopConsume(String userdeviceID, StompHeaderAccessor headerAccessor) {
		
	}

}
