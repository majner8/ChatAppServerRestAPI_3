package ChatAPP_WebSocket.Service.Chat;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import ChatAPP_RabitMQ.Producer.RabitMQMessageProducerInterface;
import ChatAPP_WebSocket.WebSocketEndPointPath;
import chatAPP_CommontPart.AOP.RabitMQAnnotationAOP;
import chatAPP_CommontPart.AOP.WebSocketThreadLocalSession;
import chatAPP_CommontPart.Data.Util.chatIdGenerator;
import chatAPP_DTO.Message.MessageDTO;
import chatAPP_database.Chat.ChatEntity;
import chatAPP_database.Chat.UserChats;
import chatAPP_database.Chat.UserChatsRepository;
import chatAPP_database.Chat.chatEntityRepository;

@Service
public class CreateChatService implements CreateChatInterface{

	@Autowired
	private chatEntityRepository chatRepo;
	@Autowired
	private chatIdGenerator chatIdGenerator;
	@Autowired
	private UserChatsRepository userChatRepo;
	@Autowired
	private RabitMQMessageProducerInterface rabbitMQproducer;
	
	@RabitMQAnnotationAOP(dtoClass = MessageDTO.class, getPath = WebSocketEndPointPath.Chat_SendMessagePath, haveToBeMessageRequired = true)
	@WebSocketThreadLocalSession
	@Override
	public void createUserChat(SimpMessageHeaderAccessor session, long createdByUser, long otherUser) {
		// TODO Auto-generated method stub
		String chatID=this.chatIdGenerator.generateIDForUserToUserChat(createdByUser, otherUser);
		ChatEntity entity=this.chatRepo.createChatUserToUserDatabaseSchema(createdByUser, chatID);
		if(entity==null) {
			//schema was created before
			return;
		}
			this.makeDatabaseSchemaOnUserChats(entity, createdByUser,otherUser);
		
		
	}
	@RabitMQAnnotationAOP(dtoClass = MessageDTO.class, getPath = WebSocketEndPointPath.Chat_SendMessagePath, haveToBeMessageRequired = true)
	@WebSocketThreadLocalSession
	@Override
	public void createGroupChat(SimpMessageHeaderAccessor session) {
		// TODO Auto-generated method stub
		
	}

	
	@Transactional
	private void makeDatabaseSchemaOnUserChats(ChatEntity chat,long... userIds) {
		for(long user:userIds) {
			this.userChatRepo.createUserChatSchema(user,chat.getChatID(), chat);
		}
	}
}
