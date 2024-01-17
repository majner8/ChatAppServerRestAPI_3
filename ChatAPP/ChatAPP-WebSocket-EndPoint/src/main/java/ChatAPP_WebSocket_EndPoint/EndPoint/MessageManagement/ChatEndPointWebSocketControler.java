package ChatAPP_WebSocket_EndPoint.EndPoint.MessageManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Async;

import ChatAPP_Chat.Service.ChatMessageService;
import ChatAPP_WebSocket.Service.Chat.ProcessChatMessageService;
import ChatAPP_WebSocket_EndPoint.WebSocketEndPointPath;
import chatAPP_CommontPart.AOP.RabitMQPropertiesAOP;
import chatAPP_DTO.Message.MessageDTO;
import chatAPP_DTO.Message.SawMessageDTO;

@Async
public class ChatEndPointWebSocketControler implements WebSocketChatEndPoint{

	
	@Autowired
	private ProcessChatMessageService MessageService;
	@Override
	@RabitMQPropertiesAOP(dtoClass = MessageDTO.class, 
	getPath = WebSocketEndPointPath.Chat_SendMessagePath, 
	haveToBeMessageRequired = true)
	@MessageMapping(WebSocketEndPointPath.Chat_SendMessagePath)
	public void SendMessage(MessageDTO message,SimpMessageHeaderAccessor session) {
		this.MessageService.SendMessage(message);
	}

	@MessageMapping(WebSocketEndPointPath.Chat_changeMessagePath)
	@RabitMQPropertiesAOP(dtoClass = MessageDTO.class, 
	getPath = WebSocketEndPointPath.Chat_changeMessagePath, 
	haveToBeMessageRequired = true)
	@Override
	public void ChangeMessage(MessageDTO message,SimpMessageHeaderAccessor session) {
		this.MessageService.ChangeMessage(message);
	}

	@MessageMapping(WebSocketEndPointPath.Chat_sawMessagePath)
	@RabitMQPropertiesAOP(dtoClass = SawMessageDTO.class, 
	getPath = WebSocketEndPointPath.Chat_sawMessagePath, 
	haveToBeMessageRequired = false)
	@Override
	public void sawMessage(SawMessageDTO message, SimpMessageHeaderAccessor session) {
		this.MessageService.sawMessage(message);
	}

}
