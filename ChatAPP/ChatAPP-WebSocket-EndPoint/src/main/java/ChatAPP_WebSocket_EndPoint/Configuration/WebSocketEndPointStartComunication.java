package ChatAPP_WebSocket_EndPoint.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import chatAPP_CommontPart.ApplicationListener.WebSocketSessionListener;
import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;

@Controller
public class WebSocketEndPointStartComunication {

	@Autowired
	private WebSocketSessionListener rabitMQConsumer;
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue 
	webSocketSession;
	
	@MessageMapping(WebSocketEndPointPath.Config_StartConsumingPath)
	public void userIsReadyToStartComunication(SimpMessageHeaderAccessor session) {
		Log4j2.log.info(Log4j2.MarkerLog.WebSocket.getMarker(),
				String.format("Device send Conected message, Starting consuming, UserID: %s deviceID: %s", this.webSocketSession.getSessionOwnerUserID(),this.webSocketSession.getCustomUserDetails().getDeviceID())); 		
	
		this.rabitMQConsumer.UserConnect();

	}
}
