package ChatAPP_WebSocket.ConnectedSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import chatAPP_CommontPart.ApplicationListener.WebSocketSessionListener;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;

@Component
public class StoamptConnectionListener {

	@Autowired
	private WebSocketSessionListener rabitMQConsumer;
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue
	webSocketSession;
	@EventListener
	public void userConnect(SessionConnectedEvent event) {
		Log4j2.log.info(Log4j2.MarkerLog.WebSocket.getMarker(),
				String.format("Connection was Estabilish, UserID: %s deviceID: %s", this.webSocketSession.getSessionOwnerUserID(),this.webSocketSession.getCustomUserDetails().getDeviceID()));
	}

	@EventListener
	public void userDisconnect(SessionDisconnectEvent event) {

	}

}
