package chatAPP_database.Chat;


import java.util.Optional;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_database.CustomJpaRepository;


public interface chatEntityRepository extends CustomJpaRepository<ChatEntity,String>  {

	
	public default ChatEntity createChatUserToUserDatabaseSchema(long createdByUser,String chatID) {
		ChatEntity newEntity=new ChatEntity();
		newEntity.setChatID(chatID);
		newEntity.setCreatedByUserID(createdByUser);
		
		return this.InsertOrIgnore(newEntity,newEntity.getChatID());
		
	}
}
