package ChatAPP_WebSocket.Service.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import ChatAPP_RabitMQ.Consumer.MessageAcknowledger;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
public class ConfirmSentMessageService {

	@Autowired
	private MessageAcknowledger rabitMQ;
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue session;

	public void ConfirmMessage(SimpMessageHeaderAccessor session,String messageID) {

		this.rabitMQ.AckMessage(this.session.getConnectionID(), messageID);

	}
}
