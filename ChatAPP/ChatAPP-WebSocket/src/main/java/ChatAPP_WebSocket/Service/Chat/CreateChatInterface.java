package ChatAPP_WebSocket.Service.Chat;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import chatAPP_CommontPart.AOP.WebSocketThreadLocalSession;

public interface CreateChatInterface {

	public void createUserChat(SimpMessageHeaderAccessor session,long createdByUser,long otherUser);
	
	public void createGroupChat(SimpMessageHeaderAccessor session);

}

