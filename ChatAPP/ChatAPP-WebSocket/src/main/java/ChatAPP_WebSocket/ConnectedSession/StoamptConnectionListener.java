package ChatAPP_WebSocket.ConnectedSession;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StoamptConnectionListener {

	@EventListener
	public void userConnect(SessionConnectedEvent event) {
		 StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
		 

	}
	@EventListener
	public void userDisconnect(SessionDisconnectEvent event) {
		
	}
	
}
