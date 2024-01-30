package ChatAPP_Chat.ChatManagement;

import org.springframework.stereotype.Component;

public interface ActiveChatManagementInterface {

	public ActiveChat getActiveChat(String chatID);
	
	@Component
	public static class justForCmpo implements ActiveChatManagementInterface{

		@Override
		public ActiveChat getActiveChat(String chatID) {
			// TODO Auto-generated method stub
			return null;
		}}
}
