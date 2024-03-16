package ChatAPP_WebSocket_EndPoint.EndPoint.Chat.ChatAction;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import chatAPP_DTO.Chat.CreateChatDTO;

public interface WebSocketChatEndPoint {

	public void createChat(CreateChatDTO chatDTO,SimpMessageHeaderAccessor session);
}
