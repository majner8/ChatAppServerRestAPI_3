package ChatAPP_RabitMQ.Listener;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ChatAPP_RabitMQ.RabitMQProperties;
import chatAPP_CommontPart.AOP.BeanInitAnnotation.Last;
import chatAPP_CommontPart.ApplicationListener.WebSocketSessionListener;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;

/**Class is responsible for starting consuming from queue */
@Component
@Last
public class rabbitMQDeviceUserListenerManager implements WebSocketSessionListener{
	@Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    @Qualifier("customChannelAwareMessageListener")
    private ChannelAwareMessageListener listener;
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue 
	webSocketSession;

	@Autowired
	private RabitMQProperties amqProp;
	
	private void StartConsuming() {
		SimpleMessageListenerContainer container=this.createContainer();
		//save container to session
		this.webSocketSession.getSimpMessageHeaderAccessor().getSessionAttributes().
		put(this.amqProp.getContainerHeaderName(), container);
		container.start();
	}
	private SimpleMessageListenerContainer createContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		//because deviceID+userID is same as name of Queue
	    container.setQueueNames(this.webSocketSession.getConnectionID());
	    container.setMessageListener(listener);
	    return container;
	}
	@Override
	public void UserConnect() {
		this.StartConsuming();
		
	}

	@Override
	public void UserDisconnect() {
		// TODO Auto-generated method stub
		
	}


}
