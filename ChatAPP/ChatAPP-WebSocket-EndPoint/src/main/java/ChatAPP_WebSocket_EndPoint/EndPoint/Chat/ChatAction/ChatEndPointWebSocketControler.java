package ChatAPP_WebSocket_EndPoint.EndPoint.Chat.ChatAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Async;

import ChatAPP_WebSocket.WebSocketEndPointPath;
import ChatAPP_WebSocket.Service.Chat.ProcessChatMessageService;
import chatAPP_DTO.Message.MessageDTO;
import chatAPP_DTO.Message.SawMessageDTO;

@Async
public class ChatEndPointWebSocketControler implements WebSocketChatEndPoint{

	
	@Autowired
	private ProcessChatMessageService MessageService;
	@Override
	
	@MessageMapping(WebSocketEndPointPath.Chat_SendMessagePath)
	public void SendMessage(MessageDTO message,SimpMessageHeaderAccessor session) {
		this.MessageService.SendMessage(session,message);
	}

	@MessageMapping(WebSocketEndPointPath.Chat_changeMessagePath)

	@Override
	public void ChangeMessage(MessageDTO message,SimpMessageHeaderAccessor session) {
		this.MessageService.ChangeMessage(session,message);
	}

	@MessageMapping(WebSocketEndPointPath.Chat_sawMessagePath)
	@Override
	public void sawMessage(SawMessageDTO message, SimpMessageHeaderAccessor session) {
		this.MessageService.sawMessage(session,message);
	}

}
