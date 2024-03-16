package ChatAPP_WebSocket.Service.Chat;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;


public interface CreateChatInterface {

	public void createChat(SimpMessageHeaderAccessor session,long createdByUser,long... otherUsers);
	

}

