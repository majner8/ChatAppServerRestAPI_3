package ChatAPP_Chat.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import ChatAPP_Chat.ChatConfig.ChatProperties;
import ChatAPP_Chat.ChatManagement.ActiveChat;
import ChatAPP_Chat.ChatManagement.Active_chat_int;
import ChatAPP_Chat.ChatManagement.ChatManagementInterface;
import chatAPP_database.Chat.UserChatsRepository;

public class ChatMessageService implements ChatManagementInterface{

	private final Map<String,Active_chat_int> chat=Collections.synchronizedMap(new HashMap<String,Active_chat_int>());
	@Autowired
	private ChatProperties properties;
	@Autowired
	private UserChatsRepository chatRepo;

	/**Metod manage loading chat from database, and put them to collection */
	private Active_chat_int loadChat(String chatID) {
		Set<Long>usersIDs=this.chatRepo.getUserId(chatID);
		ActiveChat chat=new ActiveChat(usersIDs);
		this.chat.put(chatID, chat);
		return chat;
	}
	
	@Scheduled()
	public void GarbageCollectorChech() {
		synchronized(this.chat) {
			this.chat.forEach((K,V)->{
				this.MakeGarbageChech(K, V);
			});
		}
		
	}
	@Async
	private void MakeGarbageChech(String key,Active_chat_int value) {
		if(!value.isChatActive(this.properties.getMaximumAvaiableTimeoutOfActiveChat())) {
			//chat have to be removed
			this.chat.remove(key, value);
		}
		
	}

	@Override
	public Set<Long> getUserIDofMembers(String chatID, boolean shouldLoadedChat) {
		// TODO Auto-generated method stub
		Active_chat_int chat=this.chat.get(chatID);
		if(chat==null) {
			//chat have to be loaded.
			chat=this.loadChat(chatID);
		}
		return chat.getUserIDofMembers();	}
}
