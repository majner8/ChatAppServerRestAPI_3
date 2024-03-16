package ChatAPP_WebSocket_EndPoint.EndPoint.Chat.ChatAction;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import chatAPP_DTO.Message.MessageDTO;
import chatAPP_DTO.Message.SawMessageDTO;

public interface WebSocketChatComunicationEndPoint{
	

	public void SendMessage(MessageDTO message,SimpMessageHeaderAccessor session);
	
	public void ChangeMessage(MessageDTO message,SimpMessageHeaderAccessor session);

	public void sawMessage(SawMessageDTO message,SimpMessageHeaderAccessor session);
}
