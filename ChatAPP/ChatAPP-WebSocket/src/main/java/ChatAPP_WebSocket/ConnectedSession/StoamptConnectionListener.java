package ChatAPP_WebSocket.ConnectedSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import ChatAPP_RabitMQ.Consumer.RabbitMQConsumerManager;

@Component
public class StoamptConnectionListener {

	@Autowired
	private RabbitMQConsumerManager rabitMQConsumer;
	
	@EventListener
	public void userConnect(SessionConnectedEvent event) {
		 StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
		 this.rabitMQConsumer.startConsume(headers.getUser().getName(), headers);
	}
	@EventListener
	public void userDisconnect(SessionDisconnectEvent event) {
		
	}
	
}
