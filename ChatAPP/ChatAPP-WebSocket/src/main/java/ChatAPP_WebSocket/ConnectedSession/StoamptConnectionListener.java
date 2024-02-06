package ChatAPP_WebSocket.ConnectedSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import ChatAPP_RabitMQ.Consumer.RabbitMQConsumerManager;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.Security.CustomUserDetailsInterface;

@Component
public class StoamptConnectionListener {

	@Autowired
	private RabbitMQConsumerManager rabitMQConsumer;
	
	@EventListener
	public void userConnect(SessionConnectedEvent event) {
		
		StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
		CustomUserDetailsInterface userDetails=(CustomUserDetailsInterface)headers.getUser();
		Log4j2.log.info(Log4j2.MarkerLog.WebSocket.getMarker(),
				String.format("Connection was Estabilish, UserID: %s deviceID: %s", userDetails.getUserID(),userDetails.getDeviceID())); 
		this.rabitMQConsumer.startConsume(headers.getUser().getName(), headers);
		 
	}
	@EventListener
	public void userDisconnect(SessionDisconnectEvent event) {
		
	}
	
}
