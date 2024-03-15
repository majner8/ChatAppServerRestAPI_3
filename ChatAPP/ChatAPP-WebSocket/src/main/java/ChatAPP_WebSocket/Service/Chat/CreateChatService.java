package ChatAPP_WebSocket.Service.Chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import ChatAPP_RabitMQ.Producer.RabitMQMessageProducerInterface;
import ChatAPP_WebSocket.WebSocketEndPointPath;
import chatAPP_CommontPart.AOP.RabitMQAnnotationAOP;
import chatAPP_CommontPart.AOP.WebSocketThreadLocalSession;
import chatAPP_CommontPart.Data.Util.chatIdGenerator;
import chatAPP_DTO.Chat.ChatInformationDTO;
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
	
	@RabitMQAnnotationAOP(dtoClass = ChatInformationDTO.class, getPath = WebSocketEndPointPath.Chat_SendMessagePath, haveToBeMessageRequired = true)
	@WebSocketThreadLocalSession
	@Override
	public void createChat(SimpMessageHeaderAccessor session, long createdByUser, long[] otherUser) {
		String chatID=this.generateChatId(createdByUser, otherUser);
		ChatEntity entity=this.chatRepo.createChatUserToUserDatabaseSchema(createdByUser, chatID);
		Set<UserChats> userChat=this.makeDatabaseSchemaOnUserChats(entity, createdByUser,otherUser);
		entity.setChat(userChat);
		this.rabbitMQproducer.pushMessageToRabitMQ(chatID,this.chatRepo.convertEntityToDTO(entity));
	}
	private String generateChatId(long createdByUser, long[] otherUsers) 
	{
		return otherUsers.length==1?
				this.chatIdGenerator.generateIDForUserToUserChat(createdByUser, otherUsers[0])
				:null;
	}
	
	@Transactional
	private Set<UserChats> makeDatabaseSchemaOnUserChats(ChatEntity chat,long createdBy,long[] userIds) {
		HashSet<UserChats> list=new HashSet<UserChats>();
		UserChats x=this.userChatRepo.createUserChatSchema(createdBy, chat.getChatID(), chat);
		list.add(x);
		for(long user:userIds) {
			x=this.userChatRepo.createUserChatSchema(user,chat.getChatID(), chat);
			list.add(x);
		}
		return list;
	}
}
