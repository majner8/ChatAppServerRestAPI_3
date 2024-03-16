package ChatAPP_WebSocket_EndPoint.EndPoint.Chat.ChatAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ChatAPP_WebSocket.Service.Chat.CreateChatInterface;
import ChatAPP_WebSocket.Service.Chat.ProcessChatMessageService;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;
import chatAPP_DTO.Chat.CreateChatDTO;
import chatAPP_DTO.Message.MessageDTO;
import chatAPP_DTO.Message.SawMessageDTO;

@Controller
public class ChatEndPointWebSocketControler implements WebSocketChatComunicationEndPoint,WebSocketChatEndPoint{

	
	@Autowired
	private ProcessChatMessageService MessageService;
	@Autowired
	private CreateChatInterface createChatService;
	@Override
	@MessageMapping(WebSocketEndPointPath.Chat_SendMessagePath)
	public void SendMessage(MessageDTO message,SimpMessageHeaderAccessor session) {
		
		this.MessageService.SendMessage(message);
	}

	@MessageMapping(WebSocketEndPointPath.Chat_changeMessagePath)
	@Override
	public void ChangeMessage(MessageDTO message,SimpMessageHeaderAccessor session) {
		this.MessageService.ChangeMessage(message);
	}

	@MessageMapping(WebSocketEndPointPath.Chat_sawMessagePath)
	@Override
	public void sawMessage(SawMessageDTO message, SimpMessageHeaderAccessor session) {
		this.MessageService.sawMessage(message);
	}

	@MessageMapping(WebSocketEndPointPath.createChatEndPoint)
	@Override
	public void createChat(CreateChatDTO chatDTO, SimpMessageHeaderAccessor session) {
		this.createChatService.createChat(chatDTO.getCreatedBy(), chatDTO.getOtherUser());
	}

}
