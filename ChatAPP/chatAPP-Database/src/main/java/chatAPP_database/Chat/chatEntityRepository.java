package chatAPP_database.Chat;


import java.util.Optional;

import chatAPP_CommontPart.Log4j2.Log4j2;
import chatAPP_database.CustomJpaRepository;


public interface chatEntityRepository extends CustomJpaRepository<ChatEntity,String>  {

	
	public default ChatEntity createChatUserToUserDatabaseSchema(long createdByUser,String chatID) {
		Optional<ChatEntity> entity=this.findById(chatID);
		if(entity.isEmpty()) {
			Log4j2.log.warn(Log4j2.MarkerLog.Database.getMarker(),String.format("createChatUserToUserDatabaseSchema was skipped, schema has been created yet"
					+ " createdByUser: %d chatID: %s"
					+ "", createdByUser,chatID));
			return null;
		}
		ChatEntity newEntity=new ChatEntity();
		newEntity.setChatID(chatID);
		newEntity.setCreatedByUserID(createdByUser);
		return this.save(newEntity);
		
	}
}
