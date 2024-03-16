package ChatAPP_WebSocket.Service.Chat;

import java.util.List;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;


public interface CreateChatInterface {

	public void createChat(long createdByUser,long[] otherUsers);
	
}

