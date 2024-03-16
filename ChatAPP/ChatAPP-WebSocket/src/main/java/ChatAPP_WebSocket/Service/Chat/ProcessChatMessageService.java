package ChatAPP_WebSocket.Service.Chat;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import ChatAPP_Chat.ChatManagement.ChatManagementInterface;
import ChatAPP_RabitMQ.Producer.RabitMQMessageProducerInterface;
import ChatAPP_Security.RequestPermision.MessageRequestPermision;
import chatAPP_CommontPart.AOP.RabitMQAnnotationAOP;
import chatAPP_CommontPart.Properties.WebSocketEndPointPath;
import chatAPP_CommontPart.ThreadLocal.WebSocketThreadLocalSessionInterface;
import chatAPP_DTO.Message.MessageDTO;
import chatAPP_DTO.Message.SawMessageDTO;
import chatAPP_database.Chat.Messages.MessageEntity;
import chatAPP_database.Chat.Messages.MessageRepositoryEntity;

@Service
public class ProcessChatMessageService {

	
	@Autowired
	private MessageRepositoryEntity messageRepo;
	@Autowired
	private WebSocketThreadLocalSessionInterface.WebSocketThreadLocalSessionValue sessionAttributeInterface;

	@Autowired
	private RabitMQMessageProducerInterface rabitMQPush;

	/** */
	@RabitMQAnnotationAOP(dtoClass = MessageDTO.class, getPath = WebSocketEndPointPath.Chat_SendMessagePath, haveToBeMessageRequired = true)
	public void SendMessage(MessageDTO message) {
		MessageEntity entity=this.messageRepo.convertDTOToEntity(message);
		//message will be save, and update with order in table
		this.messageRepo.saveAndFlush(entity);
		message=this.messageRepo.convertEntityToDTO(entity);
		
		this.PushMessageToRabitMQService(message);
	}

	@RabitMQAnnotationAOP(dtoClass = MessageDTO.class, getPath = WebSocketEndPointPath.Chat_changeMessagePath, haveToBeMessageRequired = true)
	
	public void ChangeMessage(MessageDTO message) {
		//if message is not exist EntityWasNotFoundException would be thrown
		MessageEntity entity=this.messageRepo.findByPrimaryKey(message.getMessageID());
		
		//optimistic locking can be thrown, if message will be modify from other device
		this.messageRepo.saveAndFlush(entity);
		message=this.messageRepo.convertEntityToDTO(entity);
			
		this.PushMessageToRabitMQService(message);
	}
	
	@RabitMQAnnotationAOP(dtoClass = SawMessageDTO.class, getPath = WebSocketEndPointPath.Chat_sawMessagePath, haveToBeMessageRequired = false)
	public void sawMessage(SawMessageDTO message) {
		
		
	}
	
	private void PushMessageToRabitMQService(MessageDTO message) {
		this.rabitMQPush.pushMessageToRabitMQ(message.getChatID(), message);		
	}
}
