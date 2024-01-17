package ChatAPP_WebSocket.Service.Chat;

import org.springframework.beans.factory.annotation.Autowired;

import ChatAPP_RabitMQ.Listener.rabitMQListenerService;
import chatAPP_CommontPart.ThreadLocal.ThreadLocalSimpMessageHeaderAccessor;

public class ConfirmSentMessageService {

	@Autowired
	private rabitMQListenerService rabitMQ;
	@Autowired
	private ThreadLocalSimpMessageHeaderAccessor session;
	
	public void ConfirmMessage(String messageID) {
		this.rabitMQ.AckMessage(this.session.getConnectionID(), messageID);
	}
}
