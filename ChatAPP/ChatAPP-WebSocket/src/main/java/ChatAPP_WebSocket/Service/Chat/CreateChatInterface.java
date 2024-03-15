package ChatAPP_WebSocket.Service.Chat;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import chatAPP_CommontPart.AOP.WebSocketThreadLocalSession;

public interface CreateChatInterface {

	public void createChat(SimpMessageHeaderAccessor session,long createdByUser,long... otherUsers);
	

}

