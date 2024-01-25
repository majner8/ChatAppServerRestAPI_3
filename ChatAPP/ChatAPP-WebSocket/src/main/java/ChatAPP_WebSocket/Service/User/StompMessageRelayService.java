package ChatAPP_WebSocket.Service.User;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import ChatAPP_RabitMQ.Listener.RabbitMQMessageRelayInterface;

@Component
public class StompMessageRelayService implements RabbitMQMessageRelayInterface {

	@Autowired
	private SimpMessagingTemplate SimpMessageTemplate;
	
	@Override
	public void MessageTimeoutExpired(String recipientID, String messageID) {
		//have to be done 
	}
	@Override
	public void SendConsumedMessage(String webSocketEndPointPath, String messageID, String message,
			String recipientID) {
		HashMap<String,Object>header=new HashMap<String,Object>();

		this.SimpMessageTemplate.convertAndSendToUser(messageID, webSocketEndPointPath, message, header);
	}
	
	
	
	
}
