package ChatAPP_WebSocket.Service.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ChatAPP_RabitMQ.Listener.RabbitMQMessageRelayInterface;
import ChatAPP_WebSocket.WebSocketHeaderAttributeName;
import chatAPP_CommontPart.Log4j2.Log4j2;

public class StompMessageRelayService implements RabbitMQMessageRelayInterface {

	@Autowired
	private SimpMessagingTemplate SimpMessageTemplate;
	@Autowired
	private WebSocketHeaderAttributeName attribute;
	@Autowired
	
	@Override
	public void MessageTimeoutExpired(String recipientID, String messageID) {
		//have to be done 
	}
	@Override
	public void SendConsumedMessage(String webSocketEndPointPath, String messageID, String message,
			String recipientID) {
		HashMap<String,Object>header=new HashMap<String,Object>();
		header.put(this.attribute.getAcknowledgeIdHeaderName(), messageID);
		this.SimpMessageTemplate.convertAndSendToUser(messageID, webSocketEndPointPath, message, header);
	}
	
	
	
	
}
