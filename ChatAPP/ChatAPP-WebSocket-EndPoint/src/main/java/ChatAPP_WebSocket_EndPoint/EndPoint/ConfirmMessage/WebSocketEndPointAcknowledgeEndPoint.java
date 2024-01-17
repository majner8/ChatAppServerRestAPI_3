package ChatAPP_WebSocket_EndPoint.EndPoint.ConfirmMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Async;

import ChatAPP_WebSocket.Service.Chat.ConfirmSentMessageService;
import ChatAPP_WebSocket_EndPoint.WebSocketEndPointPath;
import chatAPP_CommontPart.AOP.ThreadLocalWebSocketSession;
@Async
public class WebSocketEndPointAcknowledgeEndPoint implements WebSocketEndPointAcknowledgeEndPointInterface {

	@Autowired
	private ConfirmSentMessageService service;
	@MessageMapping(value=WebSocketEndPointPath.AcknowledgeEndPoint_ConfirmMessage)
	@Override
	public void ConfirmMessage(SimpMessageHeaderAccessor session, String messageIDToAck) {
		this.service.ConfirmMessage(session,messageIDToAck);
	}

	

	
}
